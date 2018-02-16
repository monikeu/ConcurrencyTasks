package domowe.producerConsumerSequential;

public class Producer implements Runnable {

    private int number;
    private MonitorIf monitorN;
    private int sleepTime;
    private int done = 0;
    private int runsNumber;

    public Producer(int number, MonitorN monitorN, int runsNumber, int sleepTime) {
        this.number = number;
        this.runsNumber = runsNumber;
        this.monitorN = monitorN;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        while (done < runsNumber) {
            monitorN.put(number, sleepTime);
            done++;
        }
    }

}