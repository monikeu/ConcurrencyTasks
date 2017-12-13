package domowe.producerConsumerNElems;

import domowe.ProducerConsumerAsynchronous.MonitorIf;

public class Producer implements Runnable {

    private int number;
    private MonitorIf monitorN;
    public  static int done = 0;


    Producer(int number, MonitorIf monitorN) {
        this.number = number;
        this.monitorN = monitorN;
    }

    @Override
    public void run() {
        while (done < 5000 && Consumer.done < 5000) {
            add();
            monitorN.put(number);
        }
    }

    synchronized void add(){
        done++;
    }
}