package lab2;

import lab1.MyBinarySemaphore;

public class NaivePhilosopher implements Runnable{

    private int idx;
    private MyBinarySemaphore[] forks;
    private MySemaphore butler;

    public NaivePhilosopher(int idx, MyBinarySemaphore[] forks, MySemaphore butler){
        this.idx = idx;
        this.forks = forks;
        this.butler = butler;
    }

    public void eat()  {
        System.out.println("Philosopher no " + this.idx + " is eating.");
    }

    public void think()  {
        System.out.println("Philosopher no " + this.idx + " is thinking.");
    }


    @Override
    public void run() {

        for(;;){
            try{
                think();
                butler.decrement();

                forks[idx].decrement();
                System.out.println(idx + " taken left");

                forks[(idx+1)%5].decrement();
                System.out.println(idx + " taken right");

                eat();

                forks[idx].increment();
                System.out.println(idx + " put left");

                forks[(idx+1)%5].increment();
                System.out.println(idx + " put right");

                butler.increment();


            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
