package monitors;

import utils.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleMonitor extends Monitor {

    public SimpleMonitor(Buffer buffer) {
        super(buffer);
    }

    Lock lock = new ReentrantLock(true);
    Condition producerCondition = lock.newCondition();
    Condition consumerCondition = lock.newCondition();

    public void produce(int threadId, int producedResources) {
        lock.lock();
        while (buffer.toLittleSpace(producedResources)) {
            try {
                producerCondition.await();
            }
            catch (InterruptedException ignored) {}
        }
        buffer.increment(producedResources);
        consumerCondition.signal();

        lock.unlock();
    }

    public void consume(int threadId, int consumerResources) {
        lock.lock();
        while (buffer.toLittleResources(consumerResources)) {
            try {
                consumerCondition.await();
            }
            catch (InterruptedException ignored) {}
        }
        buffer.decrement(consumerResources);
        producerCondition.signal();
        lock.unlock();
    }
}
