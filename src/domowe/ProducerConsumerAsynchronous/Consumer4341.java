package domowe.ProducerConsumerAsynchronous;

public class Consumer4341 implements Runnable {

    private int number;
    private Monitor4341 monitor;
    private int buffer[];

    Consumer4341(int number, Monitor4341 monitor, int[] buffer) {

        this.number = number;
        this.monitor = monitor;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int i;
        while (true) {
//            System.out.println("Consuming\n");
            i = monitor.get_beginning();
            monitor.get_end(i);
        }
    }
}