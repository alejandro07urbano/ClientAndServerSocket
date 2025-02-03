package SocketStuff;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class MailboxServer {
    private static final int PORT = 6013;  // Define the server port number
    private static ConcurrentHashMap<String, String> mailbox = new ConcurrentHashMap<>();  // Thread-safe map for storing messages

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Mailbox server started, listening on port " + PORT);

            // Continuously accept new client connections in an infinite loop
            while (true) {
                // Accept a new client connection and get the client's socket
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client is connected  to the server: " + clientSocket.getInetAddress());

                // Start a new thread to handle the client
                new Thread(new ClientHandlers(clientSocket)).start();
            }
        } catch (IOException e) {
            // Handle any I/O exceptions
            e.printStackTrace();
        }
    }

    // Inner class to handle client communication
    private static class ClientHandlers implements Runnable {
        private Socket socket;  // Socket representing the client's connection

        // Constructor to initialize the socket
        public ClientHandlers(Socket socket) {
            this.socket = socket;
        }
        //Run the server and client 
        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String message;
                
                // Continuously read messages from the client
                while ((message = in.readLine()) != null) {
                    System.out.println("Server recieved message: " + message);

                    // Process the client's input and send an appropriate response
                    String definedMessage = getDefinedMessage(message);
                    if (definedMessage != null) {
                        // Send predefined message based on input (1, 2, or 3)
                        out.println(definedMessage);
                    } else {
                        // Send an error message if input is invalid
                        out.println("Invalid input. Please enter 1, 2, or 3.");
                    }
                }
            } catch (IOException e) {
                // Handle exceptions during client-server communication
                e.printStackTrace();
            } finally {
                // Close the socket connection when done
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Method to return a predefined message based on the client's input
        private String getDefinedMessage(String key) {
            //Each case is the output of the server 
            switch (key) {
                case "1":
                    return "Hello";  
                case "2":
                    return "Hello World";  
                case "3":
                    return "Good Job";  
                default:
                    return null;  
            }
        }
    }
}
