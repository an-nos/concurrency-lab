package lab3;

public class Consumer implements Runnable{

    private int id;
    private Buffer buffer;

    public Consumer(Buffer buffer, int id){
        this.buffer = buffer;
        this.id = id;
    }

    private void consume() throws InterruptedException {
        int takenBuffer;
        takenBuffer = buffer.consume();
        System.out.println("Consumer idx " + id + " consumed value " + takenBuffer);
//        Thread.sleep(1000);
    }

    @Override
    public void run() {
        for(;;){
            try { this.consume(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

    }
}
