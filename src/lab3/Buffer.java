package lab3;

import java.util.ArrayList;

public class Buffer {

    private ArrayList<Integer> values;
    private int limit;

    public Buffer(){
        this.values = new ArrayList<>();
        this.limit = 10;
    }

    public synchronized void produce(int newVal) throws InterruptedException {
        while(values.size() == limit) wait();
        values.add(newVal);
        notify();
    }

    public synchronized int consume() throws InterruptedException {
        while (values.isEmpty()){
            wait();
            System.out.println("Consumer has to wait");
        }
        int takenVal = values.remove(0);
        notify();
        return takenVal;
    }



}
