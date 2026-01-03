package Networking.code_11_TCP_Server_Client_Pro_Buff;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Server
{
    private static final int PORT = 5555;

    public static void main(String[] args)
    {
        System.out.println("Server starting...");
        System.out.println("Listening on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket clientSocket = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true))
        {
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            String message;
            while ((message = in.readLine()) != null)
            {
                if ("exit".equalsIgnoreCase(message))
                {
                    out.println("Server closing connection");
                    break;
                }

                System.out.println("Received: " + message);
                out.println("Echo: " + message);
            }

            System.out.println("Client disconnected");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Server stopped");
    }
}
