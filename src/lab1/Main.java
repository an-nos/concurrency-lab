package lab1;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws InterruptedException  {

        int threadNo = 10000;
        int repeat = 10;

        ArrayList<Thread> threads = new ArrayList<>();
        IntegerCounter counter = new IntegerCounter(0);

        for(int i = 0; i < threadNo; i++) {
            int finalI = i;
            threads.add(new Thread(() ->
                    IntStream.range(0, repeat).forEach(j -> {
                        try {
                            if (finalI % 2 == 0) counter.decrement();
                            else counter.increment();
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }

                    })
            ));
            threads.get(i).start();
        }

        threads.forEach(t -> {
            try { t.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        });


        System.out.println(counter);

    }
}




// ocena - Java, dłuższe zadanie (kod + analiza wydajności), Erlang, teoria, aktywność
// max 1 nieobecność, dłuższa - kontakt, trzeba nadrobić zajęcia na których się nie było
// przeczytać rozdział dot semaforów, DEFINICJA SEMAFORA - teoretyczna i praktyczna!!!!
// block synchronized - jak używać, jak działa, implementacja