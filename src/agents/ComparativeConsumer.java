package agents;

import Observers.UsersObserver;
import monitors.Monitor;

import java.io.IOException;

public class ComparativeConsumer extends User {
    public ComparativeConsumer(Monitor monitor, UsersObserver observer, int maxRandNumber, int extraWorkTimeNanos) {
        super(monitor, observer, maxRandNumber, extraWorkTimeNanos);
    }

    @Override
    public void doNormalTask(int value) throws IOException, InterruptedException {
        monitor.consume(id,value);
    }
}
