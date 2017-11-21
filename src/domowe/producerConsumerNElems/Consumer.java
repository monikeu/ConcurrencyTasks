package domowe.producerConsumerNElems;

import domowe.ProducerConsumerAsynchronous.MonitorIf;

public class Consumer implements Runnable {

    private int number;
    private MonitorIf monitorN;

    Consumer(int number, MonitorIf monitorN) {
        this.number = number;
        this.monitorN = monitorN;
    }

    @Override
    public void run() {
        while (true)
            monitorN.take(number);
    }
}