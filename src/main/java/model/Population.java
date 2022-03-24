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

    private Population(ArrayList<FactoryEfficiency> factoryEfficiencies, String populationName) {
        this.factories = new Factories(factoryEfficiencies);
        this.populationName = populationName;
    }

    public Population crossover(double p, String populationName) {
        ArrayList<FactoryEfficiency> secondGen = new ArrayList<>();

        for(int i = 0; i < factories.getFactories().size(); i++) {
            if(Math.random() < p) {
                FactoryEfficiency f1 = factories.getFactories().get(i).clone();
                FactoryEfficiency f2 = factories.getFactories().get(i+1).clone();
                Integer[] swapSpace = f1.getFactory().factory[0];
                f1.getFactory().factory[0] = f2.getFactory().factory[0];
                f2.getFactory().factory[0] = swapSpace;
                f1.getFactory().factoryFix();
                f2.getFactory().factoryFix();
                secondGen.add(f1);
                secondGen.add(f2);
                i++;
            } else {
                secondGen.add(factories.getFactories().get(i).clone());
            }
        }
        return  new Population(secondGen, populationName);
    }

    public Population mutation(double p, int offset, String populationName) {
        ArrayList<FactoryEfficiency> secondGen = new ArrayList<>();

        for(int i = 0; i < factories.getFactories().size(); i++) {
            if (Math.random() < p) {
                FactoryEfficiency f1 = factories.getFactories().get(i).clone();
                f1.getFactory().mutate(offset);
                secondGen.add(f1);
            } else {
                secondGen.add(factories.getFactories().get(i).clone());
            }
        }
        return new Population(secondGen, populationName);
    }

    public FactoryEfficiency tournamentSelection(int n) {
        ArrayList<FactoryEfficiency> chosenOnes = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            chosenOnes.add(factories.getFactories().get((int)((Math.random() * (factories.getFactories().size())))));
        }

        Factories factories = new Factories(chosenOnes);

        return factories.findBestFactory();
    }

    public FactoryEfficiency RouletteSelection() {
        int sum = factories.sumEfficiency();

        for(FactoryEfficiency factory : factories.getFactories()) {
           factory.weight = (1 - (factory.getFactoryEfficiency() / (double)sum));
        }

        double sum2 = factories.sumWeight();
        double weightSum = 0.0;
        for(FactoryEfficiency factory : factories.getFactories()) {
            weightSum += factory.weight / sum2;
            factory.weight = weightSum;
        }

        double luckyOne = Math.random();
        factories.sortFactoriesByWeight();
        FactoryEfficiency theChosenOne = new FactoryEfficiency(Integer.MAX_VALUE);

        for(FactoryEfficiency factoryEfficiency : factories.getFactories()) {
            if(luckyOne <= factoryEfficiency.weight) {
                theChosenOne = factoryEfficiency;
            }
        }
        System.out.println(theChosenOne.getFactoryEfficiency());
        return theChosenOne;
    }
}
