import java.io.File;
import java.util.Random;
import becker.robots.*;

public class Utils {
    public static String RandomFileFromList(String folderPath) {
        final File dir = new File(folderPath);
        File[] files = dir.listFiles();
        return files[new Random().nextInt(files.length)].getAbsolutePath();
    }

    public final static Direction[] directions = new Direction[]{ Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
}
