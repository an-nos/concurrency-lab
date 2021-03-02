package csp.workers;

import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.One2OneChannelInt;

import java.util.Random;

public class Producer extends Worker {
    protected One2OneChannel<One2OneChannelInt> responseChannel;
    private One2OneChannelInt bufferChannel;

    private final Random rand;

    public Producer(int id, One2OneChannelInt requestChannel, One2OneChannel<One2OneChannelInt> responseChannel) {
        super(id, requestChannel);
        this.rand = new Random();
        this.responseChannel = responseChannel;
    }

    protected void work() {
        int val = rand.nextInt(100);

        requestChannel.out().write(id);
        bufferChannel = responseChannel.in().read();

        bufferChannel.out().write(val);
        System.out.println("Producer " + id + ": " + val);

    }


}
