package Networking.code_20_UDP_Chat_Server_Client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient
{
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 5555;

    public static void main(String[] args)
    {
        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner consoleIn = new Scanner(System.in))
        {

            byte[] sendBuffer;
            byte[] receiveBuffer = new byte[1024];

            System.out.println("Enter message (type 'exit' to quit): ");
            while (true)
            {
                // Read message from user
                System.out.print("You: ");
                String message = consoleIn.nextLine();
                if ("exit".equalsIgnoreCase(message))
                {
                    System.out.println("Exiting chat.");
                    break;
                }

                // Send to server
                sendBuffer = message.getBytes();
                InetAddress serverAddr = InetAddress.getByName(SERVER_HOST);
                DatagramPacket sendPacket =
                        new DatagramPacket(sendBuffer, sendBuffer.length,
                                serverAddr, SERVER_PORT);
                clientSocket.send(sendPacket);

                // Receive server's reply
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                String response = new String(
                        receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Reply: " + response);
            }
        } catch (IOException e)
        {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
