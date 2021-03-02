package lab9.onePoneC;

import org.jcsp.lang.*;

/**
 * Producer class: produces 100 random integers and sends them on
 * output channel, then sends -1 and terminates.
 * The random integers are in a given range [start...start+100)
 */
public class Producer implements CSProcess {
    private One2OneChannelInt channel;
    private int start;

    public Producer(final One2OneChannelInt out) {
        channel = out;
    } // constructor

    public void run() {
        while (true) {
            int item = (int) (Math.random() * 100) + 1;
            channel.out().write(item);
        }
    } // run
} // class Producer

// ŚRODA 14.40 - do 13.45
// bufor ograniczony

