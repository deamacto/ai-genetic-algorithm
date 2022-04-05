package model;

import dataConfig.DataType;

import java.util.ArrayList;

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

    public int sum() {
        int sum = 0;
        for(FactoryEfficiency factoryEfficiency : factories) {
            sum += factoryEfficiency.getFactoryEfficiency();
        }
        return sum;
    }

    public double avg() {
        return sum()/(double)factories.size();
    }

    public double stdDeviation() {
        double sum = 0;
        double avg = avg();
        for(FactoryEfficiency fe : factories) {
            sum += Math.pow(fe.getFactoryEfficiency() -avg, 2);
        }

        return Math.sqrt(sum / factories.size());
    }

    public ArrayList<FactoryEfficiency> getFactories() {
        return factories;
    }

    public long sumEfficiencySquared() {
        long sum = 0;
        for(FactoryEfficiency factoryEfficiency : factories) {
            sum += (long) factoryEfficiency.getFactoryEfficiency() * factoryEfficiency.getFactoryEfficiency();
        }
        return sum;
    }

    public FactoryEfficiency findBestFactory() {
        FactoryEfficiency factoryEfficiency = new FactoryEfficiency(Integer.MAX_VALUE);
        for(FactoryEfficiency fe : factories) {
            if(fe.getFactoryEfficiency() < factoryEfficiency.getFactoryEfficiency()) {
                factoryEfficiency = fe;
            }
        }
        return factoryEfficiency;
    }

    public FactoryEfficiency findWorstFactory() {
        FactoryEfficiency factoryEfficiency = new FactoryEfficiency(Integer.MIN_VALUE);
        for(FactoryEfficiency fe : factories) {
            if(fe.getFactoryEfficiency() > factoryEfficiency.getFactoryEfficiency()) {
                factoryEfficiency = fe;
            }
        }
        return factoryEfficiency;
    }

    public double sumBestAndWorstWeight() {
        double bestWeight = Double.MAX_VALUE;
        double worstWeight = Double.MIN_VALUE;
        for(FactoryEfficiency factory : factories) {
            if(factory.weight < bestWeight) {
                bestWeight = factory.weight;
            }
            if(factory.weight > worstWeight) {
                worstWeight = factory.weight;
            }
        }
        return bestWeight + worstWeight;
    }

    public void sortFactoriesByWeight() {
        factories.sort(FactoryEfficiency.weightComparator);
    }
}
