package network;

import java.net.*;
import java.io.IOException;

public class Client extends Thread{
    private DatagramSocket socket;
    private InetAddress address;
    public int port;
    private byte[] buf;

    public Client(String ip, int port) throws SocketException, UnknownHostException {
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName(ip);
            this.port = port;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            System.out.println(received);
        }
    }

    public void sendData(byte[] buf) {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
