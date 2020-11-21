package lab4;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

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
        int val = rand.nextInt();
        buffer.produce(val);
        System.out.println("Producer idx " + id + " produced ");
    }

    private void produceRandom() throws InterruptedException {
        int size = (rand.nextInt() % buffer.getLimit() + buffer.getLimit()) % buffer.getLimit() + 1;

        ArrayList<Integer> vals = new ArrayList<>();

        IntStream.range(0, size).forEach( i ->
                vals.add(rand.nextInt() % buffer.getLimit())
        );

        buffer.produce(vals);

        System.out.println("Producer idx " + id + " produced " + vals);
    }

    @Override
    public void run() {
        for(;;){
            try { this.produceRandom(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
