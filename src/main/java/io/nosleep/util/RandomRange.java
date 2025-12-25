package io.nosleep.util;

import java.util.Random;

public class RandomRange {
  private final Random random = new Random();

  public int nextInt(int min, int max) {
    return random.nextInt(max - min + 1) + min;
  }
}
