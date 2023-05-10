package data;

import com.google.gson.Gson;
import core.Game;

public class Stats {
    Game pong;
    Gson gson = new Gson();

    // Data
    private int round;
    private String[] playerAddresses = new String[2];
    private int [] scores = new int[2];


    public void handleStats(){

    }

    public Stats(Game pong){
        this.pong = pong;
    }

}
