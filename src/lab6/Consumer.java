package lab6;

import java.util.List;
import java.util.Random;

public class Consumer implements Runnable{

    private int id;
    private Buffer buffer;
    Random rand;
    private Monitor monitor;

    public Consumer(Buffer buffer, Monitor monitor, int id){
        this.buffer = buffer;
        this.id = id;
        this.rand = new Random();
        this.monitor = monitor;
    }

    private void consume() throws InterruptedException {
        int takenBuffer;
        int idx = monitor.beginConsuming();
        System.out.println("Consumer gained index " + idx);
        takenBuffer = buffer.consume(idx);
        monitor.endConsuming(idx);
        System.out.println("Consumer idx " + id + " consumed value " + takenBuffer);
//        Thread.sleep(1000);
    }

    @Override
    public void run() {
        for(;;){
            try { this.consume(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

    }
}
