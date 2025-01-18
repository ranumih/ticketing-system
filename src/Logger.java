import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private static BufferedWriter writer;

    //Sets up a BufferedWriter to write to the passed logfile
    public static void initialize(String logFileName) {
        try {
            writer = new BufferedWriter(new FileWriter(logFileName, false));
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static void log(String message) {
        System.out.println(message);
        try {
            if (writer != null) {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } else {
                System.err.println("Logger is not initialized.");
            }
        } catch (IOException e) {
            System.err.println("Failed to log message: " + e.getMessage());
        }
    }


    public static void close() {
        try {
                writer.close();
                System.out.println("Logger File has Stopped ");
        } catch (IOException e) {
            System.err.println("Failed to close logger: " + e.getMessage());
        }
    }
}