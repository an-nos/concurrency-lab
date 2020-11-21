package lab2;

import lab1.MyBinarySemaphore;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ButlerMain {

    public static void main(String[] args) {
        ArrayList<NaivePhilosopher> philosophers = new ArrayList<>();
        MyBinarySemaphore[] forks = new MyBinarySemaphore[5];

        MySemaphore butler = new MySemaphore(4);

        for(int i = 0; i < 5; i++){
            forks[i] = new MyBinarySemaphore(true);
        }

        for(int i = 0; i < 5; i ++){
            philosophers.add(new NaivePhilosopher(i, forks, butler));
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);

        philosophers.forEach(executor::execute);

        executor.shutdown();


    }
}
