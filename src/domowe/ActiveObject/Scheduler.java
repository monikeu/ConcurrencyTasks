package domowe.ActiveObject;

import labs.Monitor;

import java.util.List;

public class Scheduler implements Runnable {

    private ActivationQueue activationQueue;
    private MonitorN monitorN;

    Scheduler(){
        monitorN = new MonitorN(10);
        activationQueue=new ActivationQueue(10);
    }

    public void dispatch(){
        List<Integer> i = monitorN.getBegin(1);
        MethodRequest methodRequest = activationQueue.dequeue(i.get(0));
        if(methodRequest.guard()) {
            try {
                methodRequest.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            monitorN.getEnd(i);
        }
        else {
            monitorN.getEnd(i);
            enqueue(methodRequest);
        }
    }

    public void enqueue(MethodRequest methodRequest){
        List<Integer> i = monitorN.putBegin(1);
        activationQueue.enqueue(methodRequest,i.get(0));
        monitorN.putEnd(i);
    }

    @Override
    public void run() {
        while(true){
            dispatch();
        }
    }
}
