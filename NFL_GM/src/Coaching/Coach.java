package Coaching;


//Class that models a Coach
public class Coach {
    //Filler Schemes
    public OScheme oScheme1 = new OScheme("Verticle",30,70,45,55,40,30,30);
    public DScheme dScheme1 = new DScheme("3-4 Zone", DScheme.defensiveFormation.D3_4,60,40,30);

    public DScheme dScheme2 = new DScheme("4-3 Zone" , DScheme.defensiveFormation.D4_3,60,40,30);
    //Field Variables
    private int offensiveRTG;
    private int deffensiveRTG;
    private String firstName;
    private String lastName;
    private OScheme offensiveScheme;
    private DScheme deffensiveScheme;

    public Coach() {

    }

    //Getters and Setters

    public int getOffensiveRTG() {
        return offensiveRTG;
    }

    public void setOffensiveRTG(int offensiveRTG) {
        this.offensiveRTG = offensiveRTG;
    }

    public int getDeffensiveRTG() {
        return deffensiveRTG;
    }

    public void setDeffensiveRTG(int deffensiveRTG) {
        this.deffensiveRTG = deffensiveRTG;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public OScheme getOffensiveScheme() {
        return offensiveScheme;
    }

    public void setOffensiveScheme(OScheme offensiveScheme) {
        this.offensiveScheme = offensiveScheme;
    }

    public DScheme getDeffensiveScheme() {
        return deffensiveScheme;
    }

    public void setDeffensiveScheme(DScheme deffensiveScheme) {
        this.deffensiveScheme = deffensiveScheme;
    }
}
