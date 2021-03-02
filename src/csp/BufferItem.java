package csp;

import org.jcsp.lang.*;

public class BufferItem implements CSProcess {

    private int val;
    private final int id;

    private final One2OneChannelInt[] consumptionChannels;
    private final One2OneChannelInt productionChannel;

    private final One2OneChannel<One2OneChannelInt[]> consumptionManagerChannel;
    private final One2OneChannel<One2OneChannelInt> productionManagerChannel;

    private final Alternative alternative;

    private static final int CONSUME = 0, PRODUCE = 1;


    public BufferItem(int id,
                      One2OneChannel<One2OneChannelInt[]> consumptionManagerChannel,
                      One2OneChannel<One2OneChannelInt> productionManagerChannel) {

        this.id = id;
        this.val = -1;
        this.consumptionManagerChannel = consumptionManagerChannel;
        this.productionManagerChannel = productionManagerChannel;

        this.productionChannel = Channel.one2oneInt();

        this.consumptionChannels = new One2OneChannelInt[2];
        consumptionChannels[0] = Channel.one2oneInt();
        consumptionChannels[1] = Channel.one2oneInt();

        Guard[] guards = {consumptionChannels[0].in(), productionChannel.in()};
        alternative = new Alternative(guards);

    }


    private void produce(int value) {
        this.val = value;
        System.out.println("Produced in buffer " + id + ": " + val);
    }

    private int consume() {
        int consumedVal = val;
        val = -1;
        System.out.println("Consumed in buffer " + id + ": " + consumedVal);
        return consumedVal;
    }


    public void run() {

        productionManagerChannel.out().write(productionChannel);

        while (true) {

            int idx = alternative.select();

            if (idx == CONSUME) {
                consumptionChannels[0].in().read();
                consumptionChannels[1].out().write(consume());
                productionManagerChannel.out().write(productionChannel);
            } else {
                produce(productionChannel.in().read());
                consumptionManagerChannel.out().write(consumptionChannels);
            }

        }


    }

}
