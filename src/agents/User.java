package agents;

import Observers.UsersObserver;
import monitors.Monitor;

import java.io.IOException;

import static java.lang.Thread.sleep;

public abstract class User extends Agent {
    private final static int NANOS_MAX = 1000000;

    private final int extraWorkTimeNanosPerUnit;
    private int extraWorkTimeNanos;
    private int extraWorkTimeMilli;

    public User(Monitor monitor, UsersObserver observer, int maxRandNumber, int extraWorkTimeNanos) {
        super(monitor, observer, maxRandNumber);
        this.observer.addUser(id);
        this.extraWorkTimeNanosPerUnit = extraWorkTimeNanos;
    }

    public void run() {
        while (true) {
            int value = randNumber();
            calcTimeOfExtraWork(value);
                try {
                    doNormalTask(value);
                    doExtraWork();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            observer.normalWorkDone(id);
        }
    }

    private void calcTimeOfExtraWork(int value) {
        int totalExtraWorkTime = value*extraWorkTimeNanosPerUnit;
        extraWorkTimeNanos = totalExtraWorkTime%NANOS_MAX;
        extraWorkTimeMilli = totalExtraWorkTime/NANOS_MAX;
    }


    public void doExtraWork() throws InterruptedException {
        sleep(extraWorkTimeMilli, extraWorkTimeNanos);
    }

    public abstract void doNormalTask(int value) throws IOException, InterruptedException;
}
