package lab5.hasWaiters;

import lab5.Consumer;
import lab5.Producer;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerMain {

    public static void main(String[] args) {
        int consumersNo = 1;
        int producersNo = 3;

        WaitersBuffer buffer = new WaitersBuffer(10);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for(int i = 0; i < producersNo; i++) producers.add(new Producer(buffer, i));
        for(int i = 0; i < consumersNo; i++) consumers.add(new Consumer(buffer, i));

        ExecutorService executor = Executors.newCachedThreadPool();

        consumers.forEach(executor::execute);
        producers.forEach(executor::execute);

        executor.shutdown();

    }


//    ZADANIE: deadline - zajęcia 7
//    2 locki, 4 condition
//    najpierw rozwiązanie bezpośrednio wzięte z książki (z hasWaiters) - mogło powstać zakleszczenie
//    wychwycić zakleszczenie, co jest tego powodem? screeny
//    potem rozwiązanie ze zmiennymi boolean zamiast hasWaiters
//    pokazać, że się nie zakleszcza
//    będzie pytanie z zadania (prawdopodobnie 2 ćwiczenia zajmie pytanie)

}
