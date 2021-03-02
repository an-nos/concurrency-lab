package csp.workers;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public abstract class Worker implements CSProcess {

    protected One2OneChannelInt requestChannel;
    protected int id;

    public Worker(int id, One2OneChannelInt requestChannel) {
        this.id = id;
        this.requestChannel = requestChannel;
    }

    public void run() {
        while (true) {
            work();
        }
    }

    protected abstract void work();

}
