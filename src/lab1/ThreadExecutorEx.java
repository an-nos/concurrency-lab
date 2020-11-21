package lab1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadExecutorEx {

    private static int range = 10;

    private static class CountingTaskCreator {
        public static Runnable createCountingTask(IntegerCounter counter, boolean inc, MyBinarySemaphore semaphore) {
            return () -> {
                for (int i = 0; i < range; i++) {
                    try {
                        if (inc) {
                            semaphore.decrement();
                            counter.activeIncrement();
                            semaphore.increment();
                        } else {
                            semaphore.decrement();
                            counter.activeDecrement();
                            semaphore.increment();

                        }
                    }
                    catch(InterruptedException e){ e.printStackTrace(); }
                }
            };
        }
    }

    public static void main(String[] args) {

        int threadNo = 100;

        IntegerCounter counter = new IntegerCounter(0);

        MyBinarySemaphore semaphore = new MyBinarySemaphore(true);

        ExecutorService executor = Executors.newFixedThreadPool(threadNo);

        for(int i = 0; i < threadNo; i++) {
            executor.execute(CountingTaskCreator.createCountingTask(counter, true, semaphore));
            executor.execute(CountingTaskCreator.createCountingTask(counter, false, semaphore));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println(counter.getNumber());

    }
}
