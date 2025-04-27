package ait.numbers.model;

import ait.numbers.task.OneGroupSum;
public class ThreadGroupSum extends GroupSum {
    public ThreadGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int tasksNumber = numberGroups.length;
        OneGroupSum[] task = new OneGroupSum[tasksNumber];
        Thread[] thread = new Thread[tasksNumber];

        for (int i = 0; i < tasksNumber; i++) {
            task[i] = new OneGroupSum(numberGroups[i]);
            thread[i] = new Thread(task[i]);
            thread[i].start();
        }

        try {
            for (int i = 0; i < thread.length; i++) {
                thread[i].join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int sum = 0;
        for (OneGroupSum t : task) {
            sum += t.getSum();
        }

        return sum;
    }
}