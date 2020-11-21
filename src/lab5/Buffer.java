package lab5;

import java.util.List;

public interface Buffer {

    void produce(List<Integer> newVals, int idx) throws InterruptedException;

    List<Integer> consume(int numberToTake, int idx) throws InterruptedException;

    int getLimit();

}
