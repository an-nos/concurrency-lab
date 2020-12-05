package lab7.asynchronous.active_object.method_requests;

import lab7.asynchronous.active_object.Buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ProduceMethodRequest implements IMethodRequest<Void> {

    private List<Integer> elementsToInsert;
    private Buffer buffer;
    private CompletableFuture<Void> future;

    public ProduceMethodRequest(Buffer buffer, CompletableFuture<Void> future, List<Integer> elementsToInsert){
        this.future = future;
        this.buffer = buffer;

        this.elementsToInsert = new ArrayList<>();
        this.elementsToInsert.addAll(elementsToInsert);
    }


    @Override
    public boolean guard() {
        return elementsToInsert.size() <= buffer.getEmptyCount();
    }

    @Override
    public CompletableFuture<Void> execute() throws InterruptedException {
        buffer.produce(elementsToInsert);
        future.complete(null);
        return future;
    }


    @Override
    public String toString() {
        return "Produce Method Request: " + this.elementsToInsert.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduceMethodRequest that = (ProduceMethodRequest) o;
        return Objects.equals(elementsToInsert, that.elementsToInsert) && Objects.equals(buffer, that.buffer) && Objects.equals(future, that.future);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementsToInsert, buffer, future);
    }
}
