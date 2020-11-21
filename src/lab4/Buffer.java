package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private ArrayList<Integer> values;
    private int limit;
    private Lock lock;
    private Condition isEmpty;
    private Condition isFull;

    public Buffer(int limit){
        this.values = new ArrayList<>();
        this.limit = limit;
        this.lock = new ReentrantLock();
        this.isEmpty = lock.newCondition();
        this.isFull = lock.newCondition();
    }

    public void produce(int newVal) throws InterruptedException {
        lock.lock();
        try {
            while (values.size() == limit) isFull.await();
            values.add(newVal);
            isEmpty.signal();
        }
        finally{ lock.unlock(); }
    }

    public void produce(List<Integer> newVals) throws InterruptedException {
        lock.lock();
        try{
            while(values.size() + newVals.size() > limit) isFull.await();
            newVals.forEach(product -> {
                values.add(product);
            });
            isEmpty.signal();

        }
        finally{ lock.unlock(); }
    }

    public int consume() throws InterruptedException {
        int takenVal = 0;
        lock.lock();
        try {
            while (values.isEmpty()) {
                isEmpty.await();
                System.out.println("Consumer has to wait");
            }
            takenVal = values.remove(0);
            isFull.signal();
        }
        finally { lock.unlock(); }
        return takenVal;
    }

    public List<Integer> consume(int numberToTake) throws InterruptedException {
        List<Integer> takenVals = new ArrayList<>();
        lock.lock();
        System.out.println("Consumer wants to take " + numberToTake);
        try {
            while (values.size() < numberToTake) {
                isEmpty.await();
                System.out.println("Consumer has to wait");
            }

            for(int i = 0; i < numberToTake; i++){
                takenVals.add(values.remove(0));
            }
            isFull.signal();
            System.out.println("Consumer consumed " + takenVals);

        }
        finally { lock.unlock(); }
        return takenVals;

    }

    public int getLimit(){ return this.limit; }

}
