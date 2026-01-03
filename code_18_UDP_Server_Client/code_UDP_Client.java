package Networking.code_18_UDP_Server_Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class code_UDP_Client
{
    static int PORT = 5555;
    static String serverAdd = "localhost";

    public static void main(String[] args)
    {
        try (DatagramSocket socket = new DatagramSocket())
        {
            InetAddress serverAddress = InetAddress.getByName(serverAdd);

            Scanner sc = new Scanner(System.in);
            System.out.println("Connected to server (Type message or type \"exit\" to quit)");

            byte[] receiveBuffer = new byte[1024];

            while (true)
            {
                System.out.print("Enter message: ");
                String userInput = sc.nextLine();

                if ("exit".equalsIgnoreCase(userInput))
                {
                    System.out.println("Closing connection");
                    break;
                }

                // Send message to server
                byte[] sendBuffer = userInput.getBytes();       // Convert the string to bytes
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, PORT);
                socket.send(sendPacket);

                // Receive response from server
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + serverResponse);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
