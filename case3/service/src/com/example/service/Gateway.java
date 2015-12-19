package com.example.service;

import com.example.service.util.Util;

public class Gateway {
  public void method() {
    System.out.printf("%s start%n", this.getClass().getName());
    Util util = new Util();
    util.method();
    System.out.printf("%s end%n", this.getClass().getName());
  }
}
