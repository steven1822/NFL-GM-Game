package League;

import Coaching.Coach;
import Players.Player;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SplittableRandom;


//Class modeling a football team
public class Team {
//Teams name
    private String teamName;
    //City that team is located in
    private String cityName;
    //League.Team color 1
    private Color color1;
    //League.Team color 2
    private Color color2;
    //League.Team color 3
    private Color color3;

    //ArrayList that holds all the players on a team
    private ArrayList<Player> roster;

    //The coach of the team
    private Coach coach;

    //Starting Offense for the team
    private HashMap<String,Player> startingO;
    //Starting Defense for a 3-4 defense
    private HashMap<String, Player> D3_4startingD;
    //Starting Defense for a 4-3 defense
    private HashMap<String,Player> D4_3startingD;
    //Filler player to set keys for starting lineups
    private Player blankPlayer = new Player("Blank","Player","WR");

    public Team(){
        startingO = new HashMap<String,Player>();
        D3_4startingD = new HashMap<String,Player>();
        D4_3startingD = new HashMap<String,Player>();
        //Creates the Keys for the starting Offense Hash Map
        this.startingO.put("QB",blankPlayer);
        this.startingO.put("WR1",blankPlayer);
        this.startingO.put("WR2",blankPlayer);
        this.startingO.put("WR3",blankPlayer);
        this.startingO.put("TE",blankPlayer);
        this.startingO.put("LT",blankPlayer);
        this.startingO.put("RT",blankPlayer);
        this.startingO.put("LG",blankPlayer);
        this.startingO.put("RG",blankPlayer);
        this.startingO.put("G",blankPlayer);
        this.startingO.put("RB",blankPlayer);
        //Creates the keys for the Starting 3-4 defense hash map
        this.D3_4startingD.put("MLB1",blankPlayer);
        this.D3_4startingD.put("MLB2",blankPlayer);
        this.D3_4startingD.put("OLB",blankPlayer);
        this.D3_4startingD.put("EDGE",blankPlayer);
        this.D3_4startingD.put("DT",blankPlayer);
        this.D3_4startingD.put("LE",blankPlayer);
        this.D3_4startingD.put("RE",blankPlayer);
        this.D3_4startingD.put("CB1",blankPlayer);
        this.D3_4startingD.put("CB2",blankPlayer);
        this.D3_4startingD.put("FS",blankPlayer);
        this.D3_4startingD.put("SS",blankPlayer);
        //Creates the keys for the Starting 4-3 defense hash map
        this.D4_3startingD.put("MLB",blankPlayer);
        this.D4_3startingD.put("ROLB",blankPlayer);
        this.D4_3startingD.put("LOLB",blankPlayer);
        this.D4_3startingD.put("EDGE1",blankPlayer);
        this.D4_3startingD.put("EDGE2",blankPlayer);
        this.D4_3startingD.put("DT1",blankPlayer);
        this.D4_3startingD.put("DT2",blankPlayer);
        this.D4_3startingD.put("CB1",blankPlayer);
        this.D4_3startingD.put("CB2",blankPlayer);
        this.D4_3startingD.put("FS",blankPlayer);
        this.D4_3startingD.put("SS",blankPlayer);






    }


    //Getters and Setters
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public Color getColor3() {
        return color3;
    }

    public void setColor3(Color color3) {
        this.color3 = color3;
    }

    public ArrayList<Player> getRoster() {
        return roster;
    }

    public void setRoster(ArrayList<Player> roster) {
        this.roster = roster;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public HashMap<String, Player> getStartingO() {
        return startingO;
    }

    public void setStartingO(HashMap<String, Player> startingO) {
        this.startingO = startingO;
    }

    public HashMap<String, Player> getD3_4startingD() {
        return D3_4startingD;
    }

    public void setD3_4startingD(HashMap<String, Player> d3_4startingD) {
        D3_4startingD = d3_4startingD;
    }

    public HashMap<String, Player> getD4_3startingD() {
        return D4_3startingD;
    }

    public void setD4_3startingD(HashMap<String, Player> d4_3startingD) {
        D4_3startingD = d4_3startingD;
    }
}
