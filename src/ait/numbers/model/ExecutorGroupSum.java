package ait.numbers.model;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorGroupSum extends GroupSum{
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<Integer>> taskResults = new ArrayList<>();

        for (int[] group : numberGroups) {
            taskResults.add(executorService.submit(() -> {
                int sum = 0;
                for (int num : group) {
                    sum += num;
                }
                return sum;
            }));
        }

        int totalSum = 0;
        for (Future<Integer> future : taskResults) {
                try {
                    totalSum += future.get();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }

        executorService.shutdown();
        return totalSum;
    }
}
