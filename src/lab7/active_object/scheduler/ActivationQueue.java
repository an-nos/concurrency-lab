package lab7.active_object.scheduler;

import lab7.active_object.method_requests.ConsumeMethodRequest;
import lab7.active_object.method_requests.IMethodRequest;
import lab7.active_object.method_requests.ProduceMethodRequest;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {

    private List<IMethodRequest<?>> allMethodRequestsQueue;
    private List<ConsumeMethodRequest> consumeMethodRequests;
    private List<ProduceMethodRequest> produceMethodRequests;

    private ReentrantLock queueLock;
    private Condition isEmptyCondition;
    LinkedHashSet


    public ActivationQueue(){
        this.allMethodRequestsQueue = new ArrayList<>();
        this.consumeMethodRequests = new ArrayList<>();
        this.produceMethodRequests = new ArrayList<>();
        this.queueLock = new ReentrantLock();
        this.isEmptyCondition = queueLock.newCondition();
    }


    private List<? extends IMethodRequest<?>> getQueueOf(IMethodRequest<?> methodRequest){
        if(methodRequest instanceof ConsumeMethodRequest) return consumeMethodRequests;
        return produceMethodRequests;
    }

    private List<? extends IMethodRequest<?>> getOppositeQueueTo(IMethodRequest<?> methodRequest){
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

            methodRequest = allMethodRequestsQueue.get(0);
            List<? extends IMethodRequest<?>> specificQueue = getQueueOf(methodRequest);

            if (methodRequest.guard()) {
                allMethodRequestsQueue.remove(0);
                specificQueue.remove(0);
            } else {

                List<? extends IMethodRequest<?>> oppositeQueue = getOppositeQueueTo(methodRequest);

                while (oppositeQueue.isEmpty()) isEmptyCondition.await();

                methodRequest = oppositeQueue.get(0);

                if (methodRequest.guard()) {
                    allMethodRequestsQueue.remove(methodRequest);
                    oppositeQueue.remove(0);
                }

            }
        }
        finally {
            queueLock.unlock();
        }

        return methodRequest;

    }

}
