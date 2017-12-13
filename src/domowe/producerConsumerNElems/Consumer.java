package domowe.producerConsumerNElems;

import domowe.ProducerConsumerAsynchronous.MonitorIf;

public class Consumer implements Runnable {

    private int number;
    private MonitorIf monitorN;
    public static int done = 0;

    Consumer(int number, MonitorIf monitorN) {
        this.number = number;
        this.monitorN = monitorN;
    }

    @Override
    public void run() {
        while (done < 5000 && Producer.done < 5000) {
            add();
            monitorN.take(number);
        }
    }

    synchronized void add(){
        done++;
    }
}