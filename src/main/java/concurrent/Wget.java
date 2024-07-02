package concurrent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
        validateArgs();
    }

    @Override
    public void run() {
        var startTime = System.currentTimeMillis();
        var file = new File("tmp.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
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
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void validateArgs() {
        if (speed <= 0) {
            throw new IllegalArgumentException("Wrong speed number.");
        } else if (url.isEmpty() || url.isBlank()) {
            throw new IllegalArgumentException("Url is empty.");
        } else if (!url.startsWith("https:")) {
            throw new IllegalArgumentException("Wrong url.");
        } else if (url == null) {
            throw new IllegalArgumentException("Cannot find url in thread.properties file");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
