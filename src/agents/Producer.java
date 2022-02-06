package agents;

import Observers.UsersObserver;
import monitors.Monitor;

import java.io.IOException;

public class Producer extends Agent {

    public Producer(Monitor monitor, UsersObserver observer, int maxRandNumber) {
        super(monitor, observer, maxRandNumber);
    }

    @Override
    public void run()  {
        while (true) {
            try {
                monitor.produce(id, randNumber());
                observer.normalWorkDone(id);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}