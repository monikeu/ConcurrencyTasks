package domowe.ActiveObject;

public class Scheduler implements Runnable {

    ActivationQueue activationQueue;

    public void dispatch(){}

    public void enqueue(MethodRequest methodRequest){}

    Scheduler(){
        activationQueue=new ActivationQueue();
    }

    @Override
    public void run() {

    }
}
