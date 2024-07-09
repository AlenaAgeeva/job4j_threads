package concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final Object monitor = this;
    private final int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void offer(T value) {
        synchronized (monitor) {
            try {
                while (maxSize <= queue.size()) {
                    monitor.wait();
                }
                queue.offer(value);
                monitor.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public T poll() throws InterruptedException {
        synchronized (monitor) {
            while (isEmpty()) {
                this.wait();
            }
            T value = queue.poll();
            monitor.notifyAll();
            return value;
        }
    }

    public boolean isEmpty() {
        synchronized (monitor) {
            return queue.isEmpty();
        }
    }
}
