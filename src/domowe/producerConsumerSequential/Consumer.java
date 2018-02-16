package domowe.producerConsumerSequential;

public class Consumer implements Runnable {

    private int number;
    private MonitorIf monitorN;
    private int done = 0;
    private int runsNumber;
    private int sleepTime;


    Consumer(int number, MonitorIf monitorN, int runsNumber, int sleepTime) {
        this.number = number;
        this.monitorN = monitorN;
        this.runsNumber = runsNumber;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        while (done<runsNumber) {
            monitorN.take(number, sleepTime);
            done++;
        }
    }

}