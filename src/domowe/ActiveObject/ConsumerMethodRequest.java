package domowe.ActiveObject;

import java.util.ArrayList;
import java.util.List;

public class ConsumerMethodRequest implements MethodRequest {

    private int n;
    private Future future;

    public ConsumerMethodRequest(int n, Future future){
        this.future=future;
        this.n=n;
    }

    @Override
    public boolean guard(int inFullQ) {
      if(inFullQ >=n){
          Scheduler.reserveForConsumer(n);
          return true;
      }
      return false;
    }

    @Override
    public void run() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(Scheduler.fullQ.poll());
        }
        // consume

        Scheduler.freeQ.addAll(list);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Scheduler.giveBackFree(n);
        future.setAvalaible(true);
        System.out.println("Consumed " + n + " elems\n");
    }

}
