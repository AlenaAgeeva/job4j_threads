package concurrent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void testIncrementThreadSafe100Threads() {
        CASCount casCount = new CASCount();
        List<Thread> list = new ArrayList<>();
        while (list.size() < 100) {
            list.add(new Thread(casCount::increment));
        }
        list.forEach(thread -> {
            try {
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertThat(100).isEqualTo(casCount.get());
    }

    @Test
    public void testIncrementThreadSafe1Thread() {
        CASCount casCount = new CASCount();
        int initialValue = casCount.get();
        assertThat(0).isEqualTo(initialValue);
        casCount.increment();
        int valueAfterIncrement = casCount.get();
        assertThat(1).isEqualTo(valueAfterIncrement);
    }

    @Test
    public void testIncrementThreadSafe10ThreadsWithTimeSleep() {
        CASCount casCount = new CASCount();
        List<Thread> list = new ArrayList<>();
        while (list.size() < 10) {
            list.add(new Thread(casCount::increment));
        }
        list.forEach(thread -> {
            try {
                thread.start();
                Thread.sleep((long) (Math.random() * 1000));
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertThat(10).isEqualTo(casCount.get());
    }
}