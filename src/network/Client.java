package network;

import java.net.*;
import java.io.IOException;

public class Client {
    private DatagramSocket socket;
    private InetAddress address;
    public int port;

    private byte[] buf;

    public Client(String ip, int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName(ip);
        this.port = port;
    }

    public String sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 50000);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }
}
