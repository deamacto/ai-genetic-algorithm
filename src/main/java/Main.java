import dataConfig.DataType;
import dataConfig.Difficulty;
import input.DataReader;
import model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DataType dt = new DataType(Difficulty.EASY);

    }

    public static Statistics TournamentPopulation(DataType dataType) {
        String populationName  = "tournament";
        Population population = new Population(dataType, 100, populationName);
        ArrayList<FactoryEfficiency> bestFactories = new ArrayList<>();
        ArrayList<FactoryEfficiency> worstFactories = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            bestFactories.add(population.getFactories().findBestFactory());
            worstFactories.add(population.getFactories().findWorstFactory());

            ArrayList<FactoryEfficiency> newPopulation = new ArrayList<>();

            for(int j = 0; j < population.getFactories().getFactories().size(); j++) {
                newPopulation.add(population.tournamentSelection(20));
            }

            population = new Population(newPopulation, populationName);

            population.crossover(0.3, populationName);
            population.mutation(0.2, 1, populationName);
        }

        Factories bests = new Factories(bestFactories);
        Factories worst = new Factories(worstFactories);

        Statistics statistics = new Statistics(bests.findBestFactory().getFactoryEfficiency(), worst.findWorstFactory().getFactoryEfficiency(), bests.avg(), bests.stdDeviation());
        return statistics;
    }
}
