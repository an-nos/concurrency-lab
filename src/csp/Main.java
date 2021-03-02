package csp;

import csp.managers.ConsumerManager;
import csp.managers.ProducerManager;
import csp.workers.Consumer;
import csp.workers.Producer;
import org.jcsp.lang.*;


public class Main {

    public static void main(String[] args) {

        int bufferNo = 2, producerNo = 2, consumerNo = 2;

        CSProcess[] processes = new CSProcess[bufferNo + consumerNo + producerNo + 2];

        One2OneChannel<One2OneChannelInt>[] bufferChannelsProd = new One2OneChannel[bufferNo];
        One2OneChannel<One2OneChannelInt>[] producerResponseChannels = new One2OneChannel[producerNo];
        One2OneChannelInt[] producerRequestChannels = new One2OneChannelInt[producerNo];

        One2OneChannel<One2OneChannelInt[]>[] bufferChannelsCons = new One2OneChannel[bufferNo];
        One2OneChannel<One2OneChannelInt[]>[] consumerResponseChannels = new One2OneChannel[consumerNo];
        One2OneChannelInt[] consumerRequestChannels = new One2OneChannelInt[consumerNo];


        for (int i = 0; i < bufferNo; i++) {

            bufferChannelsProd[i] = Channel.one2one();
            bufferChannelsCons[i] = Channel.one2one();

            processes[i + 2] = new BufferItem(i, bufferChannelsCons[i], bufferChannelsProd[i]);

        }

        for (int i = 0; i < producerNo; i++) {
            producerRequestChannels[i] = Channel.one2oneInt();
            producerResponseChannels[i] = Channel.one2one();
            processes[i + bufferNo + 2] = new Producer(i, producerRequestChannels[i], producerResponseChannels[i]);
        }

        for (int i = 0; i < consumerNo; i++) {
            consumerRequestChannels[i] = Channel.one2oneInt();
            consumerResponseChannels[i] = Channel.one2one();
            processes[i + bufferNo + producerNo + 2] = new Consumer(i, consumerRequestChannels[i], consumerResponseChannels[i]);
        }

        processes[0] = new ProducerManager(bufferChannelsProd, producerResponseChannels, producerRequestChannels);
        processes[1] = new ConsumerManager(bufferChannelsCons, consumerResponseChannels, consumerRequestChannels);


        Parallel parallel = new Parallel(processes);
        parallel.run();
    }
}
