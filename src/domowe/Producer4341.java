package domowe;

public class Producer4341 implements Runnable {

    private int number;
    private Monitor4341 monitorN;

    Producer4341(int number, Monitor4341 monitorN) {
        this.number = number;
        this.monitorN = monitorN;
    }

    @Override
    public void run() {
        int i;
        while (true) {
            System.out.println("Producing\n");
            i = monitorN.put_begining();
            monitorN.put_end(i);
            System.out.println("Produced " + i + "\n");
        }
    }
}