package bernard.tatin.tools;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;


public class ForFiles {
    public static String loadTextFile(Path path) {
        try {
            Byte tableau[] = ArrayUtils.toObject(Files.readAllBytes(path));
            tableau = Arrays.stream(tableau).map(b -> b == 0 ? 32 : b).toArray(Byte[]::new);
            return new String(ArrayUtils.toPrimitive(tableau));
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String[] loadLinesFromFiles(Path path, String separators) {
        String fileContent = ForFiles.loadTextFile(path);
        if (fileContent != null) {
            return fileContent.split(separators);
        } else {
            return null;
        }
    }
    private ForFiles() {
    }
}
