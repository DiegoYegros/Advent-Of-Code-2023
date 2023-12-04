package Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<String> getLinesFromFilePath(String pathFile) throws IOException {
        var file = new File(pathFile);
        var list = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }
    public static Integer getNumberFromString(String stringToInt) {
        int parsed = 0;
        try {
            parsed = Integer.parseInt(stringToInt);
        } catch (Exception e) {
            return null;
        }
        return parsed;
    }
}
