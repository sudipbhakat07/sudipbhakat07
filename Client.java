import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) {
    try {
      Socket clientSocket = new Socket("localhost", 1234);

      // Generate random clock time
      Random random = new Random();
      Scanner sc = new Scanner(System.in);
      int clockTime = sc.nextInt();

      // Send clock time to server
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      out.println(clockTime);

      // Receive time difference from server
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      int timeDifference = Integer.parseInt(in.readLine());

      System.out.println("Time Difference is " + timeDifference);

      // Adjust clock
      int adjustedClockTime = clockTime + timeDifference;
      System.out.println("Adjusted Clock Time: " + adjustedClockTime);

      // Close connection
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
