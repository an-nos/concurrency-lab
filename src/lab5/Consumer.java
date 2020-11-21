package lab5;

import lab5.bool.BoolBuffer;

import java.util.List;
import java.util.Random;

public class Consumer implements Runnable{

    private int id;
    private Buffer buffer;
    private Random rand;
    private int limit;

    public Consumer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id = id;
        this.rand = new Random();
        this.limit = buffer.getLimit() / 2;
    }

    private void consumeRandom() throws InterruptedException {
        int size = rand.nextInt(limit) + 1;
        List<Integer> takenBuffer = buffer.consume(size, id);
//        System.out.println("Consumer " + id + " consumed " + takenBuffer);
    }

    @Override
    public void run() {
        for(;;){
            try { this.consumeRandom(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

    }
}
