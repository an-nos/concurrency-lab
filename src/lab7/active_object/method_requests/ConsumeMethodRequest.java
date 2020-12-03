package lab7.active_object.method_requests;


import lab7.active_object.Buffer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConsumeMethodRequest implements IMethodRequest<List<Integer>> {

    private int numberToTake;
    private Buffer buffer;
    private CompletableFuture<List<Integer>> future;

    public ConsumeMethodRequest(Buffer buffer, CompletableFuture<List<Integer>> future, int numberToTake){
        this.buffer = buffer;
        this.future = future;

        this.numberToTake = numberToTake;
    }

    @Override
    public boolean guard() {
        return numberToTake <= buffer.getFullCount();
    }

    @Override
    public CompletableFuture<List<Integer>> execute() {
        future.complete(buffer.consume(numberToTake));
        return future;
    }

    @Override
    public String toString() {
        return "Consume Method Request: " + this.numberToTake;
    }
}
