package concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) {
        try {
            Thread progress = new Thread(new ConsoleProgress());
            progress.start();
            Thread.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int index = 0;
        var process = new char[]{'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                index = index == process.length ? 0 : index;
                System.out.print("\r load: " + process[index++]);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
