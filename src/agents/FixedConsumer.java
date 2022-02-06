package agents;

import Observers.UsersObserver;
import monitors.Monitor;

import java.io.IOException;

public class FixedConsumer extends Agent {
    final int consumedResources;

    public FixedConsumer(Monitor monitor, UsersObserver observer, int maxRandNumber, int consumedResources) {
        super(monitor, observer, maxRandNumber);
        this.consumedResources = consumedResources;
    }

    @Override
    public void run() {
        while (true) {
            try {
                monitor.consume(id, consumedResources);
                observer.normalWorkDone(id);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}