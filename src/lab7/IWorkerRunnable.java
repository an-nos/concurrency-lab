package lab7;

public interface IWorkerRunnable extends Runnable{
    long getCompletedWork();
    long getCompletedSideJobs();
}
