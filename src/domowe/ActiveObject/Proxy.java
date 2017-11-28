package domowe.ActiveObject;

import domowe.producerConsumerNElems.Producer;

public class Proxy {

    private Scheduler scheduler;
    private Thread schedulerRunner;

    public Future method1(){return null;}
    public Future method2(){return null;}
    public Future method3(){return null;}

    Proxy(){
        scheduler=new Scheduler();
        schedulerRunner = new Thread(scheduler);
    }
}
