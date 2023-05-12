package network;

import core.Game;
import core.Platform;

import java.net.*;
import java.io.IOException;
import java.util.Objects;

public class Client extends Thread{
    private DatagramSocket socket;
    private InetAddress address;
    private String serverAddress;
    private Game pong;
    public int port;
    private String[] receivedTmp = new String[1];
    private byte[] buf;

    public Client(String ip, int port, Game pong){
        this.pong = pong;
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
            try {
                handlePacket(received, packet.getAddress(), packet.getPort());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public void handlePacket(String received, InetAddress address, int port) throws IOException {
        if (received.trim().equals("connected")){
            serverAddress = address.getHostAddress();
        }
        else if (address.getHostAddress().equals(serverAddress)){
            switch (received.trim()) {
                case "move1up" -> pong.platform1.posY -= 10;
                case "move1down" -> pong.platform1.posY += 10;
                case "move2up" -> pong.platform2.posY -= 10;
                case "move2down" -> pong.platform2.posY += 10;
                case "p1ready" -> pong.player1Ready = true;
                case "p2ready" -> pong.player2Ready = true;
                case "disconnect1" -> System.out.println("Player 1 disconnected, the server is shutting down.");
                case "disconnect2" -> System.out.println("Player 2 disconnected.");
                case "1wonround" -> pong.ball.endOnlineRound(true, false);
                case "2wonround" -> pong.ball.endOnlineRound(false, false);
            }
        }
        receivedTmp[0] = received.trim();

        if (Objects.equals(receivedTmp[0].split(":")[0], "ball")) {
            pong.ball.posX = Double.parseDouble(receivedTmp[0].split(":")[1]);
            pong.ball.posY = Double.parseDouble(receivedTmp[0].split(":")[2]);
        }
    }
}
