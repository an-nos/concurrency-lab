package lab1;

public class MyBinarySemaphore {

    private volatile boolean value;

    public MyBinarySemaphore(boolean value){ this.value = value; }

    public synchronized void increment(){
        this.value = true;
        notify();
    }

    public synchronized void decrement() throws InterruptedException {
        while(!this.value) wait();
        this.value = false;
    }

}

// java sama może wybudzać wątki - nie wiadomo, czy wątek obudził się w wyniku notify, dlatego musimy sprawdzać whilem
// zawsze while przy wait
// java nie zapewnia kolejki wątków, ale pseudo zbiór (zapewniający nie zagłodzenie wątków)

