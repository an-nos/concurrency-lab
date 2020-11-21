package lab6;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerMain {

    public static void main(String[] args) {
        int consumersNo = 100;
        int producersNo = 10;

        int size = 1000;

        Buffer buffer = new Buffer(size);
        Monitor monitor = new Monitor(size);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i < producersNo; i++) producers.add(new Producer(buffer, monitor, i));
        for(int i = 0; i < consumersNo; i++) consumers.add(new Consumer(buffer, monitor, i));

        ExecutorService executor = Executors.newCachedThreadPool();

        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        executor.shutdown();

    }

//    bufor poza monitorem
//    porcja nie przepada
//    prod, cons po jednym zasobie
//    najpierw zwykła HashMap, potem ConcurrentHashMap
//    czym się różnią te rozwiązania?

}
