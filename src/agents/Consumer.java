package agents;

import Observers.UsersObserver;
import monitors.Monitor;

import java.io.IOException;

public class Consumer extends Agent {

    public Consumer(Monitor monitor, UsersObserver observer, int maxRandNumber) {
        super(monitor, observer, maxRandNumber);
    }

    @Override
    public void run()  {
        while (true) {
            try {
                monitor.consume(id, randNumber());
                observer.normalWorkDone(id);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}