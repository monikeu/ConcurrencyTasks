package domowe.ActiveObject;

import java.util.List;

public class Producer implements Runnable {
    private int number;
    private List<Integer> buffer;
    private Proxy p;

    Producer(int number, List<Integer> buffer, Proxy p) {
        this.p = p;
        this.number = number;
        this.buffer = buffer;
    }


    @Override
    public void run() {

        List<Integer> i;
        while (true) {
            p.put(number);
        }
    }
}
