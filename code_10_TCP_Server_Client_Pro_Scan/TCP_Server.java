package Networking.code_10_TCP_Server_Client_Pro;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCP_Server
{
    private static final int PORT = 5555;

    public static void main(String[] args)
    {
        System.out.println("Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            System.out.println("Listening on port " + PORT);

            try (Socket clientSocket = serverSocket.accept())
            {
                System.out.println("Client connected: "
                        + clientSocket.getInetAddress());

                Scanner in = new Scanner(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                while (true)
                {
                    if (!in.hasNextLine())
                    {
                        System.out.println("Client disconnected");
                        break;
                    }

                    String message = in.nextLine();

                    if ("exit".equalsIgnoreCase(message))
                    {
                        out.println("Server closing connection");
                        break;
                    }

                    System.out.println("Received: " + message);
                    out.println("Echo: " + message);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Server stopped");
    }
}
