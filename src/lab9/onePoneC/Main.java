package lab9.onePoneC;

import org.jcsp.lang.*;

public final class Main {
    public static void main(String[] args) {
        new Main();
    } // main

    public Main() { // Create channel object
        final One2OneChannelInt channel = Channel.one2oneInt();
// Create and run parallel construct with a list of processes
        CSProcess[] procList = {new Producer(channel), new Consumer(channel)};
// Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    } // PCMain constructor
} // class PCMain