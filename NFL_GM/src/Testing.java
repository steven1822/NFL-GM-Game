import Coaching.Coach;
import League.Team;
import Players.*;
import Schedule.Play;
import Screens.PlayerView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.jar.JarEntry;

public class Testing {
    public Coach coach;

    public Player qb;

    public Player wr1;

    public Player te;

    public Player lt;
    public Player rg;
    public Player c;
    public Player lg;
    public Player rt;
    public Player rb;
    public Player dt;
    public Player le;
    public Player re;
    public Player lb;

    public Player edge;

    public Player lb2;
    public Player fs;
    public Player ss;

    public Team team1;
    public Team team2;

    public Testing() {
        Random rand = new Random();

        team1 = new Team();
        team2 = new Team();

        coach = new Coach();
        coach.setDeffensiveScheme(coach.dScheme2);
        coach.setOffensiveScheme(coach.oScheme1);

        team1.setCoach(coach);
        team2.setCoach(coach);


        qb = Player.createRandomPlayer("QB",1);
        qb.setFirstName("Gavin");
        qb.setLastName("Ondish");
        qb.setOverall();

        wr1 = Player.createRandomPlayer("WR",0.9);
        wr1.setFirstName("Steven");
        wr1.setLastName("Miller");
        wr1.setCollege("Penn State");
        wr1.setHeight(74);
        wr1.setWeight(195);
        wr1.setOverall();

        te = Player.createRandomPlayer("TE",1);
        te.setOverall();

        rt = Player.createRandomPlayer("OT",1);
        rt.setOverall();

        lt = Player.createRandomPlayer("OT",1.15);
        lt.setOverall();

        lg = Player.createRandomPlayer("OG",1);
        lg.setOverall();

        rg = Player.createRandomPlayer("OG",1);
        rg.setOverall();

        c = Player.createRandomPlayer("C",1);
        c.setOverall();

        rb = Player.createRandomPlayer("RB",1);
        rb.setOverall();

        le = Player.createRandomPlayer("DE",1);
        le.setOverall();

        re = Player.createRandomPlayer("DE",1);
        re.setOverall();

        dt = Player.createRandomPlayer("DT",1);
        dt.setOverall();

        lb = Player.createRandomPlayer("LB",1);
        lb.setOverall();

        lb2 = Player.createRandomPlayer("LB",0.95);
        lb2.setOverall();

        edge = Player.createRandomPlayer("EDGE",1.06);
        edge.setOverall();

        fs = Player.createRandomPlayer("S",1);
        fs.setOverall();

        ss = Player.createRandomPlayer("S",1);
        ss.setOverall();


        team1.getStartingO().put("QB", qb);
        team1.getStartingO().put("WR1",wr1);


        team1.getStartingO().put("TE",te);
        team1.getStartingO().put("RB",rb);
        team1.getStartingO().put("LT",lt);
        team1.getStartingO().put("LG",lg);
        team1.getStartingO().put("C",c);
        team1.getStartingO().put("RG",rg);
        team1.getStartingO().put("RT",rt);

        team2.getD3_4startingD().put("DT",dt);
        team2.getD3_4startingD().put("LE",le);
        team2.getD3_4startingD().put("RE",re);
        team2.getD3_4startingD().put("MLB1",lb);
        team2.getD3_4startingD().put("MLB2",lb2);
        team2.getD3_4startingD().put("EDGE",edge);
        team2.getD3_4startingD().put("SS",ss);
        team2.getD3_4startingD().put("FS",fs);

        team2.getD4_3startingD().put("DT1",dt);
        team2.getD4_3startingD().put("DT2",dt);
        team2.getD4_3startingD().put("MLB",lb);
        team2.getD4_3startingD().put("ROLB",lb2);
        team2.getD4_3startingD().put("LOLB",lb2);
        team2.getD4_3startingD().put("SS",ss);
        team2.getD4_3startingD().put("FS",fs);













    }

    public static void main(String[] args) {
        Testing test = new Testing();
        Random rand = new Random();
        Play play = new Play(test.team1,test.team2);



        Player oPlayer = Player.createRandomPlayer("random",1 );
        Player dPlayer = Player.createRandomPlayer("DT",1);

        oPlayer.setOverall();






        System.out.println(test.rb);
        System.out.println(test.lt);
        System.out.println(test.lg);
        System.out.println(test.c);
        System.out.println(test.rg);
        System.out.println(test.lt);


        System.out.println(test.dt);
        System.out.println(test.re);
        System.out.println(test.le);
        System.out.println(test.lb);
        System.out.println(test.lb2);
        System.out.println(test.edge);
        System.out.println(test.fs);
        System.out.println(test.ss);
        int shedCounter = 0;
        int blockCounter = 0;
        int total = 0;
        int runs = 20;

//        for (int i = 0; i< 10;i++){
//            total = 0;
//            for (int j = 0; j< runs;j++){
////            String result = play.runBlockTest(oPlayer,dPlayer);
////            if (result.equals("Block Shed")){
////                shedCounter += 1;
////            }
////            else {
////                blockCounter += 1;
////            }
//
//                total += play.insideRun();
//
//
//
//
//            }
//            System.out.println(total);
//            System.out.println((float) total/runs + " Avg Yards");
//        }

        int rushCounter = 0;
        for (int i = 0; i< 100; i ++){
            boolean passRush = play.passRushAttempt(test.lt,test.edge,10);
            if (passRush){
                rushCounter ++;
            }

        }
        System.out.println(rushCounter);


        PlayerView view = new PlayerView(test.wr1);

        JFrame frame = new JFrame();
        frame.setContentPane(view.getMainPanel());
        frame.setSize(1000,750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);








//        System.out.println("Tackles:" + shedCounter);
//        System.out.println("Broken Tackles:" + blockCounter);
//        System.out.println(oPlayer);








    }
}
