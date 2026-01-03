package Networking.code_5_Compare_All_IP_Reachability;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class code_Compare_All_IP_Reachability
{
    public static void main(String[] args)
    {
        String host = "amazon.com";
        //  www.google.com --> apparently has only one IP (not a good example for this code)

        int timeout = 2000;

        try
        {
            InetAddress[] addresses = InetAddress.getAllByName(host);

            System.out.println("Host: " + host);
            System.out.println("Total resolved IPs: " + addresses.length);
            System.out.println();

            for (InetAddress address : addresses)
            {
                System.out.println("Testing IP: " + address.getHostAddress());

                long start = System.currentTimeMillis();
                boolean reachable = address.isReachable(timeout);
                long end = System.currentTimeMillis();

                if (reachable)
                {
                    System.out.println("Reachable: YES");
                    System.out.println("Approximate latency: " + (end - start) + " ms");
                    /*
                        What reachability guarantees?

                        ● The IP address exists
                        ● A route to the host exists
                        ● Packets can leave your machine
                        ● Some response came back
                    */
                }
                else
                {
                    System.out.println("Reachable: NO (timeout " + timeout + " ms)");
                }

                System.out.println();
            }
        }
        catch (UnknownHostException e)
        {
            System.out.println("DNS resolution failed for host: " + host);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Network error while testing reachability");
            e.printStackTrace();
        }
    }
}

/*
    Why reachability is weak but still useful

    Reachability is good for:
        ● Early diagnostics
        ● Debugging routing problems
        ● Checking “is there some path”

    It is useless for:
        ● Verifying service availability
        ● Measuring performance
        ● Making business logic decisions
*/


/*
    Why ALL Amazon IPs show as unreachable?

    Reason 1: ICMP is blocked (by design)
        Amazon blocks ICMP Echo Requests (ping) on most of their infrastructure.

    Reason 2: TCP fallback also fails
        When ICMP fails, Java may try a TCP-based probe.
        Amazon:
            ● Does not expose TCP Echo services
            ● Does not allow random probe connections

    Reason 3: Firewalls drop packets silently
        Amazon firewalls often:
            ● Drop packets instead of rejecting them
            ● Do not send RST or ICMP unreachable
*/