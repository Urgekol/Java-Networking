package Networking.code_11_TCP_Server_Client_Pro_Buff;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCP_Client
{
    private static final String SERVER_ADDR = "localhost";
    private static final int PORT = 5555;

    public static void main(String[] args)
    {
        try (Socket socket = new Socket(SERVER_ADDR, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
             Scanner userInput = new Scanner(System.in))
        {
            System.out.println("Connected to server");

            while (true)
            {
                System.out.print("Enter message: ");
                String message = userInput.nextLine();

                out.println(message);

                String response = in.readLine();
                if (response == null)
                {
                    System.out.println("Server disconnected");
                    break;
                }

                System.out.println("Server says: " + response);

                if ("exit".equalsIgnoreCase(message))
                {
                    break;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Client closed");
    }
}
