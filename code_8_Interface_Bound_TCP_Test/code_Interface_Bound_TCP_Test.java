package Networking.code_8_Interface_Bound_TCP_Test;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/*
    Why InetAddress[] alone is insufficient?
    --> InetAddress[] only represents IPs, not interfaces

    Interfaces determine routing, not addresses
      Routing decisions are based on:
        ● Network interface
        ● Routing table
        ● Source IP
*/

public class code_Interface_Bound_TCP_Test
{
    public static void main(String[] args)
    {
        String remoteHost = "amazon.com";
        //  www.google.com --> apparently has only one IP (not a good example for this code)

        int remotePort = 80;
        int timeout = 2000;

        try
        {
            InetAddress[] remoteAddresses = InetAddress.getAllByName(remoteHost);

            /*
                Enumeration is used here because the Java networking APIs are old, low-level,
                and OS-facing, and they still expose data using legacy collections.
            */
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements())
            {
                NetworkInterface iface = interfaces.nextElement();

                if (!iface.isUp() || iface.isLoopback())
                {
                    continue;
                }

                Enumeration<InetAddress> localAddresses = iface.getInetAddresses();

                while (localAddresses.hasMoreElements())
                {
                    InetAddress localAddr = localAddresses.nextElement();

                    if (!(localAddr instanceof Inet4Address))
                    {
                        continue;
                    }

                    System.out.println("Local Interface: " + iface.getName());
                    System.out.println("Local IP: " + localAddr.getHostAddress());

                    for (InetAddress remoteAddr : remoteAddresses)
                    {
                        System.out.println("  -> Connecting to " + remoteAddr.getHostAddress());

                        long start = System.currentTimeMillis();

                        try (Socket socket = new Socket())
                        {
                            socket.bind(new InetSocketAddress(localAddr, 0));

                            socket.connect(new InetSocketAddress(remoteAddr, remotePort), timeout);

                            long end = System.currentTimeMillis();

                            System.out.println("     SUCCESS in " + (end - start) + " ms");
                        }
                        catch (IOException e)
                        {
                            long end = System.currentTimeMillis();

                            System.out.println("     FAILED after " + (end - start) + " ms");
                            System.out.println("     Reason: " + e.getMessage());
                        }
                    }

                    System.out.println();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
