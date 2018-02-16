package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {
    private int N;
    public int[] buffer;
    public Queue<Integer> freeQ = new LinkedList<>();
    public Queue<Integer> fullQ = new LinkedList<>();

    Buffer(int N) {
        this.N = N;
        buffer = new int[N];
        for (int i = 0; i < N; i++) {
            freeQ.add(i);
            buffer[i] = 0;
        }

    }

}
