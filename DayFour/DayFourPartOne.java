import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayFourPartOne {
    public static void main(String[] args) throws IOException {
        var list = Utils.getLinesFromFilePath("DayFour/DayFour.txt");
        int resolution = resolveProblem(list);
        System.out.println("Resolution: " + resolution);
    }

    public static int resolveProblem(List<String> list) {
        int result = 0;
        for (var line : list) {
            int points = 0;
            Card card = new Card();
            int id = Integer.parseInt(line.substring(5, line.indexOf(":")).strip());
            card.setId(id);
            String[] winningAndLosing = line.substring(line.indexOf(":") + 1).split("\\|");
            String[] winningNumbers = winningAndLosing[0].split(" ");
            String[] handNumbers = winningAndLosing[1].split(" ");
            card.setWinning(
                    Arrays.asList(winningNumbers).stream().filter(c -> !c.isEmpty())
                            .map(Integer::valueOf)
                            .collect(Collectors.toList()));
            card.setHand(Arrays.asList(handNumbers).stream().filter(c -> !c.isEmpty())
                    .map(Integer::valueOf)
                    .collect(Collectors.toList()));

            for (var num : card.getHand()) {
                if (card.getWinning().contains(num)) {
                    if (points == 0) {
                        points++;
                    } else {
                        points = points * 2;
                    }
                }
            }
            result += points;
        }
        return result;
    }

}

