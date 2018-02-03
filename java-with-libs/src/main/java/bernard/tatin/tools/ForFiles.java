package bernard.tatin.tools;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class ForFiles {
    public static String loadTextFile(Path path) {
        try {
            Byte tableau[] = ArrayUtils.toObject(Files.readAllBytes(path));
			// io.vavr.Array<Byte> tableau = io.vavr.Array<Byte>.(Files.readAllBytes(path));
            tableau = Arrays.stream(tableau)
					.map(b -> Match(b.intValue()).of(
							Case($(is(0)), 32),
							Case($(), b.intValue())
					).byteValue())
					.toArray(Byte[]::new);
            return new String(ArrayUtils.toPrimitive(tableau), StandardCharsets.UTF_8);
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
