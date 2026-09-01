package Networking.code_13_TCP_Chat_Server_Client_Pro_Scan;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class code_TCP_Server
{
    static int PORT = 5555;

    public static void main(String[] args)
    {
        try (ServerSocket ss = new ServerSocket(PORT))
        {
            System.out.println("Listening at port: " + PORT);

            try (Socket cs = ss.accept())
            {
                System.out.println("Client connected: " + cs.getInetAddress());

                Scanner clientIn = new Scanner(cs.getInputStream());
                PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
                Scanner serverInput = new Scanner(System.in);

                while (true)
                {
                    if (!clientIn.hasNextLine())
                    {
                        System.out.println("Client disconnected");
                        break;
                    }

                    String clientMsg = clientIn.nextLine();

                    if ("exit".equalsIgnoreCase(clientMsg))
                    {
                        out.println("Server closing connection");
                        break;
                    }

                    System.out.println("Client: " + clientMsg);

                    System.out.print("Server: ");
                    String serverMsg = serverInput.nextLine();

                    out.println(serverMsg);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
