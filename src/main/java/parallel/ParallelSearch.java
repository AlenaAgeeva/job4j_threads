package parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T target;
    private final int from;
    private final int to;

    public ParallelSearch(T[] array, T target, int from, int to) {
        this.array = array;
        this.target = target;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(target)) {
                    return i;
                }
            }
            return -1;
        } else {
            int mid = (from + to) / 2;
            ParallelSearch<T> leftSearch = new ParallelSearch<>(array, target, from, mid);
            ParallelSearch<T> rightSearch = new ParallelSearch<>(array, target, mid + 1, to);
            leftSearch.fork();
            rightSearch.fork();
            int leftResult = leftSearch.join();
            int rightResult = rightSearch.join();
            if (leftResult != -1) {
                return leftResult;
            } else {
                return rightResult;
            }
        }
    }

    public static <T> int search(T[] array, T target) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, target, 0, array.length - 1));
    }
}
