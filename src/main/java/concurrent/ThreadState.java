package concurrent;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        Thread second = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                && second.getState() != Thread.State.TERMINATED) {
            Thread.sleep(10);
        }
        System.out.println("Work is done.");
    }
}
