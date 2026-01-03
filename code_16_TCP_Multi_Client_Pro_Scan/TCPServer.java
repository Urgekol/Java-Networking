package Networking.code_16_TCP_Multi_Client_Pro_Scan;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class TCPServer
{
    private static final int PORT = 5555;

    // Thread-safe list
    private static final List<PrintWriter> clients =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args)
    {
        System.out.println("Server listening on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                new Thread(new ClientHandler(socket)).start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable
    {
        private final Socket socket;
        private PrintWriter out;

        ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        public void run()
        {
            try (Scanner in = new Scanner(socket.getInputStream()))
            {
                out = new PrintWriter(socket.getOutputStream(), true);
                clients.add(out);

                while (in.hasNextLine())
                {
                    String msg = in.nextLine();

                    if ("exit".equalsIgnoreCase(msg))
                        break;

                    System.out.println("Received: " + msg);
                    broadcast("[" + socket.getInetAddress() + "] " + msg);
                }
            }
            catch (IOException e)
            {
                System.out.println("Error with client " + socket.getInetAddress());
            }
            finally
            {
                clients.remove(out);
                try
                {
                    socket.close();
                }
                catch (IOException ignored) {}

                System.out.println("Client disconnected: " + socket.getInetAddress());
            }
        }

        private void broadcast(String message)
        {
            synchronized (clients)
            {
                for (PrintWriter clientOut : clients)
                {
                    clientOut.println(message);
                }
            }
        }
    }
}
