package lab7.asynchronous.client.timers;

public class WorkerTimer {
    private long timeStart;
    private long maxTime;

    public WorkerTimer(long maxTime){
        this.maxTime = maxTime;
    }

    public void startTime(){
        timeStart = System.currentTimeMillis();
    }

    public long getTime(){
        return System.currentTimeMillis();
    }

    public boolean isTimeExceeded(){
        return getTime() - timeStart >= maxTime;
    }
}
