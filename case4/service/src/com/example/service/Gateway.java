package com.example.service;

import com.example.commons.lib.Library;

public class Gateway {
  public void method() {
    System.out.printf("%s start%n", this.getClass().getName());
    Library lib = new Library();
    lib.method();
    System.out.printf("%s end%n", this.getClass().getName());
  }
}
