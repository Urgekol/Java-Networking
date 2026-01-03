package Networking.code_1_Get_LocalHost_Info;

import java.net.*;
import java.util.Arrays;

public class code_get_LocalHostInfo
{
    public static void main(String[] args)
    {
        try
        {
            InetAddress local = InetAddress.getLocalHost();

            // Host name
            System.out.println("Host Name: " + local.getHostName());

            // IP address (human readable)
            System.out.println("IP Address: " + local.getHostAddress());

            // Raw byte representation (educational purpose)
            byte[] addressBytes = local.getAddress();
            System.out.println("Raw Address Bytes: " + Arrays.toString(addressBytes));

            /*
                Consider my IP address - 192.168.0.165

                Java's byte type is signed (-128 to 127).
                Thus, any IP component greater than 127 will appear
                as a negative number.

                The conversion to Java's signed byte format is as follows:
                    192 becomes 192 – 256 = -64
                    168 becomes 168 – 256 = -88
                    0 remains 0
                    165 becomes 165 – 256 = -91
            */

            // Address classification (ordered correctly)
            if (local.isLoopbackAddress())
            {
                System.out.println("Type: Loopback Address");
                /*
                    checks if the address is a loopback (e.g., 127.0.0.1)

                    an address by which the send information
                    comes back to the sender for testing the information
                */
            }
            else if (local.isLinkLocalAddress())
            {
                System.out.println("Type: Link-Local Address");
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
            else if (local.isSiteLocalAddress())
            {
                System.out.println("Type: Private (Site-Local) Address");
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
            else if (local.isMulticastAddress())
            {
                System.out.println("Type: Multicast Address");
                /*
                    a multicast address is an IP address used to send data
                    to multiple recipients (hosts) simultaneously within a
                    network
                */
            }
            else
            {
                System.out.println("Type: Public Address");
            }
        }
        catch (UnknownHostException e)
        {
            System.err.println("Unable to determine local host information");
            e.printStackTrace();
        }
    }
}