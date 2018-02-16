package domowe.producerConsumerSequential;

public interface MonitorIf {

    void put(int amount, int sleepTime);
    void take(int amount, int sleepTime);
}
