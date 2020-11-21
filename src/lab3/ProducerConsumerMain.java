package lab3;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerMain {

    public static void main(String[] args) {
        int consumersNo = 1;
        int producersNo = 1;

        Buffer buffer = new Buffer();
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i < producersNo; i++) producers.add(new Producer(buffer, i));
        for(int i = 0; i < consumersNo; i++) consumers.add(new Consumer(buffer, producersNo+i));

        ExecutorService executor = Executors.newFixedThreadPool(2);


        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        executor.shutdown();

    }
}
//1 producent, 1 konsument, 1 element