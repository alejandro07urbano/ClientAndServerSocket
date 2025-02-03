package SocketStuff;
import java.io.*;
import java.net.*;

public class Client {
    //Server and port numbers 
    private static final String SERVER_ADDRESS = "localhost"; 
    private static final int SERVER_PORT = 6013; 

    public static void main(String[] args) {
        //Use the try and catch resources
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            //Notify the client when is connected to the server
            System.out.println("The mailbox server is connected");
            //Holds the user input
            String userMessage;
            while (true) {
                //Tells the user to enter an input to get a message from the server 
                System.out.print("Enter 1, 2, 3, to get a message or type exit to quit ");
                userMessage = userInput.readLine();
                //Exit the loop when the user types Exit
                if ("EXIT".equalsIgnoreCase(userMessage)) {
                    break;
                }
                //Send the client input to the server 
                out.println(userMessage);
                // Receive response from the server
                String response = in.readLine();
                System.out.println("Mailbox server response: " + response);
            }
            //To handle any Io exception that could occur
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



