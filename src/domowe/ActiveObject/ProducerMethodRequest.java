package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class ProducerMethodRequest implements MethodRequest {

    private int n;
    private Future future;

    public ProducerMethodRequest(int n, Future future){
        this.future=future;
        this.n=n;
    }

    @Override
    public boolean guard(int inFreeQ) {
      if(inFreeQ >= n){
          Scheduler.reserveForProducer(n);
          return true;
      }
      return false;
    }


    public void run() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(Scheduler.freeQ.poll());
        }
        // produce

        Scheduler.fullQ.addAll(list);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Scheduler.giveBackFull(n);
        future.setAvalaible(true);
        System.out.println("Produced  " + n + " elems\n");
    }
}
