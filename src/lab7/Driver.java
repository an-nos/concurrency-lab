package lab7;

import lab7.asynchronous.AsynchronousRunner;
import lab7.synchronous.SynchronousRunner;

public class Driver {
    public static void main(String[] args) throws InterruptedException {
        Runner runner;
        if(args[0].equals("sync")) {
            runner = new SynchronousRunner();
        }
        else{
            runner = new AsynchronousRunner();
        }
        runner.init(args);
        runner.run();
        runner.finalizeRun();

    }





}
