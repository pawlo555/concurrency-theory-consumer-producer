package sample;


import Observers.UsersObserver;
import agents.Consumer;
import agents.Producer;
import monitors.ConditionMonitor;
import monitors.Monitor;
import utils.Buffer;

public class Main_DeadLock {
    private final static int CONSUMERS = 5;
    private final static int PRODUCERS = 1;
    private final static int MAX_BUFFER = 10;

    public static void main(String[] args) throws InterruptedException {
        Thread[] consumers = new Thread[CONSUMERS];
        Thread[] producers = new Thread[PRODUCERS];

        UsersObserver observer = new UsersObserver(10000);
        Buffer buffer = new Buffer(MAX_BUFFER);
        Monitor monitor = new ConditionMonitor(buffer);

        for (int i=0; i<CONSUMERS; i++) {
            consumers[i] = new Thread(new Consumer(monitor, observer, MAX_BUFFER/2));
        }
        for (int i=0; i<PRODUCERS; i++) {
            producers[i] = new Thread(new Producer(monitor, observer, MAX_BUFFER/2));
        }

        for (int i=0; i<CONSUMERS; i++) {
            consumers[i].start();
        }
        for (int i=0; i<PRODUCERS; i++) {
            producers[i].start();
        }

        for (int i=0; i<CONSUMERS; i++) {
            consumers[i].join();
        }
        for (int i=0; i<PRODUCERS; i++) {
            producers[i].join();
        }
    }
}
