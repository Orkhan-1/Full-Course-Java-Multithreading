package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ChatClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a new thread to listen for incoming messages from the server
            new Thread(new IncomingMessageHandler()).start();

            // Read input from the console and send to the server
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput);  // Send message to the server
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread to handle incoming messages from the server
    private class IncomingMessageHandler implements Runnable {
        @Override
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println("Server: " + serverMessage);  // Display server message
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ChatClient("localhost", 12345);
        new ChatClient("localhost", 12345);
        new ChatClient("localhost", 12345);// Connect to the server on localhost and port 12345
    }
}
