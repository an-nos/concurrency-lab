package lab7.active_object.scheduler;

import lab7.active_object.method_requests.IMethodRequest;

public class BufferScheduler implements Runnable {

    private ActivationQueue queue;

    public BufferScheduler(){
        this.queue = new ActivationQueue();
    }

    public void enqueue(IMethodRequest<?> request){
        queue.enqueue(request);
    }

    public void dequeue() throws InterruptedException {
        IMethodRequest<?> methodRequest = queue.dequeue();
        methodRequest.execute();
    }

    @Override
    public void run(){
        while(true){
            try {
                dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
