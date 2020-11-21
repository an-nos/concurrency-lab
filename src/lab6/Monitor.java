package lab6;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {

    private Lock lock;
    private Condition producersCond;
    private Condition consumersCond;

    private List<Integer> empty;
    private List<Integer> full;

    private int size;

    public Monitor(int size){
        this.lock = new ReentrantLock();
        this.producersCond = lock.newCondition();
        this.consumersCond = lock.newCondition();
        this.empty = new LinkedList<>();
        this.full = new LinkedList<>();
        this.size = size;

        for(int i = 0; i < size; i++) empty.add(i);
    }

    private int getThreadsNoInBuffer(){
        return size - empty.size() - full.size() + 1;
    }

    public int beginProducing(){
        lock.lock();
        try {
            while (empty.isEmpty()) producersCond.await();
            System.out.println("Began producing, no of threads in buffer: " + getThreadsNoInBuffer() );
            return empty.remove(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return -1;

    }

    public void endProducing(int idx){
        lock.lock();
        try{
            full.add(idx);
            consumersCond.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public int beginConsuming(){
        lock.lock();
        try{
            while (full.isEmpty()) consumersCond.await();
            System.out.println("Began consuming, no of threads in buffer: " + getThreadsNoInBuffer() );
            return full.remove(0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            lock.unlock();
        }
        return -1;
    }

    public void endConsuming(int idx){
        lock.lock();
        try{
            empty.add(idx);
            producersCond.signal();
        }
        finally{
            lock.unlock();
        }
    }
}
