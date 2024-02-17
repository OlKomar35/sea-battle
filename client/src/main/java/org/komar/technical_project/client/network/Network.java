package org.komar.technical_project.client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
public class Network {

  private final Scanner scanner;

  public Network(Scanner scanner) throws IOException {

    Socket socket = new Socket("localhost", 1234);
    this.scanner = scanner;
    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

    new Thread(() -> {

      try {
        while (true) {
          String str = scanner.nextLine();
          outputStream.writeUTF(str);
          if (!str.equals(null)) {
            System.out.println("[Client] " + str);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }).start();

    new Thread(() -> {
      try {
        while (true) {
          String massage = inputStream.readUTF();

          if (!massage.equals(null)) {
            System.out.println("[Server] " + massage);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();

  }
}

