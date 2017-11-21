package domowe.ProducerConsumerAsynchronous;

public interface MonitorIf {

    void put(int amount);
    void take(int amount);
}
