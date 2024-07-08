package concurrent;

public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        final Thread consumer = new Thread(
                () -> {
                    try {
                        Integer data;
                        while ((data = queue.poll()) != null) {
                            System.out.println(data);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}
