package model;

import dataConfig.DataType;
import dataConfig.Difficulty;
import input.DataReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FactoryModel implements Cloneable{
    Integer[][] factory;
    int machines;
    DataType dt;

    public FactoryModel(DataType dt) {
        this.dt = dt;
        this.machines = dt.machinesAmount;
        factory = new Integer[dt.factoryHeight][dt.factoryWidth];
    }

    // fills "factory" with ints representing machines numbers
    public void fillFactory() {
        ArrayList<Integer> freeSpots = makePossibleSpotsList();
        for(int i = 0; i < machines; i++) {
            int randomInt = (int) ((Math.random() * (freeSpots.size())));
            int spot = freeSpots.get(randomInt);
            factory[spot / factory.length][spot % factory[0].length] = i;
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
        int machineAcordx = 0;
        int machineAcordy = 0;
        int machineBcordx = 0;
        int machineBcordy = 0;

        for(int i = 0; i < factory.length; i++) {
            for(int j = 0; j <  factory[0].length; j++) {
                if(factory[i][j] == machineA) {
                    machineAcordy = i;
                    machineAcordx = j;
                } else if(factory[i][j] == machineB) {
                    machineBcordy = i;
                    machineBcordx = j;
                }
            }
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

    //TODO implement factory fixing method
    public void factoryFix() {

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
        String f = "FactoryModel \n";
        for (Integer[] integers : factory) {
            f += "[";
            for (int j = 0; j < factory[0].length; j++) {
                if (integers[j] == null) {
                    f += " _ ";
                } else {
                    f += " " + integers[j] + " ";
                }
            }
            f += "]\n";
        }
        return f;
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

    // TODO add clone method
    @Override
    protected Object clone() {
        return new Object();
    }
}
