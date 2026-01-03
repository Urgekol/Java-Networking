package Networking.code_16_TCP_Multi_Client_Pro_Scan;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient
{
    private static final String SERVER = "localhost";
    private static final int PORT = 5555;

    public static void main(String[] args)
    {
        System.out.println("Connecting to " + SERVER + ":" + PORT);

        try (Socket socket = new Socket(SERVER, PORT);
             Scanner serverIn = new Scanner(socket.getInputStream());
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner userIn = new Scanner(System.in))
        {
            Thread reader = new Thread(() -> {
                while (serverIn.hasNextLine())
                {
                    System.out.println(serverIn.nextLine());
                }
                System.out.println("Server closed connection");
            });

            reader.start();

            while (true)
            {
                String msg = userIn.nextLine();
                out.println(msg);

                if ("exit".equalsIgnoreCase(msg))
                    break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Disconnected");
    }
}
