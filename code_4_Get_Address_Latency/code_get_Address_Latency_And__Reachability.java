package Networking.code_4_Get_Address_Latency;

import java.io.IOException;
import java.net.*;

public class code_get_Address_Latency_And__Reachability
{
    public static void main(String[] args)
    {
        String host = "www.google.com";
        int timeout = 2000; // milliseconds

        try
        {
            InetAddress address = InetAddress.getByName(host);
            System.out.println("Resolved IP: " + address.getHostAddress());

            long startTime = System.currentTimeMillis();
            boolean reachable = address.isReachable(timeout);
            long endTime = System.currentTimeMillis();

            if (reachable)
            {
                System.out.println("Host is reachable");
                System.out.println("Approximate latency: " + (endTime - startTime) + " ms");
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
                System.out.println("Host is NOT reachable within " + timeout + " ms");
            }
        }
        catch (UnknownHostException e)
        {
            System.out.println("DNS resolution failed for host: " + host);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Network error while reaching host: " + host);
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
