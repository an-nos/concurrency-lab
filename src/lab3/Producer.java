package lab3;

import java.util.Random;

public class Producer implements Runnable{

    private Buffer buffer;
    private int id;
    private Random rand;

    public Producer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id = id;
        this.rand = new Random();
    }

    private void produce() throws InterruptedException {
        int val = this.rand.nextInt();
        buffer.produce(val);
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
