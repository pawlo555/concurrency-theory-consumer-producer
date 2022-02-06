package monitors;

import utils.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockMonitor extends Monitor {
    private final ReentrantLock consumerLock = new ReentrantLock();
    private final ReentrantLock producerLock = new ReentrantLock();
    private final ReentrantLock accessLock = new ReentrantLock();
    private final Condition accessCondition = accessLock.newCondition();

    public LockMonitor(Buffer buffer) {
        super(buffer);
    }

    public void produce(int threadId, int producedResources) throws InterruptedException {
        producerLock.lock();
        accessLock.lock();
        while (buffer.toLittleSpace(producedResources)) {
            accessCondition.await();
        }

        buffer.increment(producedResources);
        accessCondition.signal();
        accessLock.unlock();
        producerLock.unlock();
    }

    public void consume(int threadId, int consumerResources) throws InterruptedException {
        consumerLock.lock();
        accessLock.lock();
        while (buffer.toLittleResources(consumerResources)) {
            accessCondition.await();
        }

        accessCondition.signal();
        buffer.decrement(consumerResources);
        accessLock.unlock();
        consumerLock.unlock();

    }
}
