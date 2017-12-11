package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class Scheduler implements Runnable {

    private static QueueConsume consumerQueue = new QueueConsume();
    private static QueueProduce producerQueue = new QueueProduce();

    public static int N = 20;
    private static Buffer buffer = new Buffer(N);

    public Servant servant = new Servant(buffer);


    public Scheduler() {
    }

    public void enqueueInProducersQueue(MethodRequest methodRequest) {
        producerQueue.enqueue(methodRequest);
    }

    public void enqueueInConsumersQueue(MethodRequest methodRequest) {
        consumerQueue.enqueue(methodRequest);
    }


    @Override
    public void run() {
        ProducerMethodRequest currentProd;
        ConsumerMethodRequest currentCons;

        while (true) {
            buffer.printState();
            if (!producerQueue.isEmpty()) {
                if (producerQueue.peek().guard(buffer.freeQ.size())) { //dopisać guardy
                    List<Integer> prodIndexes = new ArrayList<>();
                    currentProd = producerQueue.dequeue();

                    for (int i = 0; i < currentProd.n; i++) {
                        try {
                            Integer take = buffer.freeQ.take();
                            buffer.buffer[take] = 1;
                            prodIndexes.add(take);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    servant.put(currentProd, prodIndexes);
                }
            }
            buffer.printState();
            if (!consumerQueue.isEmpty()) {
                if (consumerQueue.peek().guard(buffer.fullQ.size())) { //dopisać guardy
                    List<Integer> consIndexes = new ArrayList<>();
                    currentCons = consumerQueue.dequeue();

                    for (int i = 0; i < currentCons.n; i++) {
                        try {
                            Integer take = buffer.fullQ.take();
                            buffer.buffer[take]=0;
                            consIndexes.add(take);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    servant.take(currentCons, consIndexes);
                }
            }
//              if( (currentProd = producerQueue.dequeue()) != null){
//                  List<Integer> prodIndexes = new ArrayList<>();
//                  for (int i = 0; i < currentProd.n; i++) {
//                      try {
//                          prodIndexes.add(buffer.freeQ.take());
//                      } catch (InterruptedException e) {
//                          e.printStackTrace();
//                      }
//                  }
//                  servant.put(currentProd, prodIndexes);
//              }
//
//            if( (currentCons = consumerQueue.dequeue()) != null){
//                List<Integer> consIndexes = new ArrayList<>();
//                for (int i = 0; i < currentCons.n; i++) {
//                    try {
//                        consIndexes.add(buffer.fullQ.take());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                servant.take(currentCons, consIndexes);
//            }
        }
    }

    void printBuffState(){

    }
}
