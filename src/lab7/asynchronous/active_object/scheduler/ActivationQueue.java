package lab7.asynchronous.active_object.scheduler;

import lab7.asynchronous.active_object.method_requests.ConsumeMethodRequest;
import lab7.asynchronous.active_object.method_requests.IMethodRequest;
import lab7.asynchronous.active_object.method_requests.ProduceMethodRequest;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {

    private Set<IMethodRequest<?>> allMethodRequestsQueue;
    private Set<ConsumeMethodRequest> consumeMethodRequests;
    private Set<ProduceMethodRequest> produceMethodRequests;

    private ReentrantLock queueLock;
    private Condition isEmptyCondition;



    public ActivationQueue(){
        this.allMethodRequestsQueue = new LinkedHashSet<>();
        this.consumeMethodRequests = new LinkedHashSet<>();
        this.produceMethodRequests = new LinkedHashSet<>();
        this.queueLock = new ReentrantLock();
        this.isEmptyCondition = queueLock.newCondition();
    }


    private Set<? extends IMethodRequest<?>> getQueueOf(IMethodRequest<?> methodRequest){
        if(methodRequest instanceof ConsumeMethodRequest) return consumeMethodRequests;
        return produceMethodRequests;
    }

    private Set<? extends IMethodRequest<?>> getOppositeQueueTo(IMethodRequest<?> methodRequest){
        if(methodRequest instanceof ProduceMethodRequest) return consumeMethodRequests;
        return produceMethodRequests;
    }

    public void enqueue(IMethodRequest<?> methodRequest){

        queueLock.lock();

        try {
            if (methodRequest instanceof ConsumeMethodRequest)
                consumeMethodRequests.add((ConsumeMethodRequest) methodRequest);
            else
                produceMethodRequests.add((ProduceMethodRequest) methodRequest);

            allMethodRequestsQueue.add(methodRequest);
            isEmptyCondition.signal();
        }
        finally {
            queueLock.unlock();
        }
    }


    public IMethodRequest<?> dequeue() throws InterruptedException {

        IMethodRequest<?> methodRequest;

        queueLock.lock();
        try {
            while (allMethodRequestsQueue.isEmpty()) isEmptyCondition.await();

            methodRequest = allMethodRequestsQueue.iterator().next();
            Set<? extends IMethodRequest<?>> specificQueue = getQueueOf(methodRequest);

            if (methodRequest.guard()) {
                specificQueue.remove(methodRequest);
            } else {

                specificQueue = getOppositeQueueTo(methodRequest);

                while (specificQueue.isEmpty()) isEmptyCondition.await();

                methodRequest = specificQueue.iterator().next();

                if (methodRequest.guard())
                    specificQueue.remove(methodRequest);


            }
            allMethodRequestsQueue.remove(methodRequest);
        }
        finally {
            queueLock.unlock();
        }

        return methodRequest;

    }

}
