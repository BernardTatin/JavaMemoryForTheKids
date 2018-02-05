package bernard.tatin.tools;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;
import io.vavr.control.Option;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class ForFiles {
    public static String loadTextFile(Path path) {
        try {
            Byte tableau[] = ArrayUtils.toObject(Files.readAllBytes(path));
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
        Option<String> oFileContent = Option.of(ForFiles.loadTextFile(path));

        return oFileContent.getOrElse("").split(separators);
    }

    private ForFiles() {
    }
}
