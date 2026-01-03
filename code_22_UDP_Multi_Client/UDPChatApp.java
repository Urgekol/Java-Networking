/*
    UDPChatApp.java - Main entry point
*/
package Networking.code_22_UDP_Multi_Client;

import java.net.*;
import java.util.*;

public class UDPChatApp
{
    private static final int PORT = 1234;

    public static void main(String[] args) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter 1 for server, 2 for client: ");
        int mode = sc.nextInt();
        sc.nextLine();

        if (mode == 1)
        {
            System.out.println("Starting server on port " + PORT);
            new UDPChatServer(PORT).run();
        }
        else if (mode == 2)
        {
            System.out.println("Starting client (connects to localhost:" + PORT + ")");
            new UDPChatClient("localhost", PORT).run();
        }
        else
        {
            System.out.println("Invalid mode.");
        }
        sc.close();
    }
}

/*
    UDPChatServer.java - Handles incoming messages and broadcasts to all clients
*/

class UDPChatServer
{
    private final int port;
    private final List<InetAddress> clientAddress = new ArrayList<>();
    private final List<Integer> clientPorts = new ArrayList<>();

    public UDPChatServer(int port)
    {
        this.port = port;
    }

    public void run() throws Exception
    {
        DatagramSocket socket = new DatagramSocket(null);
        socket.setReuseAddress(true);
        socket.bind(new InetSocketAddress(port));

        new Thread(() -> receiveLoop(socket)).start();

        Scanner sc = new Scanner(System.in);
        String msg;

        while (true)
        {
            System.out.print("You: ");
            msg = sc.nextLine();

            if(msg.equalsIgnoreCase("exit"))
            {
                break;
            }
            broadcast(socket, msg, null, -1);
        }
        sc.close();
        socket.close();
    }

    private void receiveLoop(DatagramSocket socket)
    {
        byte[] buf = new byte[1024];

        try
        {
            while (true)
            {
                DatagramPacket pkt = new DatagramPacket(buf, buf.length);
                socket.receive(pkt);

                String msg = new String(pkt.getData(), 0, pkt.getLength());
                InetAddress addr = pkt.getAddress();
                int port = pkt.getPort();

                updateClients(addr, port);
                System.out.println("Received: " + msg);
                if(msg.equalsIgnoreCase("exit"))
                {
                    System.out.println("You: Bye");
                }

                broadcast(socket, msg, addr, port);
            }
        }
        catch (Exception ignored)
        {

        }
    }

    private synchronized void updateClients(InetAddress addr, int port)
    {
        if (!clientAddress.contains(addr) || !clientPorts.contains(port))
        {
            clientAddress.add(addr);
            clientPorts.add(port);
        }
    }

    private void broadcast(DatagramSocket socket, String msg,
                           InetAddress skipAddr, int skipPort)
    {
        byte[] data = msg.getBytes();
        for (int i = 0; i < clientAddress.size(); i++)
        {
            InetAddress addr = clientAddress.get(i);
            int port = clientPorts.get(i);
            if (addr.equals(skipAddr) && port == skipPort)
                continue;

            try
            {
                socket.send(new DatagramPacket(data, data.length, addr, port));
            }
            catch (Exception ignored)
            {

            }
        }
    }
}

/*
    UDPChatClient.java - Sends messages to the server and receives broadcasts
*/

class UDPChatClient
{
    private final String serverHost;
    private final int serverPort;

    public UDPChatClient(String host, int port)
    {
        this.serverHost = host;
        this.serverPort = port;
    }

    public void run() throws Exception
    {
        InetAddress serverAddr = InetAddress.getByName(serverHost);
        DatagramSocket socket = new DatagramSocket();
        Scanner sc = new Scanner(System.in);

        while (true)
        {
            System.out.print("You: ");
            String msg = sc.nextLine();
            if ("exit".equalsIgnoreCase(msg)) break;

            socket.send(new DatagramPacket(
                    msg.getBytes(),
                    msg.length(),
                    serverAddr,
                    serverPort
            ));

            byte[] buf = new byte[1024];
            DatagramPacket resp = new DatagramPacket(buf, buf.length);
            socket.receive(resp);

            System.out.println("Broadcast: " +
                    new String(resp.getData(), 0, resp.getLength()));
        }
        sc.close();
        socket.close();
    }
}
