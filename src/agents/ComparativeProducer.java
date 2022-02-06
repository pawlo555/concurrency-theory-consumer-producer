package agents;

import Observers.UsersObserver;
import monitors.Monitor;
import java.io.IOException;

public class ComparativeProducer extends User {
    public ComparativeProducer(Monitor monitor, UsersObserver observer, int maxRandNumber, int extraWorkTimeNanos) {
        super(monitor, observer, maxRandNumber, extraWorkTimeNanos);
    }

    @Override
    public void doNormalTask(int value) throws IOException, InterruptedException {
        monitor.produce(id,value);
    }
}
