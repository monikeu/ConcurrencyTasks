package domowe.ActiveObject;

import java.io.*;
import java.util.List;

public class Servant {

    private Buffer buffer;
    private Writer writerCons;
    private Writer writerProd;


    Servant(Buffer b){

        this.buffer=b;

        try {
            writerCons = new BufferedWriter((new FileWriter("acCons.csv")));
            writerCons.write("Time, Amount\n");
            writerProd = new BufferedWriter((new FileWriter("acProd.csv")));
            writerProd.write("Time, Amount\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void take(ConsumerMethodRequest consumerMethodRequest, List<Integer> toTake){
        consumerMethodRequest.future.setAvalaible(true);
        try {
            long l = System.currentTimeMillis() - consumerMethodRequest.start;
            writerCons.write(l + "," + consumerMethodRequest.n + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("Servant is taking " + toTake);
        buffer.freeQ.addAll(toTake);

    }
    public void put (ProducerMethodRequest producerMethodRequest, List<Integer> toPut)  {
        producerMethodRequest.future.setAvalaible(true);
        try {
            long l = System.currentTimeMillis() - producerMethodRequest.start;
            writerProd.write(l + "," + producerMethodRequest.n + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("Servant is putting " + toPut);

        buffer.fullQ.addAll(toPut);
    }

    public void closeFiles() {
        try {
            writerProd.close();
            writerCons.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
