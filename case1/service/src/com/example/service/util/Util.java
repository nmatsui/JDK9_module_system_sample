package com.example.service.util;

public class Util {
  public void method() {
    System.out.printf("%s start%n", this.getClass().getName());
    System.out.printf("%s end%n", this.getClass().getName());
  }
}
