package domowe.ActiveObject;

import java.util.List;

public class Servant {

    private Buffer buffer;

    Servant(Buffer b){
        this.buffer=b;
    }

    public void take(ConsumerMethodRequest consumerMethodRequest, List<Integer> toTake){
        consumerMethodRequest.future.setAvalaible(true);
        System.out.println("Servant is taking " + toTake);

        buffer.freeQ.addAll(toTake);

    }
    public void put (ProducerMethodRequest producerMethodRequest, List<Integer> toPut){
        producerMethodRequest.future.setAvalaible(true);
        System.out.println("Servant is putting " + toPut);

        buffer.fullQ.addAll(toPut);
    }
}
