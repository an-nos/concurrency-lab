package lab7;

import lab7.asynchronous.AsynchronousMain;
import lab7.synchronous.SynchronousMain;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        Runner runner;
        if(args[0].equals("sync")) {
            runner = new SynchronousMain();
        }
        else{
            runner = new AsynchronousMain();
        }
        runner.init(args);
        runner.run();
        runner.finalizeRun();

    }





}
