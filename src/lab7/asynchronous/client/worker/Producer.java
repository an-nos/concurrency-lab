package lab7.asynchronous.client.worker;

import lab7.asynchronous.active_object.BufferProxy;
import lab7.asynchronous.client.TimeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Producer extends WorkerRunnable<Void> implements Runnable{

    public Producer(int id, TimeManager timeManager, BufferProxy proxy){
        super(id, timeManager, proxy);
    }

    @Override
    protected void work() throws InterruptedException, ExecutionException {

        List<Integer> elementsToInsert = new ArrayList<>();

        int nextWorkSize = getNextWorkSize();

        for(int i = 0; i < nextWorkSize; i++){
            elementsToInsert.add(rand.nextInt() % 100);
        }

        future = proxy.produce(elementsToInsert);

        doSideJob();

        completedWork += nextWorkSize;

//        System.out.println("Producer " + id + " produced " + elementsToInsert.size());

    }


}
