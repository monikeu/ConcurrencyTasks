package domowe.ProducerConsumerAsynchronous;

public class Producer4341 implements Runnable {

    private int number;
    private Monitor4341 monitor;
    private int buffer[];

    Producer4341(int number, Monitor4341 monitor,int[] buffer) {
        this.number = number;
        this.monitor = monitor;
        this.buffer = buffer;

    }

    @Override
    public void run() {
        int i;
        while (true) {
//            System.out.println("Producing\n");
            i = monitor.put_begining();
            if(i != 10){
                monitor.put_end(i);
            }
            else {
                System.out.println("Lost portion\n");
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}