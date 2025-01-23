package Coaching;

//Class that models a Coaches Offensive Scheme
public class OScheme {
    private String schemeName;
    private int runFreq;
    private int passFreq;
    private int insideRunFreq;
    private int outsideRunFreq;
    private int deepPassFreq;
    private int medPassFreq;
    private int shortPassFreq;

    public OScheme(String schemeName, int runFreq, int passFreq, int insideRunFreq, int outsideRunFreq, int deepPassFreq, int medPassFreq, int shortPassFreq) {
        if (runFreq + passFreq != 100){
            throw new IllegalArgumentException("Run Frequency + Pass Frequency must equal 100");
        }
        if (insideRunFreq + outsideRunFreq != 100){
            throw new IllegalArgumentException("Inside Run Frequency + Outside Run Frequency must equal 100");
        }
        if (deepPassFreq + shortPassFreq + medPassFreq != 100){
            throw new IllegalArgumentException("Deep Pass Frequency + Medium Pass Frequency + Short Pass Frequency must equal 100");
        }

        this.schemeName = schemeName;
        this.runFreq = runFreq;
        this.passFreq = passFreq;
        this.insideRunFreq = insideRunFreq;
        this.outsideRunFreq = outsideRunFreq;
        this.deepPassFreq = deepPassFreq;
        this.medPassFreq = medPassFreq;
        this.shortPassFreq = shortPassFreq;
    }

    //Getters and Setters

    public String getSchemeName() {

        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public int getRunFreq() {
        return runFreq;
    }

    public void setRunFreq(int runFreq) {
        this.runFreq = runFreq;
    }

    public int getPassFreq() {
        return passFreq;
    }

    public void setPassFreq(int passFreq) {
        this.passFreq = passFreq;
    }

    public int getInsideRunFreq() {
        return insideRunFreq;
    }

    public void setInsideRunFreq(int insideRunFreq) {
        this.insideRunFreq = insideRunFreq;
    }

    public int getOutsideRunFreq() {
        return outsideRunFreq;
    }

    public void setOutsideRunFreq(int outsideRunFreq) {
        this.outsideRunFreq = outsideRunFreq;
    }

    public int getDeepPassFreq() {
        return deepPassFreq;
    }

    public void setDeepPassFreq(int deepPassFreq) {
        this.deepPassFreq = deepPassFreq;
    }

    public int getMedPassFreq() {
        return medPassFreq;
    }

    public void setMedPassFreq(int medPassFreq) {
        this.medPassFreq = medPassFreq;
    }

    public int getShortPassFreq() {
        return shortPassFreq;
    }

    public void setShortPassFreq(int shortPassFreq) {
        this.shortPassFreq = shortPassFreq;
    }
}
