package Networking.code_14_TCP_Chat_Server_Client_Pro_Buff;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class code_TCP_Client
{
    static int PORT = 5555;
    static String serverAdd = "localhost";

    public static void main(String[] args)
    {
        try (Socket socket = new Socket(serverAdd, PORT);
             BufferedReader serverIn = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(
                     new BufferedWriter(
                             new OutputStreamWriter(socket.getOutputStream())),
                     true);
             Scanner userInput = new Scanner(System.in))
        {
            System.out.println("Connected to server");

            while (true)
            {
                System.out.print("Client: ");
                String msg = userInput.nextLine();
                out.println(msg);

                if ("exit".equalsIgnoreCase(msg))
                {
                    break;
                }

                String response = serverIn.readLine();
                if (response == null)
                {
                    System.out.println("Server disconnected");
                    break;
                }

                System.out.println("Server: " + response);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
