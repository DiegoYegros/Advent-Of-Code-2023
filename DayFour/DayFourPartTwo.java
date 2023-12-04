import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DayFourPartTwo {
    public static void main(String[] args) throws IOException {
        var list = Utils.getLinesFromFilePath("C:\\code\\Advent-Of-Code-2023\\DayFour\\DayFour.txt");
        List<Card> cards = getListOfCards(list);
        int resolution = solveGivenCards(cards);
        System.out.println("Resolution: " + resolution);
    }

    private static int solveGivenCards(List<Card> cards) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Card card : cards) {
            int amount = 0;
            for (var num : card.getHand()) {
                if (card.getWinning().contains(num)) {
                    amount++;
                }
            }
            map.put(card.getId(), map.getOrDefault(card.getId(), 0) + 1);
            for (int x = 1; x <= amount; x++) {
                int nextCardId = card.getId() + x;
                map.put(nextCardId, map.getOrDefault(nextCardId, 0) + map.get(card.getId()));
            }
        }
        AtomicInteger sumOfValues = new AtomicInteger(0);
        map.forEach((key, value)-> {
            System.out.println("Card: "+ key + " value: "+value);
            sumOfValues.addAndGet(value);
        });
        return sumOfValues.get();
    }


    public static List<Card> getListOfCards(List<String> list) {
        List<Card> cards = new ArrayList<>();
        for (var line : list) {
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
            cards.add(card);
        }
        return cards;
    }
}
