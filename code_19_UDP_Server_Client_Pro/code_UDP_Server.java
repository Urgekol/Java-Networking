package Networking.code_19_UDP_Server_Client_Pro;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class code_UDP_Server
{
    private static final int PORT = 5555;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args)
    {
        System.out.println("UDP Server listening on port " + PORT);

        try (DatagramSocket serverSocket = new DatagramSocket(PORT))
        {
            while (true)
            {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket =
                        new DatagramPacket(buffer, buffer.length);

                serverSocket.receive(receivePacket);

                String message = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength()
                );

                System.out.println("Received from "
                        + receivePacket.getAddress() + ":"
                        + receivePacket.getPort()
                        + " -> " + message);

                byte[] responseBytes = message.getBytes();
                DatagramPacket responsePacket =
                        new DatagramPacket(
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
            throw new RuntimeException(e);
        }
    }
}
