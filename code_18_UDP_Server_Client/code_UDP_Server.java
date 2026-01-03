package Networking.code_18_UDP_Server_Client;

import java.io.*;
import java.net.*;

public class code_UDP_Server
{
    static int PORT = 5555;

    public static void main(String[] args)
    {
        byte[] buffer = new byte[1024];

        try (DatagramSocket serverSocket = new DatagramSocket(PORT))
        {
            System.out.println("Listening at port: " + PORT);

            while (true)
            {
                System.out.println("Getting message from client.....");
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);

                String clientMessage = new String(
                        buffer,                         // your receive buffer
                        0,                              // start at index 0
                        receivePacket.getLength()       // use exactly the number of bytes received
                );
                System.out.println("Received from client: " + clientMessage + "\n");

                byte[] responseBytes = clientMessage.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes,
                        responseBytes.length,
                        receivePacket.getAddress(),
                        receivePacket.getPort()
                );

                serverSocket.send(responsePacket);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
