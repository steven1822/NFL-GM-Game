package Schedule;


import Coaching.DScheme;
import League.Team;
import Players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//class modeling a single football play
public class Play {




    //all possible teams
    public enum PlayType{
        //Offensive Plays
        //throwing plays
        DEEP_THROW, MED_THROW, SHORT_THROW, QB_SCRAMBLE,
        //Running plays
        INSIDE_RUN, OUTSIDE_RUN,

        //Defensive plays

        //Zone Plays
        ZONE_BLITZ, ZONE,
        //Man Plays
        MAN_BLITZ, MAN

    }

    private Player empty = new Player("EMPTY","PLAYER","QB");

    public enum PlayOutcome{
        OUT_OF_BOUNDS,TACKLE, FUMBLE,PASS_INCOMPLETE, INTERCEPTION, FUMBLE_RECOVERY;
    }
    private Team oTeam;
    private Team dTeam;

    private PlayType OPlayType;
    private PlayType DPlayType;

    private PlayOutcome playOutcome = null ;

    //How much time it took from snap of this play to the snap of the next play in seconds
    private int timeOfplay = 0;

    //amount of yards that the offense gained; can be negative
    private int yardsGained = 0;

    //boolean for if there was a turnover on the play
    private boolean turnOverOnPlay = false;


    private Player tackler = null;
    private Player ballHandler;
    private Player fumbler = null;

    private Player forcedFumbler = null;



    public Play(Team oTeam, Team dTeam) {
        this.oTeam = oTeam;
        this.dTeam = dTeam;


        //decidePlay();
        //insideRun();




    }

    public int insideRun(){
        //this method will simulate an inside run play
        this.ballHandler = getOteamPlayer("RB");

        Random rand = new Random();

        int yard = -3;

        char dir;

        //decides if this play is going left or right
        if (rand.nextInt(1,11)> 5){
            dir = 'L';
        }
        else {
            dir = 'R';
        }

        switch (dTeam.getCoach().getDeffensiveScheme().getdForm()){
            case D3_4:
                //1st level
                int base1 = 5;
                String dtShed = shedBlockAttempt(getOteamPlayer("C"),getDteamPlayer("DT"),base1);
                String leShed = shedBlockAttempt(getOteamPlayer("LT"),getDteamPlayer("LE"),base1);
                String reShed = shedBlockAttempt(getOteamPlayer("RT"),getDteamPlayer("RE"),base1);

                while(yard < 3){
                    yard += 1;
                    base1 += 2;

                    switch (dir){
                        case 'L':

                            if (dtShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("DT"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("C"),getDteamPlayer("DT"),base1);
                            }
                            if (leShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("LE"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("LT"),getDteamPlayer("LE"),base1);
                            }
                            if (reShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("RE"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("RT"),getDteamPlayer("RE"),base1);
                            }
                        case 'R' :
                            if (dtShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("DT"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("C"),getDteamPlayer("DT"),base1);
                            }
                            if (reShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("RE"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("RT"),getDteamPlayer("RE"),base1);
                            }
                            if (leShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("LE"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("LT"),getDteamPlayer("LE"),base1);
                            }


                    }

                    //Tests running backs vision

                    if (rbVisionTest() ){
                        yard += 1;
                    }




                }

                //second level
                int base2 = 20;

                base2 += 3;
                String mlb1Shed = shedBlockAttempt(getOteamPlayer("LG"),getDteamPlayer("MLB1"),base2);
                String mlb2Shed = shedBlockAttempt(getOteamPlayer("RG"),getDteamPlayer("MLB2"),base2);

                while(yard < 10){
                    yard += 1;
                    if (mlb1Shed.equals("Block Shed")){
                        tackleAttempt(this.ballHandler,getDteamPlayer("MLB1"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        mlb1Shed =  shedBlockAttempt(getOteamPlayer("LG"),getDteamPlayer("MLB1"),base2);
                    }

                    if (mlb2Shed.equals("Block Shed")){
                        tackleAttempt(this.ballHandler,getDteamPlayer("MLB2"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        mlb2Shed = shedBlockAttempt(getOteamPlayer("RG"),getDteamPlayer("MLB2"),base2);
                    }

                    if (rbVisionTest()){
                        yard += 1;
                    }

                }

                // level 3
                int base = 50;
                boolean fsPursuit = pursuit(getDteamPlayer("FS"),base);
                boolean ssPursuit = pursuit(getDteamPlayer("SS"),base);

                while(yard < 99){
                    if (fsPursuit){
                        tackleAttempt(this.ballHandler,getDteamPlayer("FS"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        base +=20;
                        fsPursuit = pursuit(getDteamPlayer("FS"),base);
                    }
                    if (ssPursuit){
                        tackleAttempt(this.ballHandler,getDteamPlayer("SS"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        base += 20;
                        fsPursuit = pursuit(getDteamPlayer("SS"),base);
                    }
                    yard += 3;
                }

                return yard;


            case D4_3:


                //1st lvl
                base1 = 5;


                while(yard < 3){
                    yard += 1;
                    base1 += 2;

                    String dt2Shed;

                    switch (dir){
                        case 'L':

                            dtShed = shedBlockAttempt(getOteamPlayer("LG"),getDteamPlayer("DT1"),base1);
                            dt2Shed = shedBlockAttempt(getOteamPlayer("RG"),getDteamPlayer("DT2"),base1);


                            if (dtShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("DT1"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("LG"),getDteamPlayer("DT1"),base1);
                            }
                            if (dt2Shed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("DT2"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dt2Shed = shedBlockAttempt(getOteamPlayer("RG"),getDteamPlayer("DT2"),base1);
                            }

                        case 'R' :
                            dtShed = shedBlockAttempt(getOteamPlayer("RG"),getDteamPlayer("DT1"),base1);
                            dt2Shed = shedBlockAttempt(getOteamPlayer("LG"),getDteamPlayer("DT2"),base1);



                            if (dtShed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("DT1"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dtShed = shedBlockAttempt(getOteamPlayer("RG"),getDteamPlayer("DT1"),base1);
                            }
                            if (dt2Shed.equals("Block Shed")){
                                tackleAttempt(getOteamPlayer("RB"),getDteamPlayer("DT2"));
                                if (this.playOutcome != null){
                                    return yard;
                                }
                            }
                            else {
                                dt2Shed = shedBlockAttempt(getOteamPlayer("LG"),getDteamPlayer("DT2"),base1);
                            }



                    }

                    //Tests running backs vision

                    if (rbVisionTest() ){
                        yard += 1;
                    }




                }

                // 2nd lvl
                //second level
                base2 = 10;

                base2 += 3;
                String mlbShed = shedBlockAttempt(getOteamPlayer("C"),getDteamPlayer("MLB"),base2);
                String rolbShed = shedBlockAttempt(getOteamPlayer("RT"),getDteamPlayer("ROLB"),base2);
                String lolbShed = shedBlockAttempt(getOteamPlayer("LT"),getDteamPlayer("ROLB"),base2);

                while(yard < 10){
                    yard += 1;
                    if (mlbShed.equals("Block Shed")){
                        tackleAttempt(this.ballHandler,getDteamPlayer("MLB"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        mlbShed =  shedBlockAttempt(getOteamPlayer("C"),getDteamPlayer("MLB"),base2);
                    }

                    if (rolbShed.equals("Block Shed")){
                        tackleAttempt(this.ballHandler,getDteamPlayer("ROLB"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        mlb2Shed = shedBlockAttempt(getOteamPlayer("RT"),getDteamPlayer("ROLB"),base2);
                    }
                    if (lolbShed.equals("Block Shed")){
                        tackleAttempt(this.ballHandler,getDteamPlayer("LOLB"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        mlb2Shed = shedBlockAttempt(getOteamPlayer("LT"),getDteamPlayer("LOLB"),base2);
                    }

                    if (rbVisionTest()){
                        yard += 1;
                    }

                }

                //3rd level
                base = 50;
                fsPursuit = pursuit(getDteamPlayer("FS"),base);
                ssPursuit = pursuit(getDteamPlayer("SS"),base);

                while(yard < 99){
                    if (fsPursuit){
                        tackleAttempt(this.ballHandler,getDteamPlayer("FS"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        base +=20;
                        fsPursuit = pursuit(getDteamPlayer("FS"),base);
                    }
                    if (ssPursuit){
                        tackleAttempt(this.ballHandler,getDteamPlayer("SS"));
                        if (this.playOutcome != null){
                            return yard;
                        }
                    }
                    else {
                        base += 20;
                        fsPursuit = pursuit(getDteamPlayer("SS"),base);
                    }
                    yard += 3;
                }


        }
        return yard;

    }

    private HashMap<coverageZone,Player> assignedCoverages;

    public int passPlay(){
        int yards = 0;

        Random rand = new Random();

        ArrayList<Player> playersInCoverage = new ArrayList<Player>();
        ArrayList<Player> playersPassRushing = new ArrayList<Player>();
        ArrayList<Player> playersPassBlocking = new ArrayList<Player>();
        ArrayList<Player> playersInRoutes = new ArrayList<Player>();


        //Map that will store man player assignments
        HashMap<Player,Player> manAssignments = new HashMap<Player,Player>();


        HashMap<Player,Integer> routes = assignRoutes();






        //assigns offensive positions to each list
        for(Map.Entry<Player,Integer> set: routes.entrySet()){
            if (set.getValue() == 0){
                playersPassBlocking.add(set.getKey());
            }
            else{
                playersInRoutes.add(set.getKey());
            }
        }

        //sets the O - linemen in the pass block list
        playersPassBlocking.add(getOteamPlayer("LT"));
        playersPassBlocking.add(getOteamPlayer("LG"));
        playersPassBlocking.add(getOteamPlayer("C"));
        playersPassBlocking.add(getOteamPlayer("RG"));
        playersPassBlocking.add(getOteamPlayer("RT"));

        //set the D - linemen in the pass rush list depending on formation
        switch (dTeam.getCoach().getDeffensiveScheme().getdForm()){
            case D3_4 -> {
                playersPassRushing.add(getDteamPlayer("EDGE"));
                playersPassRushing.add(getDteamPlayer("DT"));
                playersPassRushing.add(getDteamPlayer("LE"));
                playersPassRushing.add(getDteamPlayer("RE"));

            }
            case D4_3 -> {
                playersPassRushing.add(getDteamPlayer("DT1"));
                playersPassRushing.add(getDteamPlayer("DT2"));
                playersPassRushing.add(getDteamPlayer("EDGE1"));
                playersPassRushing.add(getDteamPlayer("EDGE2"));
            }
        }
        //if play type is blitz then this will assign 1-2 blitzers
        if (DPlayType == PlayType.MAN_BLITZ || DPlayType == PlayType.ZONE_BLITZ){
            //lists of available rushers
            ArrayList<Player> availableBlitzers3_4 = new ArrayList<Player>();
            availableBlitzers3_4.add(getDteamPlayer("MLB1"));
            availableBlitzers3_4.add(getDteamPlayer("MLB2"));
            availableBlitzers3_4.add(getDteamPlayer("OLB"));

            ArrayList<Player> availableBlitzers4_3 = new ArrayList<Player>();
            availableBlitzers4_3.add(getDteamPlayer("MLB"));
            availableBlitzers4_3.add(getDteamPlayer("ROLB"));
            availableBlitzers4_3.add(getDteamPlayer("LOLB"));

            //decides number of blitzers
            int numberOfBlitzers = Player.getBellcurve(1,4);
            ArrayList<Player> newBlitzers = new ArrayList<Player>();
            for(int i = 0 ;i < numberOfBlitzers;i++){
                switch (dTeam.getCoach().getDeffensiveScheme().getdForm()){
                    case D3_4 -> {
                        playersPassRushing.add(availableBlitzers3_4.get(rand.nextInt(availableBlitzers3_4.size())));

                    }
                    case D4_3 -> {
                        playersPassRushing.add(availableBlitzers4_3.get(rand.nextInt(availableBlitzers4_3.size())));
                    }
                }
            }
        }

        //assigns the players in coverage
        switch (dTeam.getCoach().getDeffensiveScheme().getdForm()){
            case D3_4 -> {
                for (Map.Entry<String,Player> set: dTeam.getD3_4startingD().entrySet()){
                    boolean blitzing = false;
                    for (Player player: playersPassRushing){
                        if (player == set.getValue()){
                            blitzing = true;
                        }
                        else {
                            blitzing = false;
                        }
                    }
                    if (!blitzing){
                        playersInCoverage.add(set.getValue());
                    }

                }



            }

            case D4_3 -> {
                for (Map.Entry<String,Player> set: dTeam.getD4_3startingD().entrySet()){
                    boolean blitzing = false;
                    for (Player player: playersPassRushing){
                        if (player == set.getValue()) {
                            blitzing = true;
                            break;
                        }
                    }
                    if (!blitzing){
                        playersInCoverage.add(set.getValue());
                    }

                }
            }




        }



        assignedCoverages = new HashMap<coverageZone,Player>();
        assignedCoverages.put(coverageZone.DEEP_RIGHT,empty);
        assignedCoverages.put(coverageZone.DEEP_MIDDLE,empty);
        assignedCoverages.put(coverageZone.DEEP_LEFT,empty);
        assignedCoverages.put(coverageZone.MED_RIGHT,empty);
        assignedCoverages.put(coverageZone.MED_MIDDLE,empty);
        assignedCoverages.put(coverageZone.MED_LEFT,empty);
        assignedCoverages.put(coverageZone.SHORT_RIGHT,empty);
        assignedCoverages.put(coverageZone.SHORT_MIDDLE,empty);
        assignedCoverages.put(coverageZone.SHORT_LEFT,empty);


        //seconds since snap
        int seconds = 0;


        switch (DPlayType){
            case MAN , MAN_BLITZ  -> {
                HashMap<Player,Player> manAssignedCoverages = assignManCoverage(playersInRoutes,playersInCoverage);
                while(true){
                    seconds += 1;
                }

            }
            case ZONE , ZONE_BLITZ -> {
                assignedCoverages = assignZoneCoverage(playersInCoverage);
            }
        }



        return yards;

    }
    public boolean passRush(ArrayList<Player> passBlockers, ArrayList<Player> passRushers,int second){
        // will simulate a pass rush based on the pass blockers, pass rushers, and seconds since the snap.
        boolean passRushSuccess = false;

        int base = second * 5;

        HashMap<Player,Player> passBlockAssignments = assignPassBlock(passBlockers,passRushers);




        return passRushSuccess;
    }

    public boolean passRushAttempt(Player oPlayer, Player dPlayer, int base){
        boolean passRushSuccess = false;
        Random rand = new Random();
        int passBlockRushDif = dPlayer.getPassRushRTG() - oPlayer.getPassBlockRTG();
        int adjustedChance = base + passBlockRushDif;
        if (adjustedChance < -9){
            adjustedChance = 0;
        } else if (adjustedChance <0) {
            adjustedChance = 1;
        }
        if (rand.nextInt(1,101) < adjustedChance){
            passRushSuccess = true;
        }
        return passRushSuccess;
    }

    public HashMap<Player,Player> assignPassBlock(ArrayList<Player> passBlockers ,ArrayList<Player> passRushers){
        //will assign each passBlocker a  pass rusher and return a hash map
        HashMap<Player,Player> assignments = new HashMap<Player,Player>();
        //creates a basis for all passBlockers
        for (Player passB: passBlockers){
            assignments.put(passB , empty);
        }
        if (dTeam.getCoach().getDeffensiveScheme().getdForm() == DScheme.defensiveFormation.D3_4){
            assignments.put(getOteamPlayer("LT"),getDteamPlayer("EDGE"));
            assignments.put(getOteamPlayer("LG"),getDteamPlayer("LE"));
            assignments.put(getOteamPlayer("C"),getDteamPlayer("DT"));
            assignments.put(getOteamPlayer("RG"),getDteamPlayer("RE"));

        }else {
            assignments.put(getOteamPlayer("LT"),getDteamPlayer("EDGE1"));
            assignments.put(getOteamPlayer("LG"),getDteamPlayer("DT"));
            assignments.put(getOteamPlayer("C"),getDteamPlayer("DT"));
            assignments.put(getOteamPlayer("RT"),getDteamPlayer("EDGE2"));
        }
        int passDif = (int)  passBlockers.size() - passRushers.size();
        if (passDif < 0){
            for (int i = 0; i < passDif * -1; i++){
                assignments.put(empty,passRushers.get(4+i));
            }

        } else if (passDif == 0) {

        }
        return assignments;
    }

    public enum coverageZone {
        DEEP_LEFT, DEEP_MIDDLE , DEEP_RIGHT,
        MED_LEFT, MED_MIDDLE , MED_RIGHT,
        SHORT_LEFT, SHORT_MIDDLE,SHORT_RIGHT
    }

    public HashMap<Player,Player> assignManCoverage(ArrayList<Player> playersInRoutes , ArrayList<Player> playersInCoverage){

        //method that returns a hash map of with a key of an offensive route runner and  a value of a defender
        HashMap<Player,Player> manAssignments = new HashMap<Player,Player>();

        //will store players that arnt assinged

        for (Player player: playersInRoutes){
            manAssignments.put(player, empty);
        }

        manAssignments.put(getOteamPlayer("WR1"),getDteamPlayer("CB1"));
        manAssignments.put(getOteamPlayer("WR2"),getDteamPlayer("CB2"));
        manAssignments.put(getOteamPlayer("WR3"),getDteamPlayer("CB3"));

        int playerDiff = playersInCoverage.size() - playersInRoutes.size();

        if (playerDiff > 0){

        } else if (playerDiff == 0) {

        }
        else {

        }


        return manAssignments;
    }

    public HashMap<coverageZone,Player> assignZoneCoverage(ArrayList<Player> playersInCoverage){
        //method that will assign zone coverages
        Random rand = new Random();
        HashMap<coverageZone,Player> assignedCoverages = new HashMap<>();
        assignedCoverages.put(coverageZone.DEEP_RIGHT,empty);
        assignedCoverages.put(coverageZone.DEEP_MIDDLE,empty);
        assignedCoverages.put(coverageZone.DEEP_LEFT,empty);
        assignedCoverages.put(coverageZone.MED_RIGHT,empty);
        assignedCoverages.put(coverageZone.MED_MIDDLE,empty);
        assignedCoverages.put(coverageZone.MED_LEFT,empty);
        assignedCoverages.put(coverageZone.SHORT_RIGHT,empty);
        assignedCoverages.put(coverageZone.SHORT_MIDDLE,empty);
        assignedCoverages.put(coverageZone.SHORT_LEFT,empty);



        int safteyCoverage = rand.nextInt(0,2);

        for (Player player: playersInCoverage){
            switch (player.getPosition()){
                case "S":
                    int turn = 0 ;
                    switch (safteyCoverage){
                        case 1:
                            if (turn == 0){
                                assignedCoverages.put(coverageZone.DEEP_MIDDLE,player);
                                turn++;
                            }
                            else {
                                int secondSafety = rand.nextInt(1,5);
                                if (secondSafety == 1){
                                    assignedCoverages.put(coverageZone.DEEP_MIDDLE,player);
                                } else if (secondSafety == 2) {
                                    assignedCoverages.put(coverageZone.MED_MIDDLE,player);
                                } else if (secondSafety == 3) {
                                    assignedCoverages.put(coverageZone.MED_LEFT,player);
                                } else {
                                    assignedCoverages.put(coverageZone.DEEP_RIGHT,player);
                                }
                            }

                            break;
                        case 0:
                            if(turn == 0){
                                assignedCoverages.put(coverageZone.DEEP_LEFT,player);
                                turn ++;
                            }
                            else {
                                assignedCoverages.put(coverageZone.DEEP_RIGHT,player);
                            }

                    }
                case "CB" :
                    int CBcount = 0;
                    if (CBcount == 0){
                        CBcount ++;
                        if (rand.nextInt(0,2) == 1){
                            assignedCoverages.put(coverageZone.MED_RIGHT,player);
                        }
                        else {
                            assignedCoverages.put(coverageZone.SHORT_RIGHT,player);
                        }
                    } else {
                        if (rand.nextInt(0,2) == 1){
                            assignedCoverages.put(coverageZone.MED_LEFT,player);
                        }
                        else {
                            assignedCoverages.put(coverageZone.SHORT_LEFT,player);
                        }
                    }
                case "LB" :
                    if (rand.nextInt(0,2) == 1){
                        assignedCoverages.put(coverageZone.MED_MIDDLE,player);
                    }
                    else {
                        assignedCoverages.put(coverageZone.SHORT_MIDDLE,player);
                    }
            }
        }
        return assignedCoverages;


    }




    public boolean rbVisionTest(){
        //tests the running backs vision

        Player rb = getOteamPlayer("RB");
        Random rand = new Random();
        //Tests running backs vision
        int baseChanceOfYardJump = 10;
        int adjustedChance = (int) Math.round(baseChanceOfYardJump + rb.getVisionRTG()/2 );
        if (rand.nextInt(1,101) < adjustedChance ){
            return true;
        }
        else {
            return false;
        }
    }

    public HashMap<Player, Integer> assignRoutes(){
        //assigns either a route or pass block to each player able to run a route
        //0 - pass block
        //1 - Short Route
        //2 - Medium Route
        //3 - Deep Route
        Random rand = new Random();
        HashMap<Player,Integer> routes = new HashMap<Player,Integer>();
        ArrayList<Player> routeRunners = new ArrayList<Player>();

        routeRunners.add(getOteamPlayer("WR1"));
        routeRunners.add(getOteamPlayer("WR2"));
        routeRunners.add(getOteamPlayer("WR2"));




        boolean playMatch = false;

        while (!playMatch){
            routes.put(getOteamPlayer("WR1"),rand.nextInt(1,4));
            routes.put(getOteamPlayer("WR2"),rand.nextInt(1,4));
            routes.put(getOteamPlayer("WR3"),rand.nextInt(1,4));
            routes.put(getOteamPlayer("TE"),rand.nextInt(0,4));
            routes.put(getOteamPlayer("RB"),rand.nextInt(0,4));

            int deepRoutes = 0;
            int medRoutes = 0;
            int shortRoutes = 0;

            for (Player runner: routeRunners){
                if (routes.get(runner) == 1){
                    shortRoutes ++;
                } else if (routes.get(runner) == 2) {
                    medRoutes ++;
                } else if (routes.get(runner)== 3) {
                    deepRoutes ++;

                }
            }



            switch (OPlayType){
                case SHORT_THROW -> {
                    routes.put(getOteamPlayer("WR1"),1);
                    routes.put(getOteamPlayer("WR2"),1);
                    routes.put(getOteamPlayer("WR3"),1);
                    routes.put(getOteamPlayer("TE"),rand.nextInt(0,2));
                    routes.put(getOteamPlayer("RB"),rand.nextInt(0,2));
                    playMatch = true;
                }
                case MED_THROW -> {
                    if (medRoutes == 2){
                        playMatch = true;
                        routes.put(getOteamPlayer("TE"),rand.nextInt(0,3));
                        routes.put(getOteamPlayer("RB"),rand.nextInt(0,3));

                    }


                }
                case DEEP_THROW -> {
                    if (deepRoutes == 2){
                        playMatch = true;
                        routes.put(getOteamPlayer("TE"),rand.nextInt(0,4));
                        routes.put(getOteamPlayer("RB"),rand.nextInt(0,2));
                    }
                }
            }
        }



        return routes;
    }







    public void decidePlay(){
        //will decide based on coaches play frequency what each coach is running for this play
        Random rand = new Random();
        if (rand.nextInt(1,101) > dTeam.getCoach().getDeffensiveScheme().getManFreq()){
            if (rand.nextInt(1,101) > dTeam.getCoach().getDeffensiveScheme().getBlitzFreq()){
                this.DPlayType = PlayType.ZONE;
            }
            else {
                this.DPlayType = PlayType.ZONE_BLITZ;
            }
        }
        else {
            if (rand.nextInt(1,101) > dTeam.getCoach().getDeffensiveScheme().getBlitzFreq()){
                this.DPlayType = PlayType.MAN;
            }
            else {
                this.DPlayType = PlayType.MAN_BLITZ;
            }
        }

        if (rand.nextInt(1,101) > oTeam.getCoach().getOffensiveScheme().getRunFreq()){
            //decides if run wil be inside or outside
            if(rand.nextInt(1,101) > oTeam.getCoach().getOffensiveScheme().getOutsideRunFreq()){
                this.OPlayType = PlayType.INSIDE_RUN;
            }
            else {
                this.OPlayType = PlayType.OUTSIDE_RUN;
            }
        }
        else {
            //Decides what pass play
            int playDecider = rand.nextInt(1,101);

            if (playDecider > oTeam.getCoach().getOffensiveScheme().getDeepPassFreq() + oTeam.getCoach().getOffensiveScheme().getMedPassFreq()){
                this.OPlayType = PlayType.SHORT_THROW;
            } else if (playDecider < oTeam.getCoach().getOffensiveScheme().getDeepPassFreq()) {
                this.OPlayType = PlayType.MED_THROW;
            }
            else {
                this.OPlayType = PlayType.DEEP_THROW;
            }
        }
    }

    public String shedBlockAttempt(Player oPlayer, Player dPlayer,int base){
        int baseChanceOfshed = base;
        int runBlockBlockShedDiff = oPlayer.getRunBlockRTG() - dPlayer.getBlockShedRTG();
        int adjustedChanceOfShed = baseChanceOfshed - (int) Math.round(runBlockBlockShedDiff*1.2);
        Random rand = new Random();
        if (rand.nextInt(1,101) < adjustedChanceOfShed){
            return "Block Shed";
        }
        else {
            return "Block Held";
        }

    }

    public boolean pursuit(Player dPlayer,int base){
        //method that simulates a defensive players pursuit of the ball handler
        Random rand = new Random();

        int basePursuitChance = base;
        int speedDiff = dPlayer.getSpeed() - this.ballHandler.getSpeed();
        int adjustedChance =  base + (int) Math.round(speedDiff*1.1);
        return rand.nextInt(1, 101) < adjustedChance;

    }

    public void tackleAttempt(Player oPlayer, Player dPlayer){
        int baseChanceOfTackle = 60;
        int tackleAgilityDiff = dPlayer.getTackleRTG() - oPlayer.getAgility();
        int tackleStrengthDiff = dPlayer.getTackleRTG() - oPlayer.getStrength();

        int aAdjustedChanceOfTackle = (int) Math.round((float) tackleAgilityDiff *1.1) + baseChanceOfTackle;
        int sAdjustedChanceOfTackle = (int) Math.round((float) tackleStrengthDiff *1.1) + baseChanceOfTackle;

        Random rand = new Random();
        if (rand.nextInt(1,101) < aAdjustedChanceOfTackle){
            fumbleTest(oPlayer,dPlayer);

        } else if (rand.nextInt(1,101) < sAdjustedChanceOfTackle) {
            fumbleTest(oPlayer,dPlayer);
        }


    }

    public void fumbleTest(Player oPlayer, Player dPlayer){
        int baseChanceOfFumble = 1;
        int strengthCarryDiff = dPlayer.getStrength() - oPlayer.getCarryRTG();

        int adjustedChanceOfFumble = Math.round((float) strengthCarryDiff/3) + baseChanceOfFumble;

        Random rand = new Random();
        if (rand.nextInt(1,101)< adjustedChanceOfFumble){
            this.playOutcome = PlayOutcome.FUMBLE;
            this.turnOverOnPlay = true;
            this.forcedFumbler = dPlayer;
            this.fumbler = oPlayer;
        }
        else {
            this.playOutcome = PlayOutcome.TACKLE;
            this.turnOverOnPlay = false;
            this.tackler = dPlayer;
        }
    }

    public Player getOteamPlayer(String position){
        return this.oTeam.getStartingO().get(position);
    }
    public Player getDteamPlayer(String position){
        if (this.dTeam.getCoach().getDeffensiveScheme().getdForm() == DScheme.defensiveFormation.D3_4){
            return this.dTeam.getD3_4startingD().get(position);
        }
        else {
            return this.dTeam.getD4_3startingD().get(position);
        }
    }


    //getters and setter


    public Team getoTeam() {
        return oTeam;
    }

    public void setoTeam(Team oTeam) {
        this.oTeam = oTeam;
    }

    public Team getdTeam() {
        return dTeam;
    }

    public void setdTeam(Team dTeam) {
        this.dTeam = dTeam;
    }

    public PlayType getOPlayType() {
        return OPlayType;
    }

    public void setOPlayType(PlayType OPlayType) {
        this.OPlayType = OPlayType;
    }

    public PlayType getDPlayType() {
        return DPlayType;
    }

    public void setDPlayType(PlayType DPlayType) {
        this.DPlayType = DPlayType;
    }

    public PlayOutcome getPlayOutcome() {
        return playOutcome;
    }

    public void setPlayOutcome(PlayOutcome playOutcome) {
        this.playOutcome = playOutcome;
    }

    public int getTimeOfplay() {
        return timeOfplay;
    }

    public void setTimeOfplay(int timeOfplay) {
        this.timeOfplay = timeOfplay;
    }

    public int getYardsGained() {
        return yardsGained;
    }

    public void setYardsGained(int yardsGained) {
        this.yardsGained = yardsGained;
    }

    public boolean isTurnOverOnPlay() {
        return turnOverOnPlay;
    }

    public void setTurnOverOnPlay(boolean turnOverOnPlay) {
        this.turnOverOnPlay = turnOverOnPlay;
    }

    public Player getTackler() {
        return tackler;
    }

    public void setTackler(Player tackler) {
        this.tackler = tackler;
    }

    public Player getBallHandler() {
        return ballHandler;
    }

    public void setBallHandler(Player ballHandler) {
        this.ballHandler = ballHandler;
    }

    public Player getFumbler() {
        return fumbler;
    }

    public void setFumbler(Player fumbler) {
        this.fumbler = fumbler;
    }

    public Player getForcedFumbler() {
        return forcedFumbler;
    }

    public void setForcedFumbler(Player forcedFumbler) {
        this.forcedFumbler = forcedFumbler;
    }
}
