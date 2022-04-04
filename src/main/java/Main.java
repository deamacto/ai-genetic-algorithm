import dataConfig.DataType;
import dataConfig.Difficulty;
import model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DataType dt = new DataType(Difficulty.FLAT);

        Statistics tournamentStats = TournamentPopulation(dt);
        System.out.println(tournamentStats);

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
                newPopulation.add(population.tournamentSelection(5).clone());
            }

            population = new Population(newPopulation, populationName);

            population = population.crossover(0.6, populationName);
            population.mutation(0.5, 1);
        }

        Factories bests = new Factories(bestFactories);
        Factories worst = new Factories(worstFactories);

        return new Statistics(bests.findBestFactory().getFactoryEfficiency(), worst.findWorstFactory().getFactoryEfficiency(), bests.avg(), bests.stdDeviation());
    }
}
