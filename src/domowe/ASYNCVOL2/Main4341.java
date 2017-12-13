package domowe.ASYNCVOL2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Main4341 {
    private final static int M = 10;
    private final static int producersNumber = 10;
    private final static int consumersNumber = 10;

    public static void main(String[] args) {

        List<Integer> buffer = new ArrayList<>();

        MonitorN monitorN = new MonitorN(M);

        Thread producers[] = new Thread[producersNumber];
        Thread consumers[] = new Thread[consumersNumber];
        
        Random random = new Random();

        for(int i=0;i<consumersNumber;i++){
            consumers[i] = new Thread(new Consumer4341(abs(random.nextInt(M)), monitorN,buffer));
            consumers[i].start();
        }
        
        for(int i=0;i<producersNumber;i++){
            producers[i] = new Thread(new Producer4341(abs(random.nextInt(M)), monitorN,buffer));
            producers[i].start();
        }

        for(int i=0;i<consumersNumber;i++){
            try {
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<producersNumber;i++){
            try {
                producers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        monitorN.closeFiles();
    }
}

// dwa locki muszą być bo jeden dla producerów drugi da consumerów
