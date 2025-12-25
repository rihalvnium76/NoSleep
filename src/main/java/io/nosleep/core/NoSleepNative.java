package io.nosleep.core;

import com.sun.jna.platform.win32.Kernel32;
import io.nosleep.util.OSUtils;
import io.nosleep.util.RandomRange;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.sun.jna.platform.win32.WinBase.*;

public class NoSleepNative implements Runnable {
  private final RandomRange random = new RandomRange();
  private final AtomicBoolean running;
  private final int executionState;

  private NoSleepNative(AtomicBoolean running, int executionState) {
    this.running = running;
    this.executionState = executionState;
  }

  public static NoSleepNative create(String[] args, AtomicBoolean running) {
    if (
      (args.length == 0 && !OSUtils.isWindows()) ||
      (args.length >= 1 && !"api".equals(args[0])) ||
      (args.length >= 2 && !"disp".equals(args[1]))
    ) {
      return null;
    }

    int executionState = ES_CONTINUOUS | ES_SYSTEM_REQUIRED | ES_AWAYMODE_REQUIRED;
    if (args.length >= 2) {
      executionState |= ES_DISPLAY_REQUIRED;
    }

    if (running == null) {
      running = new AtomicBoolean(true);
    }
    return  new NoSleepNative(running, executionState);
  }

  @Override
  public void run() {
    try {
      while (running.get()) {
        // enable
        Kernel32.INSTANCE.SetThreadExecutionState(executionState);
        Thread.sleep(random.nextInt(6000, 12000));
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    } finally {
      // restore
      Kernel32.INSTANCE.SetThreadExecutionState(ES_CONTINUOUS);
    }
  }
}
