package csp.workers;

import org.jcsp.lang.One2OneChannel;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer extends Worker {
    protected One2OneChannel<One2OneChannelInt[]> responseChannel;

    private One2OneChannelInt[] bufferChannels;

    public Consumer(int id, One2OneChannelInt requestChannel, One2OneChannel<One2OneChannelInt[]> responseChannel) {
        super(id, requestChannel);
        this.responseChannel = responseChannel;

    }

    protected void work() {

        requestChannel.out().write(id);
        bufferChannels = responseChannel.in().read();

        bufferChannels[0].out().write(id);
        int val = bufferChannels[1].in().read();
        System.out.println("Consumer " + id + ": " + val);

    }


}
