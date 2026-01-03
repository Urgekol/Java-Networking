package Networking.code_14_TCP_Chat_Server_Client_Pro_Buff;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class code_TCP_Server
{
    static int PORT = 5555;

    public static void main(String[] args)
    {
        System.out.println("Listening at port: " + PORT);

        try (ServerSocket ss = new ServerSocket(PORT);
             Socket cs = ss.accept();
             BufferedReader clientIn = new BufferedReader(new InputStreamReader(cs.getInputStream()));
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cs.getOutputStream())), true);
             Scanner serverInput = new Scanner(System.in))
        {
            System.out.println("Client connected: " + cs.getInetAddress());

            String clientMsg;
            while ((clientMsg = clientIn.readLine()) != null)
            {
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

            System.out.println("Client disconnected");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
