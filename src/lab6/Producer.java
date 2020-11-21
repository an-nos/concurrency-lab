package lab6;

import java.util.Random;

public class Producer implements Runnable{

    private Buffer buffer;
    private int id;
    private Random rand;
    private Monitor monitor;

    public Producer(Buffer buffer, Monitor monitor, int id){
        this.buffer = buffer;
        this.id = id;
        this.rand = new Random();
        this.monitor = monitor;
    }

    private void produce() throws InterruptedException {
        int val = rand.nextInt();
        int idx = monitor.beginProducing();
        System.out.println("Producer gained index " + idx);
        buffer.produce(idx, val);
        monitor.endProducing(idx);
        System.out.println("Producer idx " + id + " produced ");
    }

    @Override
    public void run() {
        for(;;){
            try { this.produce(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
