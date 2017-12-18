package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class Scheduler implements Runnable {

    private static QueueConsume consumerQueue = new QueueConsume();
    private static QueueProduce producerQueue = new QueueProduce();
    private static Buffer buffer;
    private int threadNumb;
    private int N;
    private String consFileName;
    private String prodFileName;
    private int runsNumber;


    public int done = 0;
    public Servant servant;


    public Scheduler(int threadNumb, int M, int sleepTime, String consFileName, String prodFileName, int runsNumber) {
        this.threadNumb = threadNumb;

        this.N = 2*M;
        this.consFileName = consFileName;
        this.prodFileName = prodFileName;
        this.runsNumber = runsNumber;
        buffer = new Buffer(N);
        servant = new Servant(buffer,threadNumb,N, sleepTime,consFileName,prodFileName);
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

        while (done < runsNumber) {
            if (!producerQueue.isEmpty()) {

                if (producerQueue.peek().guard(buffer.freeQ.size())) {
                    List<Integer> prodIndexes = new ArrayList<>();
                    currentProd = producerQueue.dequeue();

                    for (int i = 0; i < currentProd.n; i++) {
                        Integer take = buffer.freeQ.poll();
                        buffer.buffer[take] = 1;
                        prodIndexes.add(take);
                    }
                    servant.put(currentProd, prodIndexes);
                    done++;
                }

            }
            if (!consumerQueue.isEmpty()) {
                if (consumerQueue.peek().guard(buffer.fullQ.size())) {
                    List<Integer> consIndexes = new ArrayList<>();
                    currentCons = consumerQueue.dequeue();

                    for (int i = 0; i < currentCons.n; i++) {
                        Integer take = buffer.fullQ.poll();
                        buffer.buffer[take]=0;
                        consIndexes.add(take);
                    }
                    servant.take(currentCons, consIndexes);
                    done++;
                }
            }
        }

        servant.closeFiles();
    }

}
