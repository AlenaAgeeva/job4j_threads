package concurrent;

public class Wget {
    public static void main(String[] args) {
        try {
            for (int i = 0; i <= 100; i++) {
                System.out.print("\rLoading : " + i + "%");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
