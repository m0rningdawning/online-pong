package data;

import core.Game;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Stats {
    Game pong;
    Gson gson;
    File file = new File("stats/offlineStats.json");
    File onlineFile = new File("stats/onlineStats.json");

    // Data
    private int round;
    private int port;
    private String[] playerAddresses;
    private int [] scores;

    public Stats(Game pong, String[] playerAddresses, int[] scores, int round, int port){
        gson = new Gson();
        this.pong = pong;
        this.playerAddresses = playerAddresses;
        this.scores = scores;
        this.round = round;
        this.port = port;
    }

    public void prepareStats(boolean eof) {
        if (pong.isOnline){
            try (FileWriter writer = new FileWriter("stats/onlineStats.json", true)){
                if (onlineFile.length() == 0) {
                    writer.write("[\n");
                }
                if (onlineFile.length() != 0 && !eof) {
                    writer.write(",\n");
                }
                if (eof) {
                    writer.write("\n]");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else{
            try (FileWriter writer = new FileWriter("stats/offlineStats.json", true)){
                if (file.length() == 0) {
                    writer.write("[\n");
                }
                if (file.length() != 0 && !eof) {
                    writer.write(",\n");
                }
                if (eof) {
                    writer.write("\n]");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleStats() {
        String gameJson = "{\"isOnline\": \"" + pong.isOnline + "\" " + "}";
        String scoresJson = "{\"score1\": \"" + scores[0] + "\", \"score2\": \"" + scores[1] + "\" " + "}";
        String roundJson = "{\"round\": \"" + round + "\" " + "}";

        try (FileWriter writer = new FileWriter("stats/offlineStats.json", true)) {
            writer.write("\t{\n");
            writer.write("\t\t\"game\": " + gameJson + ",\n");
            writer.write("\t\t\"scores\": " + scoresJson + ",\n");
            writer.write("\t\t\"round\": " + roundJson + "\n");
            writer.write("\t}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleOnlineStats(){
        String gameJson = "{\"isOnline\": \"" + pong.isOnline + "\" " + "}";
        String playerAddrJson = "{\"server\": \"" + pong.isServer + "\" " + "}";
        String scoresJson = "{\"score1\": \"" + scores[0] + "\", \"score2\": \"" + scores[1] + "\" " + "}";
        String roundJson = "{\"round\": \"" + round + "\" " + "}";
        String portJson = "{\"port\": \"" + port + "\" " + "}";

        try (FileWriter writer = new FileWriter("stats/onlineStats.json", true)) {
            writer.write("\t{\n");
            writer.write("\t\t\"game\": " + gameJson + ",\n");
            writer.write("\t\t\"playerAddresses\": " + playerAddrJson + ",\n");
            writer.write("\t\t\"scores\": " + scoresJson + ",\n");
            writer.write("\t\t\"round\": " + roundJson + ",\n");
            writer.write("\t\t\"port\": " + portJson + "\n");
            writer.write("\t}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
