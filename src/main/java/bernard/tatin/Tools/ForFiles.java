package bernard.tatin.Tools;

import java.nio.file.Files;
import java.nio.file.Path;

public class ForFiles {
    private final static ForFiles ourInstance = new ForFiles();

    public static String loadTextFile(Path path) {
        try {
        byte[] aLine = Files.readAllBytes(path);
        int l = aLine.length;

        if (l > 0) {
            // '\0' is a word separator
            for (int i=0; i<l; i++) {
                if (aLine[i] == 0) {
                    aLine[i] = 32;
                }
            }
            return new String(aLine, "UTF-8");
        } else {
            return null;
        }
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String[] loadLinesFromfiles(Path path) {
        String fileContent = ForFiles.loadTextFile(path);
        if (fileContent != null) {
            return fileContent.split("\n");
        } else {
            return null;
        }
    }
    private ForFiles() {
    }
}
