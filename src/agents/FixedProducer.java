package agents;

import Observers.UsersObserver;
import monitors.Monitor;

import java.io.IOException;

public class FixedProducer extends Agent {
    final int producedResources;

    public FixedProducer(Monitor monitor, UsersObserver observer, int maxRandNumber, int producedResources) {
        super(monitor, observer, maxRandNumber);
        this.producedResources = producedResources;
    }

    @Override
    public void run() {
        while (true) {
            try {
                monitor.produce(id, producedResources);
                observer.normalWorkDone(id);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
