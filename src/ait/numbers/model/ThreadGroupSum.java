package ait.numbers.model;

public class ThreadGroupSum extends GroupSum {
    public ThreadGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        final int[] halfSum = new int[2];

        Thread t1 = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < numberGroups.length / 2; i++) {
                for (int num : numberGroups[i]) {
                    sum += num;
                }
            }
            halfSum[0] = sum;
        }
        );

        Thread t2 = new Thread(() -> {
            int sum = 0;
            for (int i = numberGroups.length / 2; i < numberGroups.length; i++) {
                for (int num : numberGroups[i]) {
                    sum += num;
                }
            }
            halfSum[1] = sum;
        }
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return halfSum[0] + halfSum[1];
    }

}
