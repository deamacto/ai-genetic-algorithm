import dataConfig.DataType;
import dataConfig.Difficulty;
import model.FactoryModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class FactoryModelTest {

    Integer[][] factory = {{0, 1}, {2, 3}};
    FactoryModel factoryModel = new FactoryModel(factory, new DataType(Difficulty.TEST));

    @Test
    public void testMachineDistance() {
        assertEquals(2, factoryModel.machinesDistance(1, 2));
    }

    @Test
    public void testCalculatingEfficiency() {
        assertEquals(282, factoryModel.factoryEfficiency());
    }

    @Test
    public void testMutation() {
        Integer[][] mutatedFactory = {{2, 3}, {0, 1}};
        factoryModel.mutate(2);

        assertEquals(mutatedFactory, factory);
    }

    @Test
    public void testFixing() {
        Integer[][] factory = {{0, 1}, {1, 0}};
        FactoryModel fm = new FactoryModel(factory, new DataType(Difficulty.TEST));
        fm.factoryFix();

        ArrayList<Integer> flattenFM = (ArrayList<Integer>) Arrays.stream(fm.getFactory())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());

        Collections.sort(flattenFM);
        assertEquals(Arrays.asList(0, 1, 2, 3), flattenFM);
    }
}
