package Networking.code_3_Get_All_Addresses;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
    1. A single hostname maps to multiple IPs  (One hostname → many IPs)
    2. IPv4 and IPv6 can coexist
    3. getByName() hides reality by returning only one address
*/

public class code_Get_All_Addresses
{
    public static void main(String[] args)
    {
        String host = "amazon.com";
        //  www.google.com --> apparently has only one IP (not a good example for this code)

        try
        {
            System.out.println("Host: " + host);
            System.out.println("Resolving ALL addresses...\n");

            InetAddress[] addresses = InetAddress.getAllByName(host);

            System.out.println("Total addresses found: " + addresses.length);
            System.out.println();

            int index = 1;
            for (InetAddress addr : addresses)
            {
                System.out.println("Address #" + index);
                System.out.println("IP: " + addr.getHostAddress());

                // IP version detection
                if (addr instanceof Inet4Address)
                {
                    System.out.println("IP Version: IPv4");
                }
                else if (addr instanceof Inet6Address)
                {
                    System.out.println("IP Version: IPv6");
                }


                if (addr.isLoopbackAddress())
                {
                    System.out.println("\t\tType: Loopback");
                    /*
                        checks if the address is a loopback (e.g., 127.0.0.1)

                        an address by which the send information
                        comes back to the sender for testing the information
                    */
                }
                else if (addr.isLinkLocalAddress())
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
                else if (addr.isSiteLocalAddress())
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
                else if (addr.isMulticastAddress())
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

                System.out.println();
                index++;
            }

            // Comparison with getByName()
            InetAddress single = InetAddress.getByName(host);
            System.out.println("getByName() returned only: " + single.getHostAddress());

        }
        catch (UnknownHostException e)
        {
            System.err.println("Failed to resolve host: " + host);
            e.printStackTrace();
        }
    }
}
