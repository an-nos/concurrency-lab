package lab7.synchronous.worker;

import lab7.IWorkerRunnable;
import lab7.asynchronous.client.timers.WorkerTimer;
import lab7.synchronous.buffer.Buffer;

import java.util.Random;

public abstract class WorkerRunnable implements IWorkerRunnable {

    protected int id;
    protected Buffer buffer;
    protected Random rand;
    protected int limit;
    protected long sideJob;
    protected long completedWork;
    protected long completedSideJobs;
    protected WorkerTimer timer;

    public WorkerRunnable(Buffer buffer, int id, long sideJob, long maxTime){
        this.buffer = buffer;
        this.id = id;
        this.rand = new Random();
        this.limit = buffer.getLimit() / 2;
        this.sideJob = sideJob;
        this.completedSideJobs = 0;
        this.completedWork = 0;
        this.timer = new WorkerTimer(maxTime);
        timer.startTime();
    }

    protected abstract void work() throws InterruptedException;

    private void doSideJob() throws InterruptedException {
        Thread.sleep(sideJob);
        completedSideJobs+=sideJob;
    }

    @Override
    public void run() {
        while(!timer.isTimeExceeded()){
            try {
                work();
                doSideJob();
            }
            catch (InterruptedException e) { }
        }
    }

    @Override
    public long getCompletedSideJobs(){
        return completedSideJobs;
    }

    @Override
    public long getCompletedWork(){
        return this.completedWork;
    }
}
