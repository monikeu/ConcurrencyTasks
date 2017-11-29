package domowe.ActiveObject;

import domowe.producerConsumerNElems.Producer;

import java.util.concurrent.Callable;

public class Proxy {

    private Scheduler scheduler;
    private Thread schedulerRunner;
    private Servant servant;

    public Future method1(){
        Future future = new Future();
        Callable method = (Callable<Future>) () -> servant.method1();
        scheduler.enqueue(new MethodRequest(future, method));
        return future;
    }
    public Future method2(){return null;}
    public Future method3(){return null;}

    Proxy(){
        servant = new Servant();
        scheduler=new Scheduler();
        schedulerRunner = new Thread(scheduler);
        schedulerRunner.start();
    }
}
