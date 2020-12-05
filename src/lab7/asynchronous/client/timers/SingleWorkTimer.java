package lab7.asynchronous.client.timers;

public class SingleWorkTimer {
    private int timeQuantum;
    private long singleWorkTime;
    private long timeWorked;

    public SingleWorkTimer(int timeQuantum, int singleWorkTime){
        this.timeQuantum = timeQuantum;
        this.singleWorkTime = singleWorkTime;
        this.timeWorked = 0;
    }

    public void workQuantum(){ timeWorked += timeQuantum; }

    public void workFor(long period){ timeWorked += period; }

    public int getTimeQuantum(){
        return timeQuantum;
    }

    public long getTimeLeft(){
        return singleWorkTime - timeWorked;
    }

    public void restartTime(){
        timeWorked = 0;
    }

    public long getMaxTime(){
        return singleWorkTime;
    }

    public long getTimeWorked(){
        return timeWorked;
    }
}
