package lab2;

import lab1.MyBinarySemaphore;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PhilosopherMain {


    public static void main(String[] args) throws InterruptedException {

        ArrayList<ClassicPhilosopher> philosophers = new ArrayList<>();
        MyBinarySemaphore[] forks = new MyBinarySemaphore[5];

        for(int i = 0; i < 5; i++){
            forks[i] = new MyBinarySemaphore(true);
        }

        for(int i = 0; i < 5; i ++){
            philosophers.add(new ClassicPhilosopher(i, forks));
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);

        philosophers.forEach(executor::execute);

        executor.shutdown();
//        executor.awaitTermination(100, TimeUnit.MILLISECONDS);
//        executor.shutdownNow();

    }
}

// 5 filozofów - z lokajem
// 5 filozofów - "dla leworęcznych" - jeden z filozofów sięga najpierw po prawy
