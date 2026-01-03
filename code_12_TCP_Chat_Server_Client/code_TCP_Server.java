package Networking.code_8_TCP_Chat_Server_Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class code_TCP_Server
{
    static int PORT = 5555;

    public static void main(String[] args)
    {
        try (ServerSocket ss = new ServerSocket(PORT))
        {
            System.out.println("Listening at port: " + PORT);
            Socket cs = ss.accept();
            System.out.println("Client connected");

            Scanner cin = new Scanner(cs.getInputStream());
            PrintWriter cw = new PrintWriter(cs.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            String clientMessage;
            String serverMessage;

            // Process each line received from the client as long as it is available.
            while(cin.hasNextLine())
            {
                String cm = cin.nextLine();
                System.out.println("Received from client: " + cm);
                cw.println("Server echos: " + cm);

                System.out.println("Enter your message to send");
                serverMessage = sc.nextLine();
                cw.write(serverMessage);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
