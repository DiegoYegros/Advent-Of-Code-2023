import java.io.*;
import java.util.List;

public class DayOnePartOne {
    public static void main(String[] args) throws IOException {
        var list = Utils.getLinesFromFilePath("src/DayOne.txt");

        resolveDayOneProblemOne(list);
    }

    private static int resolveDayOneProblemOne(List<String> list) {
        int totalSum = 0;
        for (var item : list) {
            int firstNumber = 0;
            int lastNumber = 0;
            boolean searchFirstNumber = true;
            boolean searchLastNumber = true;
            for (int x = 0; x < (item.length()); x++) {
                if (searchFirstNumber) {
                    Integer number = Utils.getNumberFromString(String.valueOf(item.charAt(x)));
                    if (number != null) {
                        firstNumber = number;
                        searchFirstNumber = false;
                    }
                }
                if (searchLastNumber) {
                    Integer number = Utils.getNumberFromString(String.valueOf(item.charAt(item.length() - 1 - x)));
                    if (number != null) {
                        lastNumber = number;
                        searchLastNumber = false;
                    }
                }
            }
            totalSum = totalSum + Integer.parseInt(firstNumber + String.valueOf(lastNumber));
        }
        System.out.println("Answer problem one day one: "+ totalSum);
        return totalSum;
    }
}