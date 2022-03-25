import dataConfig.DataType;
import dataConfig.Difficulty;
import model.Factories;
import model.FactoryEfficiency;
import model.FactoryModel;
import model.Population;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PopulationTest {

    FactoryEfficiency factoryEfficiency1;
    FactoryEfficiency factoryEfficiency2;
    FactoryEfficiency factoryEfficiency3;
    Population population;

    @Before
    public void setup() {
        Integer[][] factory1 = {{0, 1}, {2, 3}};
        FactoryModel factoryModel1 = new FactoryModel(factory1, new DataType(Difficulty.TEST));
        factoryEfficiency1 = new FactoryEfficiency(factoryModel1);
        factoryEfficiency1.weight = 2.0;

        Integer[][] factory2 = {{1, 0}, {2, 3}};
        FactoryModel factoryModel2 = new FactoryModel(factory2, new DataType(Difficulty.TEST));
        factoryEfficiency2 = new FactoryEfficiency(factoryModel2);
        factoryEfficiency2.weight = 1.0;

        Integer[][] factory3 = {{3, 0}, {1, 2}};
        FactoryModel factoryModel3 = new FactoryModel(factory3, new DataType(Difficulty.TEST));
        factoryEfficiency3 = new FactoryEfficiency(factoryModel3);
        factoryEfficiency3.weight = 3.0;

        ArrayList<FactoryEfficiency> factoryEfficiencies = new ArrayList<>();
        factoryEfficiencies.add(factoryEfficiency1);
        factoryEfficiencies.add(factoryEfficiency2);
        factoryEfficiencies.add(factoryEfficiency3);

        population = new Population(factoryEfficiencies, "Test");
    }

    @Test
    public void testTournament() {
        FactoryEfficiency factoryEfficiency = population.tournamentSelection(3);

        assertEquals(factoryEfficiency, factoryEfficiency2);
    }
}
