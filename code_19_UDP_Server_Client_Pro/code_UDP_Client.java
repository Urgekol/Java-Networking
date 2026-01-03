package Networking.code_19_UDP_Server_Client_Pro;

import java.net.*;
import java.util.Scanner;

public class code_UDP_Client
{
    private static final int PORT = 5555;
    private static final String SERVER = "localhost";
    private static final int BUFFER_SIZE = 1024;
    private static final int TIMEOUT_MS = 3000;

    public static void main(String[] args)
    {
        try (DatagramSocket socket = new DatagramSocket())
        {
            socket.setSoTimeout(TIMEOUT_MS);
            InetAddress serverAddress = InetAddress.getByName(SERVER);
            Scanner sc = new Scanner(System.in);

            System.out.println("UDP Client ready (type \"exit\" to quit)");

            while (true)
            {
                System.out.print("Client: ");
                String input = sc.nextLine();

                if ("exit".equalsIgnoreCase(input))
                    break;

                byte[] sendBytes = input.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(
                                sendBytes,
                                sendBytes.length,
                                serverAddress,
                                PORT
                        );

                socket.send(sendPacket);

                byte[] receiveBuffer = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);

                try
                {
                    socket.receive(receivePacket);

                    String response = new String(
                            receivePacket.getData(),
                            0,
                            receivePacket.getLength()
                    );

                    System.out.println("Server: " + response);
                }
                catch (SocketTimeoutException e)
                {
                    System.out.println("No response from server (timeout)");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println("Client closed");
    }
}
