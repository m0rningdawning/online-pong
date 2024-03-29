package network;

import core.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

public class Server extends Thread{
    private final int MAX_PLAYERS = 2;
    private DatagramSocket socket;
    public int port;
    public int connectedPlayers = 0;
    public String[] playerAddresses = new String[MAX_PLAYERS];
    private String[] receivedTmp = new String[1];
    Game pong;
    byte[] buf;

    public Server(Game pong, int port, boolean global) throws IOException {
        this.pong = pong;
        if (global) {
            this.port = port;
            this.socket = new DatagramSocket(port, InetAddress.getByName(checkPubIp()));
        } else {
            this.port = port;
            this.socket = new DatagramSocket(port);
        }
    }

    public String checkIps() {
        Enumeration<NetworkInterface> interfaces;
        Enumeration<InetAddress> addresses;
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

    public String checkPubIp() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
        return in.readLine().trim();
    }

    public void handlePacket(String received, InetAddress address, int port) throws UnknownHostException {
        if (received.trim().equals("connect")){
            if (connectedPlayers < MAX_PLAYERS){
                connectedPlayers++;
                pong.platform1.score = pong.platform2.score = 0;
                playerAddresses[connectedPlayers - 1] = address.getHostAddress() + ":" + port;
                System.out.println("Player " + connectedPlayers + " connected.");
                sendData(("connected").getBytes(), address, port);
            }
            else{
                System.out.println("Player " + connectedPlayers + " tried to connect.");
                sendData("full".getBytes(), address, port);
            }
        }

        else if (address.getHostAddress().equals(playerAddresses[0].split(":")[0]) || address.getHostAddress().equals(playerAddresses[1].split(":")[0])) {
            receivedTmp[0] = received.trim();

            if (Objects.equals(receivedTmp[0].split(":")[0], "ball")) {
                sendData(receivedTmp[0].getBytes(), InetAddress.getByName(playerAddresses[1].split(":")[0]), Integer.parseInt(playerAddresses[1].split(":")[1]));
            }

            switch (received.trim()) {
                case "move1up" -> sendData("move1up".getBytes(), InetAddress.getByName(playerAddresses[1].split(":")[0]), Integer.parseInt(playerAddresses[1].split(":")[1]));
                case "move1down" -> sendData("move1down".getBytes(), InetAddress.getByName(playerAddresses[1].split(":")[0]), Integer.parseInt(playerAddresses[1].split(":")[1]));
                case "move2up" -> sendData("move2up".getBytes(), InetAddress.getByName(playerAddresses[0].split(":")[0]), Integer.parseInt(playerAddresses[0].split(":")[1]));
                case "move2down" -> sendData("move2down".getBytes(), InetAddress.getByName(playerAddresses[0].split(":")[0]), Integer.parseInt(playerAddresses[0].split(":")[1]));
                case "p1ready" -> {
                    if (playerAddresses[1] != null)
                        sendData("p1ready".getBytes(), InetAddress.getByName(playerAddresses[1].split(":")[0]), Integer.parseInt(playerAddresses[1].split(":")[1]));
                }
                case "p2ready" -> {
                    if (playerAddresses[0] != null)
                        sendData("p2ready".getBytes(), InetAddress.getByName(playerAddresses[0].split(":")[0]), Integer.parseInt(playerAddresses[0].split(":")[1]));
                }
                case "disconnect" -> {
                    if (address.getHostAddress().equals(playerAddresses[0].split(":")[0])){
                        playerAddresses[0] = null;
                        if (playerAddresses[1] != null)
                            sendData("disconnect1".getBytes(), InetAddress.getByName(playerAddresses[1].split(":")[0]), Integer.parseInt(playerAddresses[1].split(":")[1]));
                    }
                    else {
                        playerAddresses[1] = null;
                        sendData("disconnect2".getBytes(), InetAddress.getByName(playerAddresses[0].split(":")[0]), Integer.parseInt(playerAddresses[0].split(":")[1]));
                    }
                    connectedPlayers--;
                }
            }
        }
    }

    public void run(){
        while (true){
            buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String received = new String(packet.getData(), 0, packet.getLength());

            try {
                handlePacket(received, packet.getAddress(), packet.getPort());
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
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
