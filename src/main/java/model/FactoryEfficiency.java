package model;

import java.util.Comparator;

public class FactoryEfficiency {
    private final FactoryModel factory;
    private int factoryEfficiency;
    public double weight = 0;

    public FactoryEfficiency(FactoryModel factory) {
        this.factory = factory;
        this.factoryEfficiency = factory.factoryEfficiency();
    }

    public FactoryEfficiency(int efficiency) {
        this.factory = null;
        this.factoryEfficiency = efficiency;
    }

    public FactoryModel getFactory() {
        return factory;
    }

    public int getFactoryEfficiency() {
        return factoryEfficiency;
    }

    public void revaluateEfficiency() {
        this.factoryEfficiency = factory.factoryEfficiency();
    }

    @Override
    public FactoryEfficiency clone() {
        return new FactoryEfficiency(this.factory.clone());
    }

    public static Comparator<FactoryEfficiency> weightComparator = (f1, f2) -> (Double.compare(f1.weight, f2.weight));
}
