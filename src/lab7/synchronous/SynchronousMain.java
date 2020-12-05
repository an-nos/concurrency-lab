package lab7.synchronous;

import lab7.Runner;
import lab7.synchronous.buffer.BoolBuffer;
import lab7.synchronous.worker.Consumer;
import lab7.synchronous.worker.Producer;
import lab7.synchronous.worker.WorkerRunnable;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronousMain extends Runner {

    public SynchronousMain(){
        super();
    }

    public void run() {
        //arguments: bufferSize producersNo consumersNo bufferTaskTime sideJobLength totalTime timeQuantum

        BoolBuffer buffer = new BoolBuffer(size, bufferTaskTime);
//        ArrayList<WorkerRunnable> workers = new ArrayList<>();

        for(int i = 0; i < producersNo; i++) workers.add(new Producer(buffer, i, sideJobLength, maxTime));
        for(int i = producersNo; i < producersNo + consumersNo; i++) workers.add(new Consumer(buffer, i, sideJobLength, maxTime));

        ExecutorService executor = Executors.newCachedThreadPool();

        workers.forEach(executor::execute);

        try {
            executor.shutdown();
            executor.awaitTermination(maxTime + sideJobLength * 2L, TimeUnit.MILLISECONDS);

        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            executor.shutdownNow();
        }


    }




}
