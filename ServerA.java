import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;

public class ServerA {
  public static void main(String[] args) throws Exception {
    // Set the port number
    int portNumber = 4000;
    // Establish the listen socket
    ServerSocket serverSocket = new ServerSocket(portNumber);
    // Process client requests
    while (true) {
      // Listen for a new connection request
      Socket socket = serverSocket.accept();
      // Create a new thread for the connection
      Thread thread = new Thread(new ClientHandler(socket));
      // Start the thread
      thread.start();
    }
  }

  private static class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
      this.socket = socket;
    }

    public void run() {
      try {
        // Create a BufferedReader to read from the socket
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Create a PrintWriter to write to the socket
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Read the file name from the socket
        String fileName = in.readLine();

        // Append the directory to the file name
        fileName = "C:\\Users\\kosta\\Desktop\\asyrmata1\\" + fileName;

        // Check if the file exists
        File file = new File(fileName);
        if (file.exists()) {
          // Send the file to the client
          BufferedReader fileIn = new BufferedReader(new FileReader(fileName));
          String line;
          while ((line = fileIn.readLine()) != null) {
            out.println(line);
          }

          // Close the file
          fileIn.close();
        } else {
          // Send an error message to the client
          out.println("Error: File not found");
        }

        // Close the socket
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
