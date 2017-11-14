package domowe;

public class Consumer4341 implements Runnable {

    private int number;
    private Monitor4341 monitorN;

    Consumer4341(int number, Monitor4341 monitorN) {
        this.number = number;
        this.monitorN = monitorN;
    }

    @Override
    public void run() {
        int i;
        while (true) {
            System.out.println("Consuming\n");
            i = monitorN.get_beginning();
            monitorN.get_end(i);
            System.out.println("Consumed " + i + "\n");
        }
    }
}