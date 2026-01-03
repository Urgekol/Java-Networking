package Networking.code_8_TCP_Chat_Server_Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class code_TCP_Client
{
    static int PORT = 5555;
    static String serverAdd = "localhost";

    public static void main(String[] args)
    {
        // Try-with-resources ensures that Socket, Scanner, and PrintWriter are automatically closed.
        try (Socket cs = new Socket(serverAdd, PORT))
        {
            System.out.println("Connected to server (Type message or type \"exit\" to quit)");
            Scanner userInputScanner = new Scanner(System.in);

            Scanner serverInputScanner = new Scanner(cs.getInputStream());

            PrintWriter sw = new PrintWriter(cs.getOutputStream(), true);

            String userInput;               // Client message
            while (true)
            {
                System.out.print("Enter message: ");
                userInput = userInputScanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput))
                {
                    System.out.println("Goodbye from server!");
                    break;
                }

                // Send the user input to the server
                sw.println(userInput);

                // Check if the server sent any response
                if (serverInputScanner.hasNextLine())
                {
                    String serverResponse = serverInputScanner.nextLine();
                    System.out.println("Server response: " + serverResponse);
                }
                else
                {
                    System.out.println("Server closed the connection.");
                    break;
                }
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
