package lab2;

public class MySemaphore {

    private volatile int value;

    public MySemaphore(int value){
        this.value = value;
    }

    public synchronized void increment(){
        this.value++;
        notify();
    }

    public synchronized void decrement() throws InterruptedException {
        while(this.value <= 0) wait();
        this.value--;
    }
}
