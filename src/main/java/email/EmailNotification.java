package email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public void emailTo(User user) {
        threadPool.submit(() ->
                send(String.format("Notification %s to email %s", user.username(), user.email()),
                        String.format("Add a new event to %s", user.username()),
                        user.email()));
    }

    public void close() {
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void send(String subject, String body, String email) {
        System.out.println(subject);
        System.out.println(body);
    }
}
