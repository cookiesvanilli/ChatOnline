package ru.netology.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private final BufferedWriter writer;

    public Logger(File outputFile) throws IOException {
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        this.writer = new BufferedWriter(new FileWriter(outputFile, true));
    }

    public void writeLine(String line) throws IOException {
        if (line == null) {
            throw new IllegalArgumentException("Line may not be null");
        }

        this.writer.write(line);
        this.writer.newLine();
        this.writer.flush();
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public static synchronized void log(String value, String help1, String help2) {
        Calendar dateTime = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateString = dateFormat.format(dateTime.getTime());
        try {
            Logger log = new Logger(new File("src/main/java/ru/netology/resources/File.log"));
            log.writeLine(dateString + ": " + help1 + value + help2);
            log.close();
        } catch (IOException e) {
            System.out.println("Log exception: " + e);
        }
    }

}
