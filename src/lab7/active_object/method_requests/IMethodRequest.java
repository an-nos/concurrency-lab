package lab7.active_object.method_requests;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IMethodRequest<T> {

    boolean guard();

    CompletableFuture<T> execute();

}
