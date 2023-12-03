import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayTwoPartOne {
    public static void main(String[] args) throws IOException {
        var lines = Utils.getLinesFromFilePath("src/DayTwo.txt");
        var containers = parseInfoFromLines(lines);
        int resolution = solveProblemFromContainers(containers);
        System.out.println("Resolution: "+ resolution);

    }

    private static int solveProblemFromContainers(List<Container> containers) {
        int totalSum = 0;
        int MAX_AMOUNT_FOR_RED = 12;
        int MAX_AMOUNT_FOR_BLUE = 14;
        int MAX_AMOUNT_FOR_GREEN = 13;
        for (var container : containers){
            boolean isCandidate = true;
            for (var bag : container.getBag()){
                if (bag.getColor().equals(Color.RED)){
                    if (bag.getAmount()>MAX_AMOUNT_FOR_RED){
                        isCandidate = false;
                        break;
                    }
                }
                if (bag.getColor().equals(Color.BLUE)){
                    if (bag.getAmount()>MAX_AMOUNT_FOR_BLUE){
                        isCandidate = false;
                        break;
                    }
                }
                if (bag.getColor().equals(Color.GREEN)){
                    if (bag.getAmount()>MAX_AMOUNT_FOR_GREEN){
                        isCandidate = false;
                        break;
                    }
                }
            }
            if (isCandidate){
                totalSum+=Integer.parseInt(container.getGameID().trim());
            }
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
                var eachPlay = bag.split(", ");
                for (var play : eachPlay) {
                    var playObject = new Play();
                    for (var color : Color.values()) {
                        Pattern pattern = Pattern.compile(Pattern.quote(color.getColor()));
                        Matcher matcher = pattern.matcher(play);
                        if (matcher.find()) {
                            var newPlay = play.replace(play.substring(matcher.start() - 1, matcher.end()), "");
                            var playTimes = Integer.parseInt(newPlay);
                            playObject.setAmount(playTimes);
                            playObject.setColor(color);
                            listOfPlays.add(playObject);
                        }
                    }
                }
                container.setBag(listOfPlays);
            }
            containers.add(container);
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

        public static Color fromCode(String code) {
            for (var color : Color.values()) {
                if (code.equals(color.getColor())) {
                    return color;
                }
            }
            return null;
        }
    }
}
