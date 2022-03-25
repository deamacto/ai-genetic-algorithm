import dataConfig.DataType;
import dataConfig.Difficulty;
import model.Factories;
import model.FactoryEfficiency;
import model.FactoryModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FactoriesTest {

    Factories factories;
    FactoryEfficiency factoryEfficiency1;
    FactoryEfficiency factoryEfficiency2;
    FactoryEfficiency factoryEfficiency3;

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

        factories = new Factories(factoryEfficiencies);
    }

    @Test
    public void testSum() {
        assertEquals(factories.sumEfficiencySquared(), 237128);
    }

    @Test
    public void testBestFactory() {
        assertEquals(factories.findBestFactory(), factories.getFactories().get(1));
    }

    @Test
    public void testWorstFactory() {
        assertEquals(factories.findWorstFactory(), factories.getFactories().get(2));
    }

    @Test
    public void testSumBestAndWorstWeight() {
        assertEquals(factories.sumBestAndWorstWeight(), 4.0, 0.0);
    }

    @Test
    public void testSort() {
        ArrayList<FactoryEfficiency> factoryEfficienciesSorted = new ArrayList<>();
        factoryEfficienciesSorted.add(factoryEfficiency2);
        factoryEfficienciesSorted.add(factoryEfficiency1);
        factoryEfficienciesSorted.add(factoryEfficiency3);
        factories.sortFactoriesByWeight();

        assertEquals(factories.getFactories(), factoryEfficienciesSorted);
    }
}
