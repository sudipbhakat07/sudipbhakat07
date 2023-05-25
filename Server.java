import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
  public static void main(String[] args) {
    try {
      ServerSocket serverSocket = new ServerSocket(1234);
      System.out.println("Server started. Listening for connections...");

      // Accept client connections
      List<Socket> clients = new ArrayList<>();
      while (clients.size() < 5) {
        Socket clientSocket = serverSocket.accept();
        clients.add(clientSocket);
        System.out.println("Client connected: " + clientSocket.getInetAddress());
      }

      // Receive clock times from clients
      List<Integer> clockTimes = new ArrayList<>();
      for (Socket client : clients) {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        int clockTime = Integer.parseInt(in.readLine());
        clockTimes.add(clockTime);
      }

      // Calculate average clock time
      int sum = 0;
      for (int clockTime : clockTimes) {
        sum += clockTime;
      }
      int averageTime = sum / clockTimes.size();

      // Send time difference to clients
      for (Socket client : clients) {
        int timeDifference = averageTime - clockTimes.get(clients.indexOf(client));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        out.println(timeDifference);
      }

      // Close connections
      for (Socket client : clients) {
        client.close();
      }
      serverSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
