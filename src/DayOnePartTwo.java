import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayOnePartTwo {
    public static void main(String[] args) throws IOException {
        long before = System.currentTimeMillis();
        var list = Utils.getLinesFromFilePath("src/DayOne.txt");
        int totalSum = resolveDayOneProblemTwo(list);
        System.out.println("Total sum: " + totalSum + " --- Exec time: "+ (System.currentTimeMillis()-before)+" ms");
    }

    private static int resolveDayOneProblemTwo(List<String> list) {
        int totalSum = 0;
        for (var item : list) {
            int firstIndex = Integer.MAX_VALUE;
            int firstNumber = Integer.MIN_VALUE;
            int lastIndex = 0;
            int lastNumber = 0;
            var valores = new ArrayList<Valor>();
            for (var number : Numbers.values()) {
                Pattern pattern = Pattern.compile(Pattern.quote(number.getNumber()));
                Matcher matcher = pattern.matcher(item);
                while (matcher.find()) {
                    String substring = item.substring(matcher.start(), matcher.end());
                    valores.add(new Valor(matcher.start(), Numbers.fromCode(substring).getValue()));
                }
            }
            for (int x = 0; x < item.length(); x++) {
                Integer number = Utils.getNumberFromString(String.valueOf(item.charAt(x)));
                if (number != null) {
                    valores.add(new Valor(x, Integer.parseInt(String.valueOf(item.charAt(x)))));
                }
            }

            for (var valor : valores) {
                if (valor.getIndex() < firstIndex) {
                    firstIndex = valor.getIndex();
                    firstNumber = valor.getValue();
                }
                if (valor.getIndex() > lastIndex) {
                    lastIndex = valor.getIndex();
                    lastNumber = valor.getValue();
                }
            }
            String first = String.valueOf(firstNumber);
            String last;
            if (lastNumber == 0) {
                last = first;
            } else {
                last = String.valueOf(lastNumber);
            }
            totalSum = totalSum + Integer.parseInt(first + last);
            System.out.println("Item:" + item + "  |  First: " + first + " ------ Last: " + last + "| sum: " + totalSum);
        }
        return totalSum;
    }
}
