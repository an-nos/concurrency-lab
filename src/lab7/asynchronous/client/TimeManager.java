package lab7.asynchronous.client;


import lab7.asynchronous.client.timers.SingleWorkTimer;
import lab7.asynchronous.client.timers.WorkerTimer;

public class TimeManager {

    private SingleWorkTimer singleWorkTimer;
    private WorkerTimer workerTimer;

    public TimeManager(int timeQuantum, int singleWorkTime, long maxTime){
        this.singleWorkTimer = new SingleWorkTimer(timeQuantum, singleWorkTime);
        this.workerTimer = new WorkerTimer(maxTime);
    }

    public void startTime(){
        this.workerTimer.startTime();
    }

    public boolean isTimeExceeded(){
        return workerTimer.isTimeExceeded();
    }

    public int timeQuantum(){
        return singleWorkTimer.getTimeQuantum();
    }

    public void workQuantum(){
        singleWorkTimer.workQuantum();
    }

    public long timeLeft(){
        return singleWorkTimer.getTimeLeft();
    }

    public void workFor(long period){
        singleWorkTimer.workFor(period);
    }

    public long getTimeWorked(){
        return singleWorkTimer.getTimeWorked();
    }

    public void restartTime(){
        this.singleWorkTimer.restartTime();
    }




}
