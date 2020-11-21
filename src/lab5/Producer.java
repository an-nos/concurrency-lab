package lab5;

import lab5.bool.BoolBuffer;

import java.util.ArrayList;
import java.util.Random;

public class Producer implements Runnable{

    private Buffer buffer;
    private int id;
    private Random rand;
    private int limit;

    public Producer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id = id;
        this.rand = new Random();
        this.limit = buffer.getLimit() / 2;
    }

    private void produceRandom() throws InterruptedException {
        int size = rand.nextInt(limit) + 1;

        ArrayList<Integer> vals = new ArrayList<>();

        for(int i = 0; i < size; i++) vals.add(rand.nextInt() % 100);

        buffer.produce(vals, id);

    }

    @Override
    public void run() {
        for(;;){
            try { this.produceRandom(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
