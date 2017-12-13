package domowe.ActiveObject;

public class Proxy {

    private Scheduler scheduler;
    private Thread schedulerRunner;

    public Proxy(){
    }

    public Future take(int n){
        Future res = new Future();
        scheduler.enqueueInConsumersQueue(new ConsumerMethodRequest(n,res,System.currentTimeMillis()));
        return res;
    }

    public Future put(int n){
        Future res = new Future();
        scheduler.enqueueInProducersQueue(new ProducerMethodRequest(n,res, System.currentTimeMillis()));
        return res;
    }

    public void runScheduler(){
        scheduler = new Scheduler();
        schedulerRunner = new Thread(scheduler);
        schedulerRunner.start();
    }

    public void joinScheduler() throws InterruptedException {
        schedulerRunner.join();
    }
}
