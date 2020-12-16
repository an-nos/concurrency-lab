package lab7.asynchronous.active_object;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

    private List<Integer> elements;
    private int limit;
    private int taskTime;

    public Buffer(int limit, int taskTime){
        this.limit = limit;
        this.taskTime = taskTime;
        this.elements = new ArrayList<>();
    }

    public void produce(List<Integer> elementsToInsert) throws InterruptedException {
        int size = elementsToInsert.size();
        if(size <= limit - elements.size()){
            elements.addAll(elementsToInsert);
            for(int i = 0; i < size; i++) Thread.sleep(taskTime);
        }
        else{
            throw new IllegalArgumentException("Buffer: tried to produce too many elements");
        }
    }

    public List<Integer> consume(int numberToTake) throws InterruptedException {
        if(elements.size() >= numberToTake){

            List<Integer> consumedElements = new ArrayList<>();

            for(int i = 0; i < numberToTake; i++){
                consumedElements.add(elements.remove(0));
                Thread.sleep(taskTime);
            }

            return consumedElements;

        }
        else{
            throw new IllegalArgumentException("Buffer: tried to consume too many elements");
        }
    }

    public int getEmptyCount(){
        return this.limit - getFullCount();
    }

    public int getFullCount(){
        return this.elements.size();
    }

    public int getSize(){ return this.limit; }

}
