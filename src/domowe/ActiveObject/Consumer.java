package domowe.ActiveObject;

import java.util.List;

public class Consumer implements Runnable {
    private int number;
    private List<Integer> buffer;
    private Proxy p;

    Consumer(int number, List<Integer> buffer, Proxy p) {
        this.p = p;
        this.number = number;
        this.buffer = buffer;
    }


    @Override
    public void run() {

        List<Integer> i;
        while (true) {
            p.take(number);
        }
    }
}
