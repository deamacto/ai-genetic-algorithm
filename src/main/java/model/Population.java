package model;

import java.util.ArrayList;

import dataConfig.DataType;

public class Population {

    private Factories factories;
    private String populationName;

    public Population(DataType dt, int n, String populationName) {
        this.factories = new Factories(dt, n);
        this.populationName = populationName;
    }

    public Population crossover(double p) {
        ArrayList<FactoryModel> secondGen = new ArrayList<>();

        for(int i = 0; i < factories.size(); i++) {
            if(Math.random() < p) {
                FactoryModel f1 = (FactoryModel) factories.get(i).clone();
                FactoryModel f2 = (FactoryModel) factories.get(i+1).clone();
                Integer[] swapSpace = f1.factory[0];
                f1.factory[0] = f2.factory[0];
                f2.factory[0] = swapSpace;
                f1.factoryFix();
                f2.factoryFix();
                secondGen.add(f1);
                secondGen.add(f2);
                i++;
            } else {
                secondGen.add((FactoryModel) factories.get(i).clone());
            }
        }
        return  secondGen;
    }

    public ArrayList<FactoryModel> mutation(double p, int offset) throws CloneNotSupportedException {
        ArrayList<FactoryModel> secondGen = new ArrayList<>();

        for(int i = 0; i < factories.size(); i++) {
            if (Math.random() < p) {
                FactoryModel f1 = (FactoryModel) factories.get(i).clone();
                f1.mutate(offset);
                secondGen.add(f1);
            } else {
                secondGen.add((FactoryModel) factories.get(i).clone());
            }
        }
        return secondGen;
    }

    public static FactoryModel tournamentSelection(int n, Population population) {
        HashSet<FactoryModel> chosenOnes = new HashSet<>();
        for(int i = 0; i < n; i++) {
            chosenOnes.add(population.getFactories().get((int)((Math.random() * (population.getFactories().size())))));
        }

        int bestEfficiency = Integer.MAX_VALUE;
        FactoryModel best = null;
        for(FactoryModel elem : chosenOnes) {
            if(bestEfficiency > elem.factoryEfficiency(population.getRoutes())) {
                best = elem;
                bestEfficiency = elem.factoryEfficiency(population.getRoutes());
            }
        }
        return best;
    }

    public FactoryModel RouletteSelection() {
        ArrayList<Double> weight = new ArrayList<>();
        int sum = efficiencySum();
        for(FactoryModel factory : factories) {
            weight.add(1 - (factory.factoryEfficiency() / (double)sum));
        }
        double sum2 = castedEfficiencySum(weight);

        double weightSum = 0.0;
        for(int i = 0; i < weight.size(); i++) {
            weightSum += weight.get(1)/sum2;
            weight.set(i, weightSum);
        }

        double luckyOne = Math.random();
        HashMap<Double, FactoryModel> factoriesWeight = weightedFactories(weight, population);

        FactoryModel theChosenOne = null;

        for(Double fw : weight) {
            if(fw >= luckyOne) {
                theChosenOne = factoriesWeight.get(fw);
            }
        }
        assert theChosenOne != null;
        System.out.println(theChosenOne.factoryEfficiency(population.getRoutes()));
        return theChosenOne;
    }


    static double castedEfficiencySum(ArrayList<Double> list) {
        double sum = 0.0;
        for(Double value : list) {
            sum += value;
        }
        return sum;
    }

    static HashMap<Double, FactoryModel> weightedFactories(ArrayList<Double> weight, Population population) {
        HashMap<Double, FactoryModel> factoriesWeight = new HashMap<>();
        for(int i = 0; i < population.getFactories().size(); i++) {
            factoriesWeight.put(weight.get(i), population.getFactories().get(i));
        }
        return factoriesWeight;
    }

}
