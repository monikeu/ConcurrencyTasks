package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class Scheduler implements Runnable {

    private static QueueConsume consumerQueue = new QueueConsume();
    private static QueueProduce producerQueue = new QueueProduce();

    public static int N = 200;
    private static Buffer buffer = new Buffer(N);

    public static int done = 0;
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

        while (done < 10000) {
//            buffer.printState();
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
                    done++;
                }

            }
//            buffer.printState();
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
                    done++;
                }
            }
        }

        servant.closeFiles();
    }

    void printBuffState(){

    }
}
