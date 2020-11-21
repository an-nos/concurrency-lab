package lab4;

import java.util.List;
import java.util.Random;

public class Consumer implements Runnable{

    private int id;
    private Buffer buffer;
    Random rand;

    public Consumer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id = id;
        this.rand = new Random();
    }

    private void consume() throws InterruptedException {
        int takenBuffer;
        takenBuffer = buffer.consume();
        System.out.println("Consumer idx " + id + " consumed value " + takenBuffer);
//        Thread.sleep(1000);
    }

    private void consumeRandom() throws InterruptedException {
        int randomSize = (rand.nextInt() % buffer.getLimit() + buffer.getLimit()) % buffer.getLimit() + 1;
        System.out.println("Consumer idx " + id + " wants to consume " + randomSize);
        List<Integer> takenBuffer = buffer.consume(randomSize);
        System.out.println("Consumer idx " + id + " consumed value " + takenBuffer);
    }

    @Override
    public void run() {
        for(;;){
            try { this.consumeRandom(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

    }
}
