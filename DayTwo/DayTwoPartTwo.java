import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utils.Utils;

public class DayTwoPartTwo {
    public static void main(String[] args) throws IOException {
        var lines = Utils.getLinesFromFilePath("src/DayTwo.txt");
        var containers = parseInfoFromLines(lines);  
        int resolution = solveProblemFromContainers(containers);
        System.out.println("Resolution: "+ resolution);

    }

    private static int solveProblemFromContainers(List<Container> containers) {
        int totalSum = 0;
        for (var container : containers){
            int MAX_AMOUNT_FOR_RED = Integer.MIN_VALUE;
            int MAX_AMOUNT_FOR_BLUE = Integer.MIN_VALUE;
            int MAX_AMOUNT_FOR_GREEN = Integer.MIN_VALUE;
            for (var bag : container.getBag()){
                if (bag.getColor().equals(Color.RED)){
                    if(bag.getAmount()>MAX_AMOUNT_FOR_RED){
                        MAX_AMOUNT_FOR_RED = bag.getAmount();
                    }
                }
                if (bag.getColor().equals(Color.BLUE)){
                    if(bag.getAmount()>MAX_AMOUNT_FOR_BLUE){
                        MAX_AMOUNT_FOR_BLUE = bag.getAmount();
                    }
                }
                if (bag.getColor().equals(Color.GREEN)){
                    if(bag.getAmount()>MAX_AMOUNT_FOR_GREEN){
                        MAX_AMOUNT_FOR_GREEN = bag.getAmount();
                    }
                }
            }
            totalSum+= MAX_AMOUNT_FOR_BLUE*MAX_AMOUNT_FOR_GREEN*MAX_AMOUNT_FOR_RED;
        }
        return totalSum;
    }

    private static List<Container> parseInfoFromLines(List<String> lines) {
        var containers = new ArrayList<Container>();
        for (var line : lines) {
            String gameID = line.substring(4, line.indexOf(":"));
            Container container = new Container();
            container.setGameID(gameID);
    
            var bags = line.substring(line.indexOf(":") + 2);
            var eachBag = bags.split("; ");
    
            var listOfPlays = new ArrayList<Play>();
            for (var bag : eachBag) {
                String[] playDetails = bag.split(", ");
                for (String play : playDetails) {
                    Play playObject = new Play();
                    for (Color color : Color.values()) {
                        if (play.contains(color.getColor())) {
                            String amountStr = play.split(" ")[0];
                            int playTimes = Integer.parseInt(amountStr);
                            playObject.setAmount(playTimes);
                            playObject.setColor(color);
                            listOfPlays.add(playObject);
                        }
                    }
                }
            }
            container.setBag(listOfPlays);
        }
        return containers;
    }
    private static class Container {
        String gameID;
        List<Play> bag;

        public String getGameID() {
            return gameID;
        }

        public void setGameID(String gameID) {
            this.gameID = gameID;
        }

        public List<Play> getBag() {
            return bag;
        }

        public void setBag(List<Play> bag) {
            this.bag = bag;
        }
    }

    private static class Play {
        Integer amount;
        Color color;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

    private enum Color {
        RED("red"),
        BLUE("blue"),
        GREEN("green");

        private final String color;

        Color(String color) {
            this.color = color;
        }

        public String getColor() {
            return this.color;
        }
    }
}
