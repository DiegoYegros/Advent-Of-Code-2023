package DayThree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import DayThree.GearsInChase;
import Utils.Utils;

public class DayThreePartTwo {

    public static void main(String[] args) throws IOException {
        // Para la parte dos del problema, mi idea es primero obtener todos los numeros,
        // luego los gears (en realidad podria hacerlo de una sola vez pero me llevaria
        // mas tiempo)
        // Luego por cada gear validar todos los lugares posibles donde puede haber un
        // numero, son pocos.
        List<String> lines = Utils.getLinesFromFilePath("DayThree/DayThree.txt");
        List<NumberInChase> numbers = getNumbersFromString(lines);
        List<GearsInChase> gears = getGearsFromString(lines);
        int resolution = solveProblem(lines, numbers, gears);
        System.out.println("Resolution: " + resolution);
    }

    private static int solveProblem(List<String> lines, List<NumberInChase> numbers, List<GearsInChase> gears) {
        int finalResult = 0;
        for (var gear : gears) {
            List<Integer> numbersAroundGear = new ArrayList<>();
            // Primero voy a chequear los lados porque me parece que es lo mas facil.
            // Izquierda
            if (!(gear.getStartIndex() == 0)) {
                var numberInLeft = numbers.stream()
                        .filter(c -> c.getEndIndex() == gear.getStartIndex())
                        .filter(c -> c.getLine() == gear.getLine())
                        .collect(Collectors.toList());
                if (!numberInLeft.isEmpty()) {
                    numbersAroundGear.add(Integer.parseInt(lines.get(gear.getLine())
                            .substring(numberInLeft.get(0).getStartIndex(), numberInLeft.get(0).getEndIndex())));
                }
            }
            // Derecha
            if (!(gear.getEndIndex() == lines.get(gear.getLine()).length() - 1)) {
                var numberInRight = numbers.stream()
                        .filter(c -> c.getStartIndex() == gear.getEndIndex())
                        .filter(c -> c.getLine() == gear.getLine())
                        .collect(Collectors.toList());
                if (!numberInRight.isEmpty()) {
                    numbersAroundGear.add(Integer.parseInt(lines.get(gear.getLine())
                            .substring(numberInRight.get(0).getStartIndex(), numberInRight.get(0).getEndIndex())));
                }
            }
            // Arriba
            if (!(gear.getLine() == 0)) {
                var numbersUp = numbers.stream()
                        .filter(c -> isBetween(gear.getStartIndex(), gear.getEndIndex(), c.getStartIndex(), c.getEndIndex()))
                        .filter(c -> c.getLine() == gear.getLine() - 1)
                        .collect(Collectors.toList());
                if (!numbersUp.isEmpty()) {
                    for (var number : numbersUp) {
                        numbersAroundGear.add(Integer.parseInt(
                                lines.get(number.getLine()).substring(number.getStartIndex(), number.getEndIndex())));
                    }
                }
            }
            // Abajo
            if (!(gear.getLine() == lines.size() - 1)) {
                if (gear.getLine() == 6){
                    int x = 0;
                }
                var numbersUp = numbers.stream()
                        .filter(c -> isBetween(gear.getStartIndex(), gear.getEndIndex(), c.getStartIndex(), c.getEndIndex()))
                        .filter(c -> c.getLine() == gear.getLine() + 1)
                        .collect(Collectors.toList());
                if (!numbersUp.isEmpty()) {
                    for (var number : numbersUp) {
                        numbersAroundGear.add(Integer.parseInt(
                                lines.get(number.getLine()).substring(number.getStartIndex(), number.getEndIndex())));
                    }
                }
            }
            // check si son solo dos numeros around gear
            System.out.println("These are the lines from the gear in line "+gear.getLine()+"! "+ numbersAroundGear.toString());
                
            if (numbersAroundGear.size() == 2) {
                finalResult += numbersAroundGear.stream().mapToInt(x -> x).reduce(1, Math::multiplyExact);
            }
        }
        return finalResult;
    }

    private static boolean isBetween(int baseStart, int baseEnd, int start, int end) {
        if ((start>= baseStart) && (start<=baseEnd)){
            return true;
        }
        if (end>=baseStart && (end<=baseEnd)){
            return true;
        }
        if (end>=baseEnd && (start<=baseStart)){
            return true;
        }
        return false;
    }

    private static List<GearsInChase> getGearsFromString(List<String> lines) {
        List<GearsInChase> gears = new ArrayList<>();
        int index = 0;
        for (var line : lines) {
            GearsInChase gearInChase = new GearsInChase();
            gearInChase.setLine(index);
            int location = 0;
            StringBuilder sb = new StringBuilder();
            Integer firstLocation = -1;
            while (location < line.length()) {
                if (isGear(line.charAt(location))) {
                    if (firstLocation == -1) {
                        gearInChase.setStartIndex(location);
                        firstLocation = location;
                    }
                    sb.append(String.valueOf(line.charAt(location)));
                } else {
                    gearInChase.setEndIndex(location);
                    if (!sb.isEmpty()) {
                        gears.add(gearInChase);
                        gearInChase = new GearsInChase();
                        gearInChase.setLine(index);
                    }
                    sb = new StringBuilder();
                    firstLocation = -1;
                }
                if (location == line.length() - 1) {
                    if (!sb.isEmpty()) {
                        gearInChase.setEndIndex(location + 1);
                        gearInChase.setLine(index);
                        gears.add(gearInChase);
                    }
                }
                location++;
            }
            index++;
        }
        return gears;
    }

    private static boolean isGear(char charAt) {
        if (String.valueOf(charAt).equals("*")) {
            return true;
        }
        return false;
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
                if (location == line.length() - 1) {
                    if (!sb.isEmpty()) {
                        numberInChase.setEndIndex(location + 1);
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

class GearsInChase {

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
