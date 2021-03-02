package lab9.onePoneC;

import org.jcsp.lang.*;

/**
 * Consumer class: reads ints from input channel, displays them,
 * then
 * terminates when a negative value is read.
 */
public class Consumer implements CSProcess {
    private One2OneChannelInt channel;

    public Consumer(final One2OneChannelInt in) {
        channel = in;
    } // constructor

    public void run() {
        while (true) {
            int item = channel.in().read();
            System.out.println(item);
        }
    } // run
} // class Consumer