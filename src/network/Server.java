package network;

import core.*;

import javax.swing.*;
import java.net.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class Server extends Thread{
    private DatagramSocket socket;
    public int port;
    Game pong;
    private byte[] buf;

    public Server(Game pong, int port) throws SocketException, UnknownHostException {
        this.pong = pong;
        try {
            this.socket = new DatagramSocket(port);
            this.port = port;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String checkIps() {
        Enumeration<NetworkInterface> interfaces = null;
        Enumeration<InetAddress> addresses = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLinkLocalAddress() && !address.isLoopbackAddress() && address instanceof Inet4Address)
                        return address.getHostAddress();
                }
            }
        } catch (SocketException e) {
            System.out.println("Could not get network interfaces.");
            e.printStackTrace();
        }
        return null;
    }

    public void run(){
        while (true){
            buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            if (received.trim().equals("p1ready") || received.trim().equals("p2ready")){
                //pong.player1Ready = true;
                System.out.println(received);
                sendData(("noted!" + packet.getAddress().getHostAddress() + ":" + packet.getPort()).getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }

    public void sendData(byte[] buf, InetAddress address, int port) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
