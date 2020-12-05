package lab7.asynchronous.active_object.method_requests;


import lab7.asynchronous.active_object.Buffer;

import java.util.List;
import java.util.Objects;
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
    public CompletableFuture<List<Integer>> execute() throws InterruptedException {
        future.complete(buffer.consume(numberToTake));
        return future;
    }

    @Override
    public String toString() {
        return "Consume Method Request: " + this.numberToTake;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumeMethodRequest that = (ConsumeMethodRequest) o;
        return numberToTake == that.numberToTake && Objects.equals(buffer, that.buffer) && Objects.equals(future, that.future);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberToTake, buffer, future);
    }
}
