package com.example.commons.lib;

public class Library {
  public void method() {
    System.out.printf("%s start%n", this.getClass().getName());
    System.out.printf("%s end%n", this.getClass().getName());
  }
}
