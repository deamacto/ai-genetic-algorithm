package model;

public class Statistics {
    int best;
    int worst;
    double avg;
    double std;

    public Statistics(int best, int worst, double avg, double std) {
        this.best = best;
        this.worst = worst;
        this.avg = avg;
        this.std = std;
    }

    public int getBest() {
        return best;
    }

    public int getWorst() {
        return worst;
    }

    public double getAvg() {
        return avg;
    }

    public double getStd() {
        return std;
    }
}
