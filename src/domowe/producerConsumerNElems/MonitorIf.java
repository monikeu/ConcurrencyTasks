package domowe.producerConsumerNElems;

public interface MonitorIf {

    void put(int amount, int sleepTime);
    void take(int amount, int sleepTime);
}
