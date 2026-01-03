package Networking.code_13_TCP_Chat_Server_Client_Pro;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class code_TCP_Client
{
    static int PORT = 5555;
    static String serverAdd = "localhost";

    public static void main(String[] args)
    {
        try (Socket cs = new Socket(serverAdd, PORT))
        {
            System.out.println("Connected to server");

            Scanner userInput = new Scanner(System.in);
            Scanner serverInput = new Scanner(cs.getInputStream());
            PrintWriter out = new PrintWriter(cs.getOutputStream(), true);

            while (true)
            {
                System.out.print("Client: ");
                String msg = userInput.nextLine();
                out.println(msg);

                if ("exit".equalsIgnoreCase(msg))
                {
                    break;
                }

                if (!serverInput.hasNextLine())
                {
                    System.out.println("Server disconnected");
                    break;
                }

                String response = serverInput.nextLine();
                System.out.println("Server: " + response);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
