package lab7.asynchronous.client.worker;

import lab7.IWorkerRunnable;
import lab7.asynchronous.active_object.BufferProxy;
import lab7.asynchronous.client.TimeManager;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class WorkerRunnable<T> implements IWorkerRunnable {

    protected int id;
    protected final Random rand;
    protected CompletableFuture<T> future;
    protected BufferProxy proxy;
    private final TimeManager timeManager;
    protected int maxWorkSize;
    protected long completedWork;
    protected long completedSideJobs;

    public WorkerRunnable(int id, TimeManager timeManager, BufferProxy proxy){
        this.id = id;
        this.timeManager = timeManager;
        this.proxy = proxy;
        this.rand = new Random();
        this.maxWorkSize = proxy.getBufferSize()/2;
        this.completedWork = 0;
        this.completedSideJobs = 0;
    }


    protected void doSideJob() throws InterruptedException, ExecutionException {
        while(!future.isDone()){
            Thread.sleep(timeManager.timeQuantum());
            timeManager.workQuantum();
            if(timeManager.timeLeft() < 0) future.get();
        }
//        System.out.println("Worker " + id + " received future. Time left: " + timeManager.timeLeft());
        long timeLeft = timeManager.timeLeft();
        if(timeLeft > 0) {
            Thread.sleep(timeLeft);
            timeManager.workFor(timeLeft);
        }

        this.completedSideJobs += timeManager.getTimeWorked();

        timeManager.restartTime();

    }

    protected int getNextWorkSize(){
        return rand.nextInt(maxWorkSize) + 1;
    }

    protected abstract void work() throws InterruptedException, ExecutionException;

    @Override
    public void run() {
        timeManager.startTime();
        while(!timeManager.isTimeExceeded()){
            try { work(); }
            catch (InterruptedException | ExecutionException e) { }  //fails if time ended and not enough buffer to finalize
        }
//        System.out.println("Worker " + this.id + " ended");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerRunnable<?> worker = (WorkerRunnable<?>) o;
        return id == worker.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public long getCompletedWork(){
        return completedWork;
    }

    @Override
    public long getCompletedSideJobs(){
        return completedSideJobs;
    }
}
