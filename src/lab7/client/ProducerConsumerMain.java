package lab7.client;

import lab7.active_object.Buffer;
import lab7.active_object.BufferProxy;
import lab7.active_object.scheduler.BufferScheduler;
import lab7.client.worker.Consumer;
import lab7.client.worker.Producer;
import lab7.client.worker.Worker;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerMain {

    public static void main(String[] args) {

        int consumersNo = 10;
        int producersNo = 10;

        int size = 100;
        int timeQuantum = 1;
        int totalWorkTime = 1000;

        Buffer buffer = new Buffer(size);
        ArrayList<Worker<?>> workers = new ArrayList<>();


        BufferScheduler scheduler = new BufferScheduler();
        BufferProxy bufferProxy = new BufferProxy(buffer, scheduler);


        for(int i = 0; i < producersNo; i++) {
            workers.add(new Producer(i, new TimeManager(timeQuantum, totalWorkTime), bufferProxy));
        }

        for(int i = producersNo; i < producersNo + consumersNo; i++){
            workers.add(new Consumer(i, new TimeManager(timeQuantum, totalWorkTime), bufferProxy));
        }

//        ConcurrentHashMap<Integer, Long> completedJobsCount = new ConcurrentHashMap<>(producersNo + consumersNo);
//        for(int i = 0; i < producersNo + consumersNo; i++) completedJobsCount.put(i, 0L);
//        workers.forEach(w -> w.setCompletedJobsCount(completedJobsCount));

        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(scheduler);

        workers.forEach(executor::execute);

        executor.shutdown();


    }

}
