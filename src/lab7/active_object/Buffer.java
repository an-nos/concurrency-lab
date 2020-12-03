package lab7.active_object;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

    private List<Integer> elements;
    private int limit;

    public Buffer(int limit){
        this.limit = limit;
        this.elements = new ArrayList<>();
    }

    public void produce(List<Integer> elementsToInsert){
        if(elementsToInsert.size() <= limit - elements.size()){
            elements.addAll(elementsToInsert);
        }
        else{
            throw new IllegalArgumentException("Buffer: tried to produce too many elements");
        }
    }

    public List<Integer> consume(int numberToTake){
        if(elements.size() >= numberToTake){

            List<Integer> consumedElements = new ArrayList<>();

            for(int i = 0; i < numberToTake; i++){
                consumedElements.add(elements.remove(0));
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
