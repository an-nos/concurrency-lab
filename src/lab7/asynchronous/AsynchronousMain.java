package lab7.asynchronous;

import lab7.Runner;
import lab7.asynchronous.active_object.Buffer;
import lab7.asynchronous.active_object.BufferProxy;
import lab7.asynchronous.active_object.scheduler.BufferScheduler;
import lab7.asynchronous.client.TimeManager;
import lab7.asynchronous.client.worker.Consumer;
import lab7.asynchronous.client.worker.Producer;
import lab7.asynchronous.client.worker.WorkerRunnable;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsynchronousMain extends Runner {

    public AsynchronousMain(){
        super();
    }


    public void run() throws InterruptedException {
        //arguments: bufferSize producersNo consumersNo bufferTaskTime sideJobLength totalTime timeQuantum

        Buffer buffer = new Buffer(size, bufferTaskTime);
//        ArrayList<WorkerRunnable<?>> workers = new ArrayList<>();


        BufferScheduler scheduler = new BufferScheduler();
        BufferProxy bufferProxy = new BufferProxy(buffer, scheduler);


        for(int i = 0; i < producersNo; i++) {
            workers.add(new Producer(i, new TimeManager(timeQuantum, sideJobLength, maxTime), bufferProxy));
        }

        for(int i = producersNo; i < producersNo + consumersNo; i++){
            workers.add(new Consumer(i, new TimeManager(timeQuantum, sideJobLength, maxTime), bufferProxy));
        }


        ExecutorService workersExecutor = Executors.newFixedThreadPool(consumersNo + producersNo);

        ExecutorService schedulerExecutor = Executors.newSingleThreadExecutor();
        schedulerExecutor.execute(scheduler);

        workers.forEach(workersExecutor::execute);

        try {
            workersExecutor.shutdown();
            schedulerExecutor.shutdown();
            workersExecutor.awaitTermination(maxTime + sideJobLength * 2L, TimeUnit.MILLISECONDS);

        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            workersExecutor.shutdownNow();
            schedulerExecutor.shutdownNow();
        }


    }

}

