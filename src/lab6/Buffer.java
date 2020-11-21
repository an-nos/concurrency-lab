package lab6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private int limit;

//    private HashMap<Integer, Integer> hashMap;
    private ConcurrentHashMap<Integer, Integer> hashMap;

    public Buffer(int limit){
        this.limit = limit;
//        this.hashMap = new HashMap<>();
        this.hashMap = new ConcurrentHashMap<>();
    }

    public void produce(int idx, int newVal) throws InterruptedException {
        Thread.sleep(100);
        hashMap.put(idx, newVal);
    }

    public int consume(int idx) throws InterruptedException {
        Thread.sleep(100);
        return hashMap.remove(idx);
    }



//    public void produce(int newVal) throws InterruptedException {
//        lock.lock();
//        try {
//            while (values.size() == limit) isFull.await();
//            values.add(newVal);
//            isEmpty.signal();
//        }
//        finally{ lock.unlock(); }
//    }
//
//    public int consume() throws InterruptedException {
//        int takenVal = 0;
//        lock.lock();
//        try {
//            while (values.isEmpty()) {
//                isEmpty.await();
//                System.out.println("Consumer has to wait");
//            }
//            takenVal = values.remove(0);
//            isFull.signal();
//        }
//        finally { lock.unlock(); }
//        return takenVal;
//    }


    public int getLimit(){ return this.limit; }

}
