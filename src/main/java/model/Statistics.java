package model;

public class Statistics {
    public int best;
    public int worst;
    public double avg;
    public double std;

    public Statistics() {
        best = Integer.MAX_VALUE;
        worst = Integer.MIN_VALUE;
        avg = 0.0;
        std = 0.0;
    }
}
