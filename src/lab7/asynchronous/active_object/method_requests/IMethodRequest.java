package lab7.asynchronous.active_object.method_requests;

import java.util.concurrent.CompletableFuture;

public interface IMethodRequest<T> {

    boolean guard();

    CompletableFuture<T> execute() throws InterruptedException;

}
