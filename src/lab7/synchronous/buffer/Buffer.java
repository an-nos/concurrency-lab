package lab7.synchronous.buffer;

import java.util.List;

public interface Buffer {

    void produce(List<Integer> newVals, int idx) throws InterruptedException;

    List<Integer> consume(int numberToTake, int idx) throws InterruptedException;

    int getLimit();

}
