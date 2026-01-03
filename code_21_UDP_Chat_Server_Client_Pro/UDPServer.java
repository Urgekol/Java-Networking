package Networking.code_21_UDP_Chat_Server_Client_Pro;

import java.net.*;
import java.util.Scanner;

public class UDPServer
{
    public static final int PORT = 5555;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("UDP Chat Server starting on port " + PORT);

        try (DatagramSocket serverSocket = new DatagramSocket(PORT))
        {
            while (true)
            {
                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);

                serverSocket.receive(receivePacket);

                String clientMsg = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength());

                if ("exit".equalsIgnoreCase(clientMsg))
                {
                    System.out.println("Client requested exit");
                    break;
                }

                System.out.println("Client: " + clientMsg);

                System.out.print("Server: ");
                String reply = sc.nextLine();
                byte[] sendBuffer = reply.getBytes();

                DatagramPacket sendPacket =
                        new DatagramPacket(
                                sendBuffer,
                                sendBuffer.length,
                                receivePacket.getAddress(),
                                receivePacket.getPort());

                serverSocket.send(sendPacket);
            }
        }
        catch (Exception e)
        {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
