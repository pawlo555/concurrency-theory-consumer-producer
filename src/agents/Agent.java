package agents;

import Observers.UsersObserver;
import monitors.Monitor;

import java.util.Random;

public abstract class Agent implements Runnable {
    private static int NextId = 0;

    protected final UsersObserver observer;
    protected final Monitor monitor;
    protected final int id;
    private final Random generator;
    private final int maxRandNumber;

    public Agent(Monitor monitor, UsersObserver observer, int maxRandNumber) {
        this.monitor = monitor;
        this.observer = observer;
        this.id = getId();
        generator = new Random(id);
        this.maxRandNumber = maxRandNumber;
        observer.addUser(id);
    }

    private static int getId() {
        NextId = NextId + 1;
        return NextId - 1;
    }

    protected int randNumber() {
        return Math.abs(generator.nextInt()) % maxRandNumber + 1;
    }
}
