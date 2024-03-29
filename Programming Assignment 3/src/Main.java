
import java.nio.file.Paths;
import Scheduler.Scheduler;

public class Main {
    public static void main(String[] args) {
        String inputPath = "Programming Assignment 3/data/input.txt";
        String outputPath = "Programming Assignment 3/data/output.txt";
        String inputAbsolutePath = Paths.get(inputPath).toAbsolutePath().toString();
        String outputAbsolutePath = Paths.get(outputPath).toAbsolutePath().toString();
        Scheduler scheduler = new Scheduler(inputAbsolutePath,outputAbsolutePath);
        scheduler.start();
    }
}
