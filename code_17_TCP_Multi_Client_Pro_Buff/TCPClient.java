package Networking.code_17_TCP_Multi_Client_Pro_Buff;

import java.io.*;
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
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
             Scanner sc = new Scanner(System.in))
        {
            Thread reader = new Thread(() -> {
                try
                {
                    String line;
                    while ((line = in.readLine()) != null)
                    {
                        System.out.println(line);
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Server connection lost.");
                }
            });
            reader.start();

            while (true)
            {
                String line = sc.nextLine();
                out.println(line);

                if ("exit".equalsIgnoreCase(line))
                    break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Disconnected.");
    }
}
