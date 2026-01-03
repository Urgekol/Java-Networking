package Networking.code_15_TCP_Multi_Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient
{
    private static final String SERVER = "localhost";
    private static final int PORT = 5555;

    public static void main(String[] args)
    {
        System.out.println("Connecting to " + SERVER + ":" + PORT + " …");

        try (Socket socket = new Socket(SERVER, PORT))
        {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            // Thread to read messages from the server
            Thread t = new Thread(() -> {
                while (in.hasNextLine())
                {
                    System.out.println(in.nextLine());
                }
                System.out.println("Server closed connection.");
            });

            t.start();

            // Main loop to send user input
            while (true)
            {

                String line = sc.nextLine();
                if ("exit".equalsIgnoreCase(line))
                    break;

                out.println(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Disconnected.");
    }
}
