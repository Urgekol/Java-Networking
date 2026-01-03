package Networking.code_15_TCP_Multi_Client;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer
{
    private static final int PORT = 5555;
    private static final List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        System.out.println("Server listening on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }
        }
    }

    private static class ClientHandler implements Runnable
    {
        private Socket socket;

        ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        public void run()
        {
            try (
                    Scanner in = new Scanner(socket.getInputStream());
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            )
            {
                clients.add(out);
                while (in.hasNextLine())
                {
                    String msg = in.nextLine();
                    System.out.println("Received: " + msg);

                    if ("exit".equalsIgnoreCase(msg))
                        break;

                    broadcast("[" + socket.getInetAddress() + "] " + msg, out);
                }
            }
            catch (IOException e)
            {
                System.out.println("Error with client " + socket.getInetAddress());
            }
        }

        private void broadcast(String message, PrintWriter sender)
        {
            synchronized (clients)
            {
                for (PrintWriter clientOut : clients)
                {
                    if (clientOut != sender)
                    {
                        clientOut.println(message);
                    }
                }
            }
        }
    }
}