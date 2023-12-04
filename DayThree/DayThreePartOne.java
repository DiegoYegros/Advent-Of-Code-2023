package DayThree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utils.Utils;

public class DayThreePartOne {

    public static void main(String[] args) throws IOException {
        List<String> lines = Utils.getLinesFromFilePath("DayThree/DayThree.txt");
        // Pienso que puedo obtener el numero de linea y obtener el indice donde empieza
        // + el indice donde termina.
        // Luego, hago una busqueda a sus alrededores por algun simbolo.
        List<NumberInChase> numbers = getNumbersFromString(lines);
        for (var numb : numbers) {
            System.out.println(lines.get(numb.getLine()).substring(numb.getStartIndex(), numb.getEndIndex()));
        }
        List<Integer> validNumbers = new ArrayList<>();
        for (var number : numbers) {
            if (9 ==Integer.parseInt(lines.get(number.getLine()).substring(number.getStartIndex(), number.getEndIndex()))){
                System.out.println("yeah");
            }
            boolean isMostUp = false;
            boolean isMostDown = false;
            boolean isMostLeft = false;
            boolean isMostRight = false;
            boolean isEmptyUp = true;
            boolean isEmptyDown = true;
            boolean isEmptyLeft = true;
            boolean isEmptyRight = true;
            boolean isEmptyDiagonal = true;
            // check up
            if (number.getLine() == 0) {
                isMostUp = true;
            } else {
                if (lines.get(number.getLine() - 1).substring(number.getStartIndex(), number.getEndIndex())
                        .replace(".", "").isEmpty()) {
                } else {
                    isEmptyUp = false;
                }
            }
            // check down
            if (number.getLine() == lines.size()-1) {
                isMostDown = true;
            } else {
                if (lines.get(number.getLine() + 1).substring(number.getStartIndex(), number.getEndIndex())
                        .replace(".", "").isEmpty()) {
                } else {
                    isEmptyDown = false;
                }
            }
            // check left
            if (number.getStartIndex() == 0) {
                isMostLeft = true;
            } else {
                if (String.valueOf(lines.get(number.getLine()).charAt(number.getStartIndex()-1)).replace(".", "")
                        .isEmpty()) {
                } else {
                    isEmptyLeft = false;
                }
            }
            // check right

            if (number.getEndIndex() == lines.get(number.getLine()).length()) {
                isMostRight = true;
            } else {
                if (String.valueOf(lines.get(number.getLine()).charAt(number.getEndIndex())).replace(".", "")
                        .isEmpty()) {
                } else {
                    isEmptyRight = false;
                }
            }
            // check diagonals
            if (!isMostDown && !isMostLeft) {
                if (String.valueOf(lines.get(number.getLine() + 1).charAt(number.getStartIndex()-1)).replace(".", "")
                        .isEmpty()) {
                } else {
                    isEmptyDiagonal = false;
                }
            }
            if (!isMostDown && !isMostRight) {
                if (String.valueOf(lines.get(number.getLine() + 1).charAt(number.getEndIndex())).replace(".", "")
                        .isEmpty()) {
                } else {
                    isEmptyDiagonal = false;
                }
            }
            if (!isMostUp && !isMostLeft) {
                if (String.valueOf(lines.get(number.getLine() - 1).charAt(number.getStartIndex()-1)).replace(".", "")
                        .isEmpty()) {
                } else {
                    isEmptyDiagonal = false;
                }
            }
            if (!isMostUp && !isMostRight) {
                if (String.valueOf(lines.get(number.getLine() - 1).charAt(number.getEndIndex())).replace(".", "")
                        .isEmpty()) {
                } else {
                    isEmptyDiagonal = false;
                }
            }
            if (!isEmptyDiagonal || !isEmptyDown || !isEmptyLeft || !isEmptyRight || !isEmptyUp) {
                validNumbers.add(Integer
                        .parseInt(lines.get(number.getLine()).substring(number.getStartIndex(), number.getEndIndex())));
            }
        }
        int sum = 0;
        for (var valid : validNumbers) {
            sum += valid;
            System.out.println("-- " + valid);
        }

        System.out.println("Sum of valids: " + sum);
    }

    private static List<NumberInChase> getNumbersFromString(List<String> lines) {
        List<NumberInChase> numbers = new ArrayList<>();
        int index = 0;
        for (var line : lines) {
            NumberInChase numberInChase = new NumberInChase();
            numberInChase.setLine(index);
            int location = 0;
            StringBuilder sb = new StringBuilder();
            Integer firstLocation = -1;
            while (location < line.length()) {
                if (parse(line.charAt(location))) {
                    if (firstLocation == -1) {
                        numberInChase.setStartIndex(location);
                        firstLocation = location;
                    }
                    sb.append(String.valueOf(line.charAt(location)));
                } else {
                    numberInChase.setEndIndex(location);
                    if (!sb.isEmpty()) {
                        numbers.add(numberInChase);
                        numberInChase = new NumberInChase();
                        numberInChase.setLine(index);
                    }
                    sb = new StringBuilder();
                    firstLocation = -1;
                }
                if (location == line.length()-1){
                    if (!sb.isEmpty()){
                        numberInChase.setEndIndex(location+1);
                        numberInChase.setLine(index);
                        numbers.add(numberInChase);
                    }
                }
                location++;
            }
            index++;
        }
        return numbers;
    }

    private static boolean parse(char charAt) {
        try {
            Integer.parseInt(String.valueOf(charAt));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

class NumberInChase {

    int line;
    int startIndex;
    int endIndex;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}