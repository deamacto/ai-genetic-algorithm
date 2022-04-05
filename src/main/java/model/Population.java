package model;

import java.util.ArrayList;

import dataConfig.DataType;

public class Population {

    private final Factories factories;
    private final String populationName;

    public Population(DataType dt, int n, String populationName) {
        this.factories = new Factories(dt, n);
        this.populationName = populationName;
    }

    public Population(ArrayList<FactoryEfficiency> factoryEfficiencies, String populationName) {
        this.factories = new Factories(factoryEfficiencies);
        this.populationName = populationName;
    }

    public Factories getFactories() {
        return factories;
    }

    public Population crossover(double p, String populationName) {
        ArrayList<FactoryEfficiency> secondGen = new ArrayList<>();

        for(int i = 0; i < factories.getFactories().size(); i++) {
            if(Math.random() < p) {
                FactoryEfficiency f1 = factories.getFactories().get(i).clone();
                FactoryEfficiency f2 = factories.getFactories().get((i+1) % factories.getFactories().size()).clone();
                Integer[] swapSpace = f1.getFactory().factory[0];
                f1.getFactory().factory[0] = f2.getFactory().factory[0];
                f2.getFactory().factory[0] = swapSpace;
                f1.getFactory().factoryFix();
                f2.getFactory().factoryFix();
                f1.reevaluateEfficiency();
                f2.reevaluateEfficiency();
                secondGen.add(f1);
                secondGen.add(f2);
                i++;
            } else {
                secondGen.add(factories.getFactories().get(i).clone());
            }
        }
        return  new Population(secondGen, populationName);
    }

    public void mutation(double p, int offset) {
        for(int i = 0; i < factories.getFactories().size(); i++) {
            if (Math.random() < p) {
                FactoryEfficiency f1 = factories.getFactories().get(i);
                f1.getFactory().mutate(offset);
                f1.reevaluateEfficiency();
            }
        }
    }

    public FactoryEfficiency tournamentSelection(int n) {
        ArrayList<FactoryEfficiency> chosenOnes = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            chosenOnes.add(factories.getFactories().get((int)((Math.random() * (factories.getFactories().size())))));
        }

        Factories factories = new Factories(chosenOnes);

        return factories.findBestFactory();
    }

    public FactoryEfficiency rouletteSelection() {
        long sum = factories.sumEfficiencySquared();

        for(FactoryEfficiency factory : factories.getFactories()) {
           factory.weight = (long) factory.getFactoryEfficiency() * (long) factory.getFactoryEfficiency() / (double)sum;
        }

        double sum2 = factories.sumBestAndWorstWeight();
        double weightSum = 0.0;
        for(FactoryEfficiency factory : factories.getFactories()) {
            weightSum += sum2 - factory.weight;
            factory.weight = weightSum;
        }

        double luckyOne = Math.random() * weightSum;
        factories.sortFactoriesByWeight();
        FactoryEfficiency theChosenOne = new FactoryEfficiency(Integer.MAX_VALUE);

        for(FactoryEfficiency factoryEfficiency : factories.getFactories()) {
            if(luckyOne <= factoryEfficiency.weight) {
                theChosenOne = factoryEfficiency;
                break;
            }
        }
        return theChosenOne;
    }
}
