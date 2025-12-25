package io.nosleep.util;

public final class OSUtils {
  private static final boolean windows = System.getProperty("os.name").startsWith("Windows");

  private OSUtils() {}

  public static boolean isWindows() {
    return windows;
  }
}
