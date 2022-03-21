package model;

public class FLOModel {
    public int source;
    public int dest;
    public int amount;
    public int cost;

    public FLOModel(int source, int dest, int amount, int cost) {
        this.source = source;
        this.dest = dest;
        this.amount = amount;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "FLOModel{" +
                "source=" + source +
                ", dest=" + dest +
                ", amount=" + amount +
                ", cost=" + cost +
                '}';
    }
}
