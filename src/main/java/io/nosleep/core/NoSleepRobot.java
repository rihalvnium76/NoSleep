package io.nosleep.core;

import io.nosleep.util.OSUtils;
import io.nosleep.util.RandomRange;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class NoSleepRobot implements Runnable {
  private final Robot robot;
  private final RandomRange random;
  private final AtomicBoolean running;

  private NoSleepRobot(AtomicBoolean running) {
    robot = buildRobot();
    random = new RandomRange();
    this.running = running;
  }

  public static NoSleepRobot create(String[] args, AtomicBoolean running) {
    if ((args.length == 0 && OSUtils.isWindows()) || (args.length >= 1 && !"key".equals(args[0]))) {
      return null;
    }

    if (running == null) {
      running = new AtomicBoolean(true);
    }
    return new NoSleepRobot(running);
  }

  private static Robot buildRobot() {
    Robot robot;
    try {
      robot = new Robot();
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }
    robot.setAutoWaitForIdle(true);
    return robot;
  }

  @Override
  public void run() {
    while (running.get()) {
      int pressCount = random.nextInt(1, 3);
      for (int i = 0; i < pressCount; i++) {
        // enable
        pressKey();
        // disable;
        pressKey();
      }
      robot.delay(random.nextInt(6000, 12000));
    }
  }

  private void pressKey() {
    pressKey(random.nextInt(100, 250));
  }

  private void pressKey(int delayMs) {
    robot.keyPress(KeyEvent.VK_SCROLL_LOCK);
    robot.keyRelease(KeyEvent.VK_SCROLL_LOCK);
    robot.delay(delayMs);
  }
}
