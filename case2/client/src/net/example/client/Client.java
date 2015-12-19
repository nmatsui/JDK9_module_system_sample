package net.example.client;

import com.example.service.Gateway;


public class Client {
  public static void main(String argv[]) {
    Gateway gateway = new Gateway();
    gateway.method();
  }
}
