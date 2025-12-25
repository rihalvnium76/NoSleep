package io.nosleep;

import io.nosleep.core.NoSleepNative;
import io.nosleep.core.NoSleepRobot;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class Main {
  public static void main(String[] args) {
    AtomicBoolean running = buildRunning();
    Runnable preventer = buildPreventer(args, running);
    runPreventer(preventer);
    printHelp(preventer, args);
  }

  private static AtomicBoolean buildRunning() {
    AtomicBoolean running = new AtomicBoolean(true);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("[I] Shutting down");
      running.set(false);
    }));

    return running;
  }

  private static Runnable buildPreventer(String[] args, AtomicBoolean running) {
    List<Supplier<Runnable>> factories = Arrays.asList(
      () -> NoSleepNative.create(args, running),
      () -> NoSleepRobot.create(args, running)
    );
    for (Supplier<Runnable> factory : factories) {
      Runnable runnable = factory.get();
      if (runnable != null) {
        return runnable;
      }
    }
    return null;
  }

  private static void runPreventer(Runnable preventer) {
    if (preventer != null) {
      System.out.printf("[I] %s is running\n", preventer.getClass().getSimpleName());
      preventer.run();
    }
  }

  private static void printHelp(Runnable preventer, String[] args) {
    if (preventer == null) {
      System.err.printf("[E] Unsupported sleep preventer: %s\n", Arrays.toString(args));
      System.out.println(
        "[I] Available sleep preventers:\n"
        + "  - key\n"
        + "  - api\n"
        + "  - api disp"
      );
    }
  }
}
