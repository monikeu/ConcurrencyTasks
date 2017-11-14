package domowe;

import java.util.Random;

public class Main4341 {
    private final static int M = 10;
    private final static int producersNumber = 10;
    private final static int consumersNumber = 10;

    public static void main(String[] args) {
        Monitor4341 monitorN = new Monitor4341(M);

        Thread producers[] = new Thread[producersNumber];

        Random random = new Random();



        for(int i=0;i<producersNumber;i++){
            producers[i] = new Thread(new Producer4341(random.nextInt(M)+1, monitorN));
            producers[i].start();
        }

        Thread customer = new Thread(new Consumer4341(random.nextInt(M), monitorN));
        customer.start();
    }
}
