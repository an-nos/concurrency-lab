package lab2;

import lab1.MyBinarySemaphore;

public class ClassicPhilosopher implements Runnable {

    protected final int idx;

    private MyBinarySemaphore[] forks;

    public ClassicPhilosopher(int idx, MyBinarySemaphore[] forks){
        this.idx = idx;
        this.forks = forks;
    }

    public void eat()  {
        System.out.println("Philosopher no " + this.idx + " is eating.");
    }

    public void think()  {
        System.out.println("Philosopher no " + this.idx + " is thinking.");
    }

    @Override
    public void run() {

        int firstFork = idx;
        int secondFork = (idx + 1) % 5;

        if(idx == 0){
            firstFork = secondFork;
            secondFork = idx;
        }


            for(;;) {
                think();

                try { forks[firstFork].decrement(); }
                catch (InterruptedException e) { e.printStackTrace(); }

                System.out.println(idx + " taken left");
                try { forks[secondFork].decrement(); }
                catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(idx + " taken right");

                eat();

                System.out.println(idx + " put left");
                forks[firstFork].increment();
                System.out.println(idx + " put right");
                forks[secondFork].increment();

            }
    }
}
