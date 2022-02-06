package sample;

import Observers.UsersObserver;
import agents.Consumer;
import agents.FixedConsumer;
import agents.FixedProducer;
import agents.Producer;
import monitors.ConditionMonitor;
import monitors.LockMonitor;
import monitors.Monitor;
import monitors.SimpleMonitor;
import utils.Buffer;


public class Main_Starving {
    private final static int CONSUMERS = 10;
    private final static int PRODUCERS = 10;
    private final static int BUFFER_SIZE = 100;

    public static void main(String[] args) throws InterruptedException {
        Thread[] consumers = new Thread[CONSUMERS];
        Thread[] producers = new Thread[PRODUCERS];

        Buffer buffer = new Buffer(BUFFER_SIZE);
        Monitor monitor = new SimpleMonitor(buffer);
        UsersObserver observer = new UsersObserver(6000);

        for (int i=0; i<CONSUMERS; i++) {
            consumers[i] = new Thread(new FixedConsumer(monitor, observer,BUFFER_SIZE/2, 10));
        }
        producers[0] = new Thread(new FixedProducer(monitor, observer, BUFFER_SIZE/2, 50));
        for (int i=1; i<PRODUCERS; i++) {
            producers[i] = new Thread(new FixedProducer(monitor, observer, BUFFER_SIZE/2, 10));
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
