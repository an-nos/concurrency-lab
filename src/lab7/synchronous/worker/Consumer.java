package lab7.synchronous.worker;

import lab7.synchronous.buffer.Buffer;

import java.util.List;

public class Consumer extends WorkerRunnable {

    public Consumer(Buffer buffer, int id, long sideJob, long maxTime){
        super(buffer, id, sideJob, maxTime);
    }

    @Override
    protected void work() throws InterruptedException {
        int size = rand.nextInt(limit) + 1;
        List<Integer> takenBuffer = buffer.consume(size, id);
//        System.out.println("Consumer " + id + " consumed " + takenBuffer);
        completedWork+=size;
    }

}
