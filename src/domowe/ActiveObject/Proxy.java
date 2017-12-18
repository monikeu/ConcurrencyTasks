package domowe.ActiveObject;

public class Proxy {

    private Scheduler scheduler;
    private Thread schedulerRunner;
    private int threadNumb;
    private int n;
    private int sleepTime;
    private String consFileName;
    private String prodFileName;
    private int runsNumber;

    public Proxy(int threadNumb, int N, int sleepTime, String consFileName, String prodFileName, int runsNumber){
        this.threadNumb = threadNumb;
        n = N;
        this.sleepTime = sleepTime;
        this.consFileName = consFileName;
        this.prodFileName = prodFileName;
        this.runsNumber = runsNumber;
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
        scheduler = new Scheduler(threadNumb,n,sleepTime, consFileName, prodFileName, runsNumber*2*threadNumb);
        schedulerRunner = new Thread(scheduler);
        schedulerRunner.start();
    }

    public void joinScheduler() throws InterruptedException {
        schedulerRunner.join();
    }
}
