package lab7.synchronous.worker;

import lab7.synchronous.buffer.Buffer;

import java.util.ArrayList;

public class Producer extends WorkerRunnable {


    public Producer(Buffer buffer, int id, long sideJob, long maxTime){
        super(buffer, id, sideJob, maxTime);
    }

    protected void work() throws InterruptedException {
        int size = rand.nextInt(limit) + 1;

        ArrayList<Integer> vals = new ArrayList<>();

        for(int i = 0; i < size; i++) vals.add(rand.nextInt() % 100);

        buffer.produce(vals, id);
        completedWork+=size;

    }


}
