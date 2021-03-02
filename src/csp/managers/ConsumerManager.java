package csp.managers;

import org.jcsp.lang.*;

import java.util.ArrayList;

public class ConsumerManager implements CSProcess {

    private final One2OneChannel<One2OneChannelInt[]>[] bufferChannels;

    private final One2OneChannel<One2OneChannelInt[]>[] consumerChannels;
    private final One2OneChannelInt[] consumerRequestChannels;

    private final int bufferChannelsSize;
    private final int consumerRequestChannelsSize;

    private final ArrayList<One2OneChannelInt[]> waitingBuffers;
    private final ArrayList<Integer> waitingConsumers;

    private final Alternative alternative;


    public ConsumerManager(One2OneChannel<One2OneChannelInt[]>[] bufferChannels,
                           One2OneChannel<One2OneChannelInt[]>[] consumerChannels,
                           One2OneChannelInt[] consumerRequestChannels) {

        this.bufferChannels = bufferChannels;
        this.consumerChannels = consumerChannels;
        this.consumerRequestChannels = consumerRequestChannels;

        bufferChannelsSize = bufferChannels.length;
        consumerRequestChannelsSize = consumerRequestChannels.length;

        waitingBuffers = new ArrayList<>();
        waitingConsumers = new ArrayList<>();

        Guard[] guards = new Guard[bufferChannelsSize + consumerRequestChannelsSize];

        for (int i = 0; i < bufferChannelsSize; i++)
            guards[i] = bufferChannels[i].in();

        for (int i = 0; i < consumerRequestChannelsSize; i++)
            guards[i + bufferChannelsSize] = consumerRequestChannels[i].in();

        alternative = new Alternative(guards);

    }

    public void run() {
        while (true) {
            int idx = alternative.select();

            if (idx < bufferChannelsSize) {
                One2OneChannelInt[] channels = bufferChannels[idx].in().read();
                waitingBuffers.add(channels);

            } else {
                int consumerId = consumerRequestChannels[idx - bufferChannelsSize].in().read();
                waitingConsumers.add(consumerId);
            }

            if (!waitingBuffers.isEmpty() && !waitingConsumers.isEmpty()) {
                int consumerId = waitingConsumers.remove(0);
                consumerChannels[consumerId].out().write(waitingBuffers.remove(0));
            }

        }
    }

}
