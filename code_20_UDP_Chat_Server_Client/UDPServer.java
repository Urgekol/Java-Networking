package Networking.code_20_UDP_Chat_Server_Client;

import java.net.*;
import java.io.*;
import java.util.*;

public class UDPServer
{
    public static final int PORT = 5555;

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("UDP Chat Server starting on port " + PORT);

        try (DatagramSocket serverSocket = new DatagramSocket(PORT))
        {
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true)
            {
                // Receive client's message
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String clientMsg = new String(receivePacket.getData(),
                                              0,
                                              receivePacket.getLength());

                System.out.println("Reply: " + clientMsg);

                // Prompt server operator for reply
                System.out.print("Your chat: ");
                String reply = sc.nextLine();
                sendBuffer = reply.getBytes();

                // Send reply back to client
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer,
                                                               sendBuffer.length,
                                                               clientAddress,
                                                               clientPort);
                serverSocket.send(sendPacket);
            }
        }
        catch (IOException e)
        {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
