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

    public void prepareStats(boolean eof, boolean clear) {
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
            if (clear) {
                FileWriter writer1 = new FileWriter("stats/offlineStats.json", false);
                writer1.write("");
                writer1.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleStats() {
        String gameJson = "{\"isOnline\": \"" + pong.isOnline + "\" " + "}";
        String scoresJson = "{\"score1\": \"" + scores[0] + "\", \"score2\": \"" + scores[1] + "\" " + "}";
        String roundJson = "{\"round\": \"" + round + "\" " + "}";

        try (FileWriter writer = new FileWriter("stats/offlineStats.json", true)) {
            writer.write("\t{\n");
            writer.write("\t\t\"game\": " + gameJson + ",\n");
            //writer.write("\"playerAddresses\": " + playerAddrJson + "\n");
            writer.write("\t\t\"scores\": " + scoresJson + ",\n");
            writer.write("\t\t\"round\": " + roundJson + "\n");
            //writer.write("\"port\": " + portJson + ",\n");
            writer.write("\t}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleOnlineStats(){
        String gameJson = "{\"isOnline\": \"" + pong.isOnline + "\" " + "}";
        //String playerAddrJson = "{\"Game\": \"" + pong + "\" " + "}";
        String scoresJson = "{\"score1\": \"" + scores[0] + "\", \"score2\": \"" + scores[1] + "\" " + "}";
        String roundJson = "{\"round\": \"" + round + "\" " + "}";
        String portJson = "{\"port\": \"" + port + "\" " + "}";

        try (FileWriter writer = new FileWriter("stats/stats.json", true)) {
            writer.write("{\n");
            writer.write("\"game\": " + gameJson + ",\n");
            //writer.write("\"playerAddresses\": " + playerAddrJson + "\n");
            writer.write("\"scores\": " + scoresJson + ",\n");
            writer.write("\"round\": " + roundJson + ",\n");
            writer.write("\"port\": " + portJson + ",\n");
            //writer.write("},");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
