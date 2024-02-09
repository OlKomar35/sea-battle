package org.komar.technical_project.server;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

  public class ServerApp {
      private static List<DataOutputStream> outputStreams = new ArrayList<>();
      public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
          System.out.println("Сервер запущен, ожидает подключения клиентов...");

          while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключился.");

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStreams.add(outputStream);

            new Thread(() -> {
              try {
                while (true) {
                  String message = inputStream.readUTF();
                  if (message.equals("/end")) {
                    break;
                  }
                  broadcastMessage(message);
                }
              } catch (IOException e) {
                e.printStackTrace();
              }
            }).start();
          }

        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      private static void broadcastMessage(String message) {
        for (DataOutputStream outputStream : outputStreams) {
          try {
            outputStream.writeUTF(message);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
  }

