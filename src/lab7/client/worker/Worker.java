package lab7.client.worker;

import lab7.active_object.BufferProxy;
import lab7.client.TimeManager;

import java.lang.invoke.ConstantCallSite;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Worker<T> implements Runnable{

    protected int id;
    protected final Random rand;
    protected CompletableFuture<T> future;
    protected BufferProxy proxy;
    private final TimeManager timeManager;
    protected int maxWorkSize;
    private ConcurrentHashMap<Integer, Long> completedJobsCount;

    public Worker(int id, TimeManager timeManager, BufferProxy proxy){
        this.id = id;
        this.timeManager = timeManager;
        this.proxy = proxy;
        this.rand = new Random();
        this.maxWorkSize = proxy.getBufferSize()/2;
    }

    public void setCompletedJobsCount(ConcurrentHashMap<Integer, Long> completedJobsCount){
        this.completedJobsCount = completedJobsCount;
    }


    protected void doSideJob() throws InterruptedException {
        while(!future.isDone()){
            Thread.sleep(timeManager.getTimeQuantum());
            timeManager.reduceTime();
        }
        System.out.println("Worker " + id + " received future. Time left: " + timeManager.getTimeLeft());

        if(timeManager.getTimeLeft() > 0)
            Thread.sleep(timeManager.getTimeLeft());

        if(completedJobsCount != null) {
            completedJobsCount.replace(id, completedJobsCount.get(id)
                    + timeManager.getMaxTime()                  //worker did this for sure after sleeping below while
                    + Math.abs(timeManager.getTimeLeft()));     //worker did -getTimeLeft() extra work
        }

        timeManager.restartTime();

    }

    protected int getNextWorkSize(){
        return rand.nextInt(maxWorkSize) + 1;
    }

    protected abstract void work() throws InterruptedException;

    @Override
    public void run() {
        for(;;){
            try { work(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

    }


}
