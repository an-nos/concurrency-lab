package lab7.client;


public class TimeManager {
    private int timeQuantum;
    private long totalWorkTime;
    private long timeLeft;

    public TimeManager(int timeQuantum, int totalWorkTime){
        this.timeQuantum = timeQuantum;
        this.totalWorkTime = totalWorkTime;
        this.timeLeft = totalWorkTime;
    }

    public void reduceTime(){
        timeLeft -= timeQuantum;
    }

    public int getTimeQuantum(){
        return this.timeQuantum;
    }

    public long getTimeLeft(){
        return this.timeLeft;
    }

    public void restartTime(){
        timeLeft = totalWorkTime;
    }

    public long getMaxTime(){
        return totalWorkTime;
    }

}
