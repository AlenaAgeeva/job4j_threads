package parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T target;
    private final int from;
    private final int to;
    private static final int THRESHOLD = 10;

    public ParallelSearch(T[] array, T target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= THRESHOLD) {
            return check();
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, target, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, target, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    private <T> int check() {
        for (int i = from; i <= to; i++) {
            if (array[i] != null && array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int search(T[] array, T target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, target, 0, array.length - 1));
    }
}
