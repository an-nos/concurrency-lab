package lab7;

import lab7.synchronous.worker.WorkerRunnable;

import java.util.ArrayList;
import java.util.List;

public abstract class Runner {
    protected int size;
    protected int producersNo;
    protected int consumersNo;
    protected int bufferTaskTime;
    protected int sideJobLength;
    protected long maxTime;
    protected int timeQuantum;
    protected int approachIdx;

    protected List<IWorkerRunnable> workers;

    public Runner(){
        this.workers = new ArrayList<>();
    }

    public void init(String args[]){
        int a = 1;
        size = Integer.parseInt(args[a++]);
        producersNo = Integer.parseInt(args[a++]);
        consumersNo = Integer.parseInt(args[a++]);
        bufferTaskTime = Integer.parseInt(args[a++]);
        sideJobLength = Integer.parseInt(args[a++]);
        maxTime = Long.parseLong(args[a++]);
        timeQuantum = Integer.parseInt(args[a++]);
        approachIdx = Integer.parseInt(args[a]);

    }

    public abstract void run() throws InterruptedException;

    public void finalizeRun(){
        int totalWork = 0;
        int totalSideJobs = 0;
        for(IWorkerRunnable w : workers){
            totalWork += w.getCompletedWork();
            totalSideJobs += w.getCompletedSideJobs();
        }

        Object[] params = new Object[]{approachIdx, size, producersNo, consumersNo, bufferTaskTime, sideJobLength, maxTime, timeQuantum, totalWork, totalSideJobs};
        StringBuilder sb = new StringBuilder();
        String pref = "";
        for (Object obj: params){
            sb.append(pref);
            pref=",";
            sb.append(obj);
        }

        System.out.println(sb.toString());
    }

}
