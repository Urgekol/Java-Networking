package Networking.code_2_Get_Address_single_IP;

import java.net.*;
import java.util.Arrays;

public class code_get_Address_singleIP
{
    public static void main(String[] args)
    {
        // You can modify the 'host' variable to test with a
        // domain name (e.g., "www.google.com")
        // or a specific IP address (e.g., "8.8.8.8").
        String host = "www.google.com";

        try
        {
            InetAddress address = InetAddress.getByName(host);

            System.out.println("Input Host: " + host);
            System.out.println("Resolved Host Name: " + address.getHostName());
            System.out.println("Resolved IP Address: " + address.getHostAddress());

            // Raw byte representation (educational purpose only)
            byte[] rawBytes = address.getAddress();
            System.out.println("Raw Address Bytes: " + Arrays.toString(rawBytes));

            /*
                Consider the IP address - 142.250.195.100

                Java's byte type is signed (-128 to 127).
                Thus, any IP component greater than 127 will appear
                as a negative number.

                The conversion to Java's signed byte format is as follows:
                    142 becomes 142 – 256 = -114
                    250 becomes 250 – 256 = -6
                    195 remains 195 - 256 = -61
                    100 becomes 100 (Since 100 ≤ 127)
            */

            // IP version detection
            if (address instanceof Inet4Address)
            {
                System.out.println("IP Version: IPv4");
            }
            else if (address instanceof Inet6Address)
            {
                System.out.println("IP Version: IPv6");
            }

            // Classification order corrected
            if (address.isLoopbackAddress())
            {
                System.out.println("Type: Loopback Address");
                /*
                    checks if the address is a loopback (e.g., 127.0.0.1)

                    an address by which the send information
                    comes back to the sender for testing the information
                */
            }
            else if (address.isLinkLocalAddress())
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
            else if (address.isSiteLocalAddress())
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
            else if (address.isMulticastAddress())
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
            System.err.println("Host '" + host + "' could not be resolved.");
            e.printStackTrace();
        }
    }
}


