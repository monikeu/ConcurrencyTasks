package domowe;

public class Producer implements Runnable {

    private int number;
    private MonitorIf monitorN;

    Producer(int number, MonitorIf monitorN) {
        this.number = number;
        this.monitorN = monitorN;
    }

    @Override
    public void run() {
        while (true)
            monitorN.put(number);
    }
}