package model;

import dataConfig.DataType;
import input.DataReader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class FactoryModel implements Cloneable{
    Integer[][] factory;
    int machines;
    DataType dt;

    public FactoryModel(DataType dt) {
        this.dt = dt;
        this.machines = dt.machinesAmount;
        factory = new Integer[dt.factoryHeight][dt.factoryWidth];
    }

    public FactoryModel(Integer[][] factory, DataType dt) {
        this.factory = factory;
        this.dt = dt;
        this.machines = dt.machinesAmount;
    }

    public Integer[][] getFactory() {
        return factory;
    }

    // fills "factory" with ints representing machines numbers
    public void fillFactory() {
        ArrayList<Integer> freeSpots = makePossibleSpotsList();
        for(int i = 0; i < machines; i++) {
            int randomInt = (int) ((Math.random() * (freeSpots.size())));
            int spot = freeSpots.get(randomInt);
            factory[spot / factory[0].length][spot % factory[0].length] = i;
            freeSpots.remove(randomInt);
        }
    }

    // makes list of available spots for empty factory
    private ArrayList<Integer> makePossibleSpotsList() {
        ArrayList<Integer> spots = new ArrayList<>();
        for(int i = 0; i < factory.length * factory[0].length; i++) {
            spots.add(i);
        }
        return spots;
    }

    //
    public int machinesDistance(int machineA, int machineB) {
        int machineAcordx = -1;
        int machineAcordy = -1;
        int machineBcordx = -1;
        int machineBcordy = -1;

        for(int i = 0; i < factory.length; i++) {
            for(int j = 0; j <  factory[0].length; j++) {
                if(factory[i][j] != null) {
                    if (factory[i][j] == machineA) {
                        machineAcordy = i;
                        machineAcordx = j;
                    } else if (factory[i][j] == machineB) {
                        machineBcordy = i;
                        machineBcordx = j;
                    }
                }
            }
        }

        if((machineAcordx == -1 && machineAcordy == -1) || (machineBcordx == -1 && machineBcordy == -1)) {
            return 0;
        }

        return Math.abs(machineAcordx - machineBcordx) + Math.abs(machineAcordy - machineBcordy);
    }

    public int factoryEfficiency() {
        ArrayList<FLOModel> flo = DataReader.readFLO(dt.flowFile, dt.costFile);
        int sum = 0;
        for (FLOModel floModel : flo) {
            sum += floModel.cost * floModel.amount * machinesDistance(floModel.source, floModel.dest);
        }
        return sum;
    }

    public void factoryFix() {
        HashSet<Integer> machineNrs = new HashSet<>();
        for(int i = 0; i < machines; i++) {
            machineNrs.add(i);
        }

        ArrayList<Integer> duplicates = new ArrayList<>();

        for(int i = 0; i < factory.length; i ++) {
            for(int j = 0; j < factory[0].length; j++) {
                if(machineNrs.contains(factory[i][j])) {
                    machineNrs.remove(factory[i][j]);
                } else if(factory[i][j] != null){
                    duplicates.add(factory[i][j]);
                }
            }
        }

        machineNrs.addAll(duplicates);
        ArrayList<Integer> unusedMachines = new ArrayList<>(machineNrs);
        ArrayList<Integer> spots = new ArrayList<>();

        for(int i = 0; i < factory.length; i ++) {
            for (int j = 0; j < factory[0].length; j++) {
                if(duplicates.contains(factory[i][j])) {
                    factory[i][j] = null;
                    spots.add(i * factory[0].length + j);
                } else if(factory[i][j] == null) {
                    spots.add(i * factory[0].length + j);
                }
            }
        }

        int unusedMachinesSize = unusedMachines.size();

        for(int i = 0; i < unusedMachinesSize; i++) {
            int randomInt = (int) ((Math.random() * (spots.size())));
            int spot = spots.get(randomInt);
            factory[spot / factory[0].length][spot % factory[0].length] = unusedMachines.remove(0);
            spots.remove(randomInt);
        }
    }

    public void mutate(int offset) {
        for(int i = 0; i < factory.length; i++) {
            for(int j = 0; j < factory[0].length; j++) {
                if(factory[i][j] != null) {
                    factory[i][j] = (factory[i][j] + offset) % machines;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder f = new StringBuilder("FactoryModel \n");
        for (Integer[] integers : factory) {
            f.append("[");
            for (int j = 0; j < factory[0].length; j++) {
                if (integers[j] == null) {
                    f.append(" _ ");
                } else {
                    f.append(" ").append(integers[j]).append(" ");
                }
            }
            f.append("]\n");
        }
        return f.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        } else {
            FactoryModel that = (FactoryModel) o;
            for(int i = 0; i < factory.length; i++) {
                for(int j = 0; j < factory[0].length; j++) {
                    if (!Objects.equals(this.factory[i][j], that.factory[i][j])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected FactoryModel clone() {
        Integer[][] newFactory = new Integer[dt.factoryHeight][dt.factoryWidth];
        for(int i = 0; i < factory.length; i++) {
            System.arraycopy(factory[i], 0, newFactory[i], 0, factory[0].length);
        }
        return new FactoryModel(newFactory, this.dt);
    }
}
