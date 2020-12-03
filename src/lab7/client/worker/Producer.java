package lab7.client.worker;

import lab7.active_object.BufferProxy;
import lab7.client.TimeManager;

import java.util.ArrayList;
import java.util.List;

public class Producer extends Worker<Void> implements Runnable{

    public Producer(int id, TimeManager timeManager, BufferProxy proxy){
        super(id, timeManager, proxy);
    }

    @Override
    protected void work() throws InterruptedException {

        List<Integer> elementsToInsert = new ArrayList<>();

        for(int i = 0; i < getNextWorkSize(); i++){
            elementsToInsert.add(rand.nextInt() % 100);
        }

        future = proxy.produce(elementsToInsert);

        doSideJob();

        System.out.println("Producer " + id + " produced " + elementsToInsert.size());

    }


}
