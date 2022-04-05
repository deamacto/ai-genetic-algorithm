import dataConfig.DataType;
import dataConfig.Difficulty;
import model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DataType dt = new DataType(Difficulty.HARD);

        Statistics tournamentStats = TournamentPopulation(dt);
        System.out.println(tournamentStats);

        Statistics rouletteStats = RoulettePopulation(dt);
        System.out.println(rouletteStats);

        Statistics randomStats = Random(dt);
        System.out.println(randomStats);
    }

    public static Statistics TournamentPopulation(DataType dataType) {
        String populationName  = "tournament";
        Population population = new Population(dataType, 500, populationName);
        ArrayList<FactoryEfficiency> bestFactories = new ArrayList<>();
        ArrayList<FactoryEfficiency> worstFactories = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            bestFactories.add(population.getFactories().findBestFactory());
            worstFactories.add(population.getFactories().findWorstFactory());

            ArrayList<FactoryEfficiency> newPopulation = new ArrayList<>();

            for(int j = 0; j < population.getFactories().getFactories().size(); j++) {
                newPopulation.add(population.tournamentSelection(5).clone());
            }

            population = new Population(newPopulation, populationName);

            population = population.crossover(0.2, populationName);
            population.mutation(0.1, 1);
        }

        Factories bests = new Factories(bestFactories);
        Factories worst = new Factories(worstFactories);

        return new Statistics(bests.findBestFactory().getFactoryEfficiency(), worst.findWorstFactory().getFactoryEfficiency(), bests.avg(), bests.stdDeviation());
    }

    public static Statistics RoulettePopulation(DataType dataType) {
        String populationName  = "roulette";
        Population population = new Population(dataType, 500, populationName);
        ArrayList<FactoryEfficiency> bestFactories = new ArrayList<>();
        ArrayList<FactoryEfficiency> worstFactories = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            bestFactories.add(population.getFactories().findBestFactory());
            worstFactories.add(population.getFactories().findWorstFactory());

            ArrayList<FactoryEfficiency> newPopulation = new ArrayList<>();

            for(int j = 0; j < population.getFactories().getFactories().size(); j++) {
                newPopulation.add(population.rouletteSelection().clone());
            }

            population = new Population(newPopulation, populationName);

            population = population.crossover(0.2, populationName);
            population.mutation(0.1, 1);
        }

        Factories bests = new Factories(bestFactories);
        Factories worst = new Factories(worstFactories);

        return new Statistics(bests.findBestFactory().getFactoryEfficiency(), worst.findWorstFactory().getFactoryEfficiency(), bests.avg(), bests.stdDeviation());
    }

    public static Statistics Random(DataType dataType) {
        String populationName  = "random";
        Population population = new Population(dataType, 500, populationName);
        ArrayList<FactoryEfficiency> bestFactories = new ArrayList<>();
        ArrayList<FactoryEfficiency> worstFactories = new ArrayList<>();

        for(int i = 0; i < 25; i++) {
            bestFactories.add(population.getFactories().findBestFactory());
            worstFactories.add(population.getFactories().findWorstFactory());

            ArrayList<FactoryEfficiency> newPopulation = new ArrayList<>();

            for(int j = 0; j < population.getFactories().getFactories().size(); j++) {
                newPopulation.add(population.getFactories().getFactories().get((int)(Math.random() * population.getFactories().getFactories().size())).clone());
            }

            population = new Population(newPopulation, populationName);
        }

        Factories bests = new Factories(bestFactories);
        Factories worst = new Factories(worstFactories);

        return new Statistics(bests.findBestFactory().getFactoryEfficiency(), worst.findWorstFactory().getFactoryEfficiency(), bests.avg(), bests.stdDeviation());
    }
}
