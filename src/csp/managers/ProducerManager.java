package csp.managers;

import org.jcsp.lang.*;

import java.util.ArrayList;

public class ProducerManager implements CSProcess {

    private final One2OneChannel<One2OneChannelInt>[] bufferChannels;

    private final One2OneChannel<One2OneChannelInt>[] producerChannels;
    private final One2OneChannelInt[] producerRequestChannels;

    private final int bufferChannelsSize;
    private final int producerRequestChannelsSize;

    private final ArrayList<One2OneChannelInt> waitingBuffers;
    private final Alternative alternative;
    private final ArrayList<Integer> waitingProducers;


    public ProducerManager(One2OneChannel<One2OneChannelInt>[] bufferChannels,
                           One2OneChannel<One2OneChannelInt>[] producerChannels,
                           One2OneChannelInt[] producerRequestChannels) {

        this.bufferChannels = bufferChannels;
        this.producerChannels = producerChannels;
        this.producerRequestChannels = producerRequestChannels;

        bufferChannelsSize = bufferChannels.length;
        producerRequestChannelsSize = producerRequestChannels.length;

        waitingBuffers = new ArrayList<>();
        waitingProducers = new ArrayList<>();

        Guard[] guards = new Guard[bufferChannelsSize + producerRequestChannelsSize];

        for (int i = 0; i < bufferChannelsSize; i++)
            guards[i] = bufferChannels[i].in();

        for (int i = 0; i < producerRequestChannelsSize; i++)
            guards[i + bufferChannelsSize] = producerRequestChannels[i].in();

        alternative = new Alternative(guards);

    }

    public void run() {
        while (true) {
            int idx = alternative.select();

            if (idx < bufferChannelsSize) {
                One2OneChannelInt channel = bufferChannels[idx].in().read();
                waitingBuffers.add(channel);

            } else {
                int id = producerRequestChannels[idx - bufferChannelsSize].in().read();
                waitingProducers.add(id);
            }

            if (!waitingBuffers.isEmpty() && !waitingProducers.isEmpty()) {
                int producerId = waitingProducers.remove(0);
                producerChannels[producerId].out().write(waitingBuffers.remove(0));
            }

        }
    }
}
