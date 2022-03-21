package model;

public class FactoryEfficiency {
    private final FactoryModel factory;
    private final int factoryEfficiency;

    public FactoryEfficiency(FactoryModel factory) {
        this.factory = factory;
        this.factoryEfficiency = factory.factoryEfficiency();
    }

    public FactoryEfficiency() {
        this.factory = null;
        this.factoryEfficiency = Integer.MAX_VALUE;
    }

    public FactoryModel getFactory() {
        return factory;
    }

    public int getFactoryEfficiency() {
        return factoryEfficiency;
    }
}
