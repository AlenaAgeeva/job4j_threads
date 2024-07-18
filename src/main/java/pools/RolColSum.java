package pools;

import java.util.concurrent.CompletableFuture;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums sum = new Sums();
            for (int j = 0; j < matrix[i].length; j++) {
                sum.setColSum(sum.getColSum() + matrix[j][i]);
                sum.setRowSum(sum.getRowSum() + matrix[i][j]);
            }
            sums[i] = sum;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        CompletableFuture<Void>[] futures = new CompletableFuture[matrix.length * 2];
        for (int i = 0; i < matrix.length; i++) {
            final int index = i;
            futures[i] = CompletableFuture.runAsync(() -> {
                sums[index] = new Sums();
                for (int j = 0; j < matrix[index].length; j++) {
                    sums[index].setRowSum(sums[index].getRowSum() + matrix[index][j]);
                }
            });
            futures[matrix.length + i] = CompletableFuture.runAsync(() -> {
                for (int[] ints : matrix) {
                    sums[index].setColSum(sums[index].getColSum() + ints[index]);
                }
            });
        }
        CompletableFuture.allOf(futures).join();
        return sums;
    }
}
