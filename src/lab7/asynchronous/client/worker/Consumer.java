package lab7.asynchronous.client.worker;

import lab7.asynchronous.active_object.BufferProxy;
import lab7.asynchronous.client.TimeManager;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Consumer extends WorkerRunnable<List<Integer>> implements Runnable{

    public Consumer(int id, TimeManager timeManager, BufferProxy proxy){
        super(id, timeManager, proxy);
    }

    @Override
    protected void work() throws InterruptedException, ExecutionException {

        int numberToConsume = getNextWorkSize();

        future = proxy.consume(numberToConsume);

        doSideJob();

        completedWork += numberToConsume;

//        System.out.println("Consumer " + id + " consumed " + numberToConsume);

    }


}
