package concurrent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String filePath;

    public Wget(String[] args) {
        validateArgs(args);
        this.url = args[0];
        this.speed = Integer.parseInt(args[1]);
        this.filePath = args[2];
    }

    @Override
    public void run() {
        var startTime = System.currentTimeMillis();
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(filePath)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            long downloadData = 0;
            System.out.println("Open connection: " + (System.currentTimeMillis() - startTime) + " ms");
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                System.out.println("Read 1024 bytes : " + (System.nanoTime() - downloadAt) + " nano.");
                if (downloadData >= speed) {
                    long currentTIme = System.currentTimeMillis();
                    long pauseTime = currentTIme - startTime;
                    if (pauseTime > speed) {
                        System.out.println("Pause here");
                        Thread.sleep(pauseTime);
                    }
                    startTime = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
            System.out.println(Files.size(Path.of(filePath)) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void validateArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Wrong arguments length.");
        } else if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Wrong speed number.");
        } else if (args[0].isEmpty() || args[0].isBlank()) {
            throw new IllegalArgumentException("Url is empty.");
        } else if (!args[0].startsWith("https:")) {
            throw new IllegalArgumentException("Wrong url.");
        } else if (args[0] == null) {
            throw new IllegalArgumentException("Cannot find url.");
        } else if (args[2] == null) {
            throw new IllegalArgumentException("Cannot find filepath.");
        } else if (args[2].isEmpty() || args[2].isBlank()) {
            throw new IllegalArgumentException("Filepath is empty.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread wget = new Thread(new Wget(args));
        wget.start();
        wget.join();
    }
}
