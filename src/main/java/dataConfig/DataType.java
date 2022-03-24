package dataConfig;

public class DataType {
    public String flowFile;
    public String costFile;
    public int factoryWidth;
    public int factoryHeight;
    public int machinesAmount;

    public DataType(Difficulty difficulty) {
        if(difficulty == Difficulty.EASY) {
            flowFile = "data/easy_flow.json";
            costFile = "data/easy_cost.json";
            factoryWidth = 3;
            factoryHeight = 3;
            machinesAmount = 9;
        } else if(difficulty == Difficulty.FLAT) {
            flowFile = "data/flat_flow.json";
            costFile = "data/flat_cost.json";
            factoryWidth = 12;
            factoryHeight = 1;
            machinesAmount = 12;
        } else if(difficulty == Difficulty.HARD) {
            flowFile = "data/hard_flow.json";
            costFile = "data/hard_cost.json";
            factoryWidth = 6;
            factoryHeight = 5;
            machinesAmount = 24;
        } else if(difficulty == Difficulty.TEST) {
            flowFile = "data/easy_flow.json";
            costFile = "data/easy_cost.json";
            factoryWidth = 2;
            factoryHeight = 2;
            machinesAmount = 4;
        }
    }
}
