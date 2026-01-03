package Networking.code_7_MyInterfaces;

/*
    This code is a complete inventory and classification of every network interface and
    IP address that exists on your machine at the OS level.

    "What networking identities does my system actually have right now?"

    That includes:
        ● Physical NICs
        ● Loopback
        ● Wi-Fi
        ● Bluetooth PAN
        ● Virtual Wi-Fi
        ● VPN tunnels
        ● Kernel adapters
        ● Debug adapters
        ● Disabled adapters
        ● Placeholder adapters
*/

/*
    Why InetAddress[] alone is insufficient?
    --> InetAddress[] only represents IPs, not interfaces

    Interfaces determine routing, not addresses
      Routing decisions are based on:
        ● Network interface
        ● Routing table
        ● Source IP
*/

import java.net.*;
import java.util.Enumeration;

public class Code_MyInterfaces
{
    public static void main(String[] args)
    {
        try
        {
            /*
                Enumeration is used here because the Java networking APIs are old, low-level,
                and OS-facing, and they still expose data using legacy collections.
            */
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements())
            {
                NetworkInterface iface = interfaces.nextElement();

                System.out.println("Interface: " + iface.getName());
                System.out.println("Display Name: " + iface.getDisplayName());
                System.out.println("Up: " + iface.isUp());
                System.out.println("Loopback: " + iface.isLoopback());
                System.out.println("Virtual: " + iface.isVirtual());

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                if (!addresses.hasMoreElements())
                {
                    System.out.println("\tNo addresses assigned");
                }

                while (addresses.hasMoreElements())
                {
                    InetAddress address = addresses.nextElement();

                    // IP version detection
                    String version =
                            ((address instanceof Inet4Address) ? "IPv4" :
                            address instanceof Inet6Address ? "IPv6" : "Unknown");

                    System.out.println("\tAddress (" + version + "): " + address.getHostAddress());

                    if (address.isLoopbackAddress())
                    {
                        System.out.println("\t\tType: Loopback");
                        /*
                            checks if the address is a loopback (e.g., 127.0.0.1)

                            an address by which the send information
                            comes back to the sender for testing the information
                        */
                    }
                    else if (address.isLinkLocalAddress())
                    {
                        System.out.println("\t\tType: Link-Local");
                        /*
                            Link-Local Address:

                            An IP address automatically assigned for communication
                            within the same local network segment when no router
                            or DHCP server is available.

                            IPv4 link-local range:
                              169.254.0.0 to 169.254.255.255

                            Link-local addresses are not routable beyond the local
                            network and cannot access the Internet.

                            In Java, identified using isLinkLocalAddress().
                        */
                    }
                    else if (address.isSiteLocalAddress())
                    {
                        System.out.println("\t\tType: Private (Site-Local)");
                        /*
                            Private (Site-Local) Address:

                            An IP address used only within a private network and not
                            routable on the public Internet.

                            IPv4 private ranges:
                              10.0.0.0     to  10.255.255.255
                              172.16.0.0   to  172.31.255.255
                              192.168.0.0  to  192.168.255.255

                            Such addresses access the Internet through NAT and are
                            identified in Java using isSiteLocalAddress().
                        */
                    }
                    else if (address.isMulticastAddress())
                    {
                        System.out.println("\t\tType: Multicast");
                        /*
                            a multicast address is an IP address used to send data
                            to multiple recipients (hosts) simultaneously within a
                            network
                        */
                    }
                    else
                    {
                        System.out.println("\t\tType: Global/Public");
                    }
                }

                System.out.println();
            }
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
    }
}
