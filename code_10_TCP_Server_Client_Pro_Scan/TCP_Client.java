package Networking.code_10_TCP_Server_Client_Pro_Scan;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCP_Client
{
    private static final String SERVER_ADDR = "localhost";
    private static final int PORT = 5555;

    public static void main(String[] args)
    {
        try (Socket socket = new Socket(SERVER_ADDR, PORT))
        {
            System.out.println("Connected to server");

            Scanner userInput = new Scanner(System.in);
            Scanner serverInput = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            while (true)
            {
                System.out.print("Enter message: ");
                String message = userInput.nextLine();

                out.println(message);

                if ("exit".equalsIgnoreCase(message))
                {
                    break;
                }

                if (!serverInput.hasNextLine())
                {
                    System.out.println("Server disconnected");
                    break;
                }

                String response = serverInput.nextLine();
                System.out.println("Server says: " + response);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Client closed");
    }
}
