package lab7.synchronous.buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoolBuffer implements Buffer {

    private ArrayList<Integer> values;
    private int limit;
    private int taskTime;
    private ReentrantLock lock1;
    private Condition firstProducer;
    private Condition firstConsumer;
    private Condition restProducers;
    private Condition restConsumers;

    private boolean isFirstProducer;
    private boolean isFirstConsumer;

    public BoolBuffer(int limit, int taskTime){

        this.values = new ArrayList<>();
        this.limit = limit;
        this.taskTime = taskTime;
        this.lock1 = new ReentrantLock();

        this.firstConsumer = lock1.newCondition();
        this.restConsumers = lock1.newCondition();

        this.firstProducer = lock1.newCondition();
        this.restProducers = lock1.newCondition();

        this.isFirstConsumer = false;
        this.isFirstProducer = false;

    }

    public void produce(List<Integer> newVals, int idx) throws InterruptedException {

        lock1.lock();

        try{
//            System.out.println("P" + idx + " in lock. Wants " + newVals.size() + ". Buffer: " + values.size());


            while(isFirstProducer){
//                System.out.println("restProducers: P" + idx + " wants " + newVals.size());
                restProducers.await();
            }

            isFirstProducer = true;
            while(values.size() + newVals.size() > limit){
//                System.out.println("firstProducer: P" + idx + " wants " + newVals.size());
                firstProducer.await();
            }

            values.addAll(newVals);

//            System.out.println("P" + idx + " produced " + newVals.size());


            restProducers.signal();
            firstConsumer.signal();

            isFirstProducer = false;

            Thread.sleep(taskTime);


        }
        finally{
            lock1.unlock();
        }

    }

    public List<Integer> consume(int numberToTake, int idx) throws InterruptedException {
        List<Integer> takenVals = new ArrayList<>();
        lock1.lock();
        try {


            while(isFirstConsumer){
//                System.out.println("restConsumers: C" + idx + " wants " + numberToTake);
                restConsumers.await();
            }

            isFirstConsumer = true;

            while(values.size() < numberToTake){
//                System.out.println("firstConsumer: C"+ idx + " wants " + numberToTake);
                firstConsumer.await();
            }

            for(int i = 0; i < numberToTake; i++) takenVals.add(values.remove(0));

//            System.out.println("C"+ idx + " consumed " + takenVals.size());

            restConsumers.signal();
            firstProducer.signal();

            isFirstConsumer = false;
            Thread.sleep(taskTime);


            return takenVals;

        }
        finally { lock1.unlock(); }

    }

    public int getLimit(){ return this.limit; }

    @Override
    public String toString() {
        return values.toString();
    }


}
