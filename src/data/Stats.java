package data;

import core.Game;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Stats {
    Game pong;
    Gson gson;

    // Data
    private int round;
    private int port;
    private String[] playerAddresses;
    private int [] scores;

    public Stats(Game pong, /*String[] playerAddresses,*/ int[] scores, int round, int port){
        gson = new Gson();
        this.pong = pong;
        //this.playerAddresses = playerAddresses;
        this.scores = scores;
        this.round = round;
        this.port = port;
    }

    public void handleStats(){
        String gameJson = "{\"isOnline\": \"" + pong.isOnline + "\" " + "}";
        //String playerAddrJson = "{\"Game\": \"" + pong + "\" " + "}";
        String scoresJson = "{\"score1\": \"" + scores[0] + "\", \"score2\": " + scores[1] + "}";
        String roundJson = "{\"round\": \"" + round + "\" " + "}";
        String portJson = "{\"port\": \"" + port + "\" " + "}";

        try (FileWriter writer = new FileWriter("stats/stats.json", true)) {
            writer.write("{\n");
            writer.write("\"game\": " + gameJson + ",\n");
            //writer.write("\"playerAddresses\": " + playerAddrJson + "\n");
            writer.write("\"scores\": " + scoresJson + ",\n");
            writer.write("\"round\": " + roundJson + "\n");
            writer.write("\"port\": " + portJson + ",\n");
            writer.write("},");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
