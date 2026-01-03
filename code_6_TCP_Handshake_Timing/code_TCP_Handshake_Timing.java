package Networking.code_6_TCP_Handshake_Timing;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/*
    TCP handshaking is the "three-step connection setup process" in which a client and a server exchange control packets
    (SYN, SYN-ACK, ACK) to establish a reliable, bidirectional TCP connection before any data is transmitted.
*/
public class code_TCP_Handshake_Timing
{
    public static void main(String[] args)
    {
        String host = "amazon.com";
        //  www.google.com --> apparently has only one IP (not a good example for this code)

        int port = 80;
        int timeout = 2000;

        try
        {
            InetAddress[] addresses = InetAddress.getAllByName(host);

            System.out.println("Host: " + host);
            System.out.println("Port: " + port);
            System.out.println("Resolved IPs: " + addresses.length);
            System.out.println();

            for (InetAddress addr : addresses)
            {
                System.out.println("Testing IP: " + addr.getHostAddress());

                long startTime = System.currentTimeMillis();

                try (Socket socket = new Socket())
                {
                    socket.connect(new InetSocketAddress(addr, port), timeout);     // the handshake connection

                    long endTime = System.currentTimeMillis();

                    System.out.println("TCP Connection: SUCCESS");
                    System.out.println("Handshake time: " + (endTime - startTime) + " ms");
                }
                catch (IOException e)
                {
                    long endTime = System.currentTimeMillis();

                    System.out.println("TCP Connection: FAILED");
                    System.out.println("Time spent: " + (endTime - startTime) + " ms");
                    System.out.println("Reason: " + e.getMessage());
                }

                System.out.println();
            }
        }
        catch (UnknownHostException e)
        {
            System.out.println("DNS resolution failed for host: " + host);
            e.printStackTrace();
        }
    }
}
