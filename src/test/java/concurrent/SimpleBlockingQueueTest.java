package concurrent;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void testOfferAndPollOperations() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(5);
        Thread producerThread = new Thread(() -> {
            try {
                blockingQueue.offer(1);
                blockingQueue.offer(2);
                blockingQueue.offer(3);
                blockingQueue.offer(4);
                blockingQueue.offer(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumerThread = new Thread(() -> {
            try {
                int value1 = blockingQueue.poll();
                assertThat(1).isEqualTo(value1);
                int value2 = blockingQueue.poll();
                assertThat(2).isEqualTo(value2);
                int value3 = blockingQueue.poll();
                assertThat(3).isEqualTo(value3);
                int value4 = blockingQueue.poll();
                assertThat(4).isEqualTo(value4);
                int value5 = blockingQueue.poll();
                assertThat(5).isEqualTo(value5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();
    }
    @Test
    void testQueueOperations() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        AtomicInteger producerSum = new AtomicInteger(0);
        AtomicInteger consumerSum = new AtomicInteger(0);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.offer(i);
                    producerSum.addAndGet(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    int value = queue.poll();
                    consumerSum.addAndGet(value);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(producerSum.get()).isEqualTo(55);
        assertThat(consumerSum.get()).isEqualTo(55);
    }
}