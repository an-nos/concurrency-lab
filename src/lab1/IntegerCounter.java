package lab1;

import java.util.Random;

public class IntegerCounter {

    private int number;
    private final Random random;

    public IntegerCounter(int number){
        this.number = number;
        this.random = new Random();
    }

    public void activeIncrement(){
        this.number+=1;
    }

    public void activeDecrement(){
        this.number-=1;
    }

    public void increment() throws InterruptedException {
        this.number += 1;
        Thread.sleep(100+random.nextInt(100));
    }

    public void decrement() throws InterruptedException {
        this.number -= 1;
        Thread.sleep(100+random.nextInt(100));
    }

    public int getNumber(){
        return this.number;
    }

    public String toString(){
        return String.valueOf(this.number);
    }

}
