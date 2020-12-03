package lab7.active_object;

import lab7.active_object.method_requests.ConsumeMethodRequest;
import lab7.active_object.method_requests.ProduceMethodRequest;
import lab7.active_object.scheduler.BufferScheduler;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BufferProxy {

    private BufferScheduler scheduler;
    private Buffer buffer;
    private int bufferSize;

    public BufferProxy(Buffer buffer, BufferScheduler scheduler){
        this.buffer = buffer;
        this.scheduler = scheduler;
        this.bufferSize = buffer.getSize();
    }


    public CompletableFuture<Void> produce(List<Integer> elementsToInsert){

        CompletableFuture<Void> future = new CompletableFuture<>();
        ProduceMethodRequest methodRequest = new ProduceMethodRequest(buffer, future, elementsToInsert);

        scheduler.enqueue(methodRequest);

        return future;
    }

    public CompletableFuture<List<Integer>> consume(int numberToTake){

        CompletableFuture<List<Integer>> future = new CompletableFuture<>();
        ConsumeMethodRequest methodRequest = new ConsumeMethodRequest(buffer, future, numberToTake);

        scheduler.enqueue(methodRequest);

        return future;

    }

    public int getBufferSize(){
        return bufferSize;
    }
}
