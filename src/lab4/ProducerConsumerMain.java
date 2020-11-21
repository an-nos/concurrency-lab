package lab4;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerMain {

    public static void main(String[] args) {
        int consumersNo = 2;
        int producersNo = 2;

        Buffer buffer = new Buffer(10);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i < producersNo; i++) producers.add(new Producer(buffer, i));
        for(int i = 0; i < consumersNo; i++) consumers.add(new Consumer(buffer, producersNo+i));

        ExecutorService executor = Executors.newFixedThreadPool(consumersNo + producersNo);

        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        executor.shutdown();

    }
}
