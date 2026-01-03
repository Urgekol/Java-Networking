package Networking.code_21_UDP_Chat_Server_Client_Pro;

import java.net.*;
import java.util.Scanner;

public class UDPClient
{
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 5555;
    private static final int BUFFER_SIZE = 1024;
    private static final int TIMEOUT_MS = 3000;

    public static void main(String[] args)
    {
        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner consoleIn = new Scanner(System.in))
        {
            clientSocket.setSoTimeout(TIMEOUT_MS);
            InetAddress serverAddr = InetAddress.getByName(SERVER_HOST);

            System.out.println("UDP Chat Client started (type 'exit' to quit)");

            while (true)
            {
                System.out.print("You: ");
                String message = consoleIn.nextLine();

                byte[] sendBuffer = message.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(
                                sendBuffer,
                                sendBuffer.length,
                                serverAddr,
                                SERVER_PORT);

                clientSocket.send(sendPacket);

                if ("exit".equalsIgnoreCase(message))
                    break;

                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);

                try
                {
                    clientSocket.receive(receivePacket);

                    String response = new String(
                            receivePacket.getData(),
                            0,
                            receivePacket.getLength());

                    System.out.println("Server: " + response);
                }
                catch (SocketTimeoutException e)
                {
                    System.out.println("No reply from server (timeout)");
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
