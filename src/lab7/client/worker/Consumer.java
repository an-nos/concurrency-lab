package lab7.client.worker;

import lab7.active_object.BufferProxy;
import lab7.client.TimeManager;

import java.util.List;

public class Consumer extends Worker<List<Integer>> implements Runnable{

    public Consumer(int id, TimeManager timeManager, BufferProxy proxy){
        super(id, timeManager, proxy);
    }

    @Override
    protected void work() throws InterruptedException {

        int numberToConsume = getNextWorkSize();

        future = proxy.consume(numberToConsume);

        doSideJob();

        System.out.println("Consumer " + id + " consumed " + numberToConsume);

    }


}
