package model;

import dataConfig.DataType;

import java.util.ArrayList;
import java.util.Collections;

public class Factories {
    private ArrayList<FactoryEfficiency> factories = new ArrayList<>();

    public Factories(DataType dt, int n) {
        for (int i = 0; i < n; i++) {
            FactoryModel fm = new FactoryModel(dt);
            fm.fillFactory();
            FactoryEfficiency factoryEfficiency = new FactoryEfficiency(fm);
            factories.add(factoryEfficiency);
        }
    }

    public Factories(ArrayList<FactoryEfficiency> factoryEfficiencies) {
        this.factories = factoryEfficiencies;
    }

    public ArrayList<FactoryEfficiency> getFactories() {
        return factories;
    }

    public int sumEfficiency() {
        int sum = 0;
        for(FactoryEfficiency factoryEfficiency : factories) {
            sum += factoryEfficiency.getFactoryEfficiency();
        }
        return sum;
    }

    public FactoryEfficiency findBestFactory() {
        FactoryEfficiency factoryEfficiency = new FactoryEfficiency();
        for(FactoryEfficiency fe : factories) {
            if(fe.getFactoryEfficiency() < factoryEfficiency.getFactoryEfficiency()) {
                factoryEfficiency = fe;
            }
        }
        return factoryEfficiency;
    }

    public int sumWeight() {
        int sum = 0;
        for(FactoryEfficiency factoryEfficiency : factories) {
            sum += factoryEfficiency.weight;
        }
        return sum;
    }

    public void sortFactoriesByWeight() {
        factories.sort(FactoryEfficiency.weightComparator);
    }
}
