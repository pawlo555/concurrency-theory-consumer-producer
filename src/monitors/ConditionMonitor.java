package monitors;

import utils.AgentIdMemorizer;
import utils.Buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionMonitor extends Monitor {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition producerCondition = lock.newCondition();
    private final Condition consumerCondition = lock.newCondition();
    private final Condition firstConsumer = lock.newCondition();
    private final Condition firstProducer = lock.newCondition();

    private final AgentIdMemorizer consumerMemorizer = new AgentIdMemorizer();
    private final AgentIdMemorizer producerMemorizer = new AgentIdMemorizer();

    public ConditionMonitor(Buffer buffer) {
        super(buffer);
    }

    public void produce(int threadId, int producedResources) throws InterruptedException {
        lock.lock();
        System.out.println("\tProducer: " + threadId);
        while (producerMemorizer.checkId(threadId)) { //
        //while (lock.hasWaiters(firstProducer)) {
            System.out.println("Producer first lock: " + lock.getWaitQueueLength(firstProducer));
            System.out.println("Not first producer: " + threadId);
            System.out.println("Buffer: " + buffer);
            producerCondition.await();
        }
        while (buffer.toLittleSpace(producedResources)) {
            System.out.println("Producer first lock: " + lock.getWaitQueueLength(firstProducer));
            System.out.println("First producer: " + threadId);
            System.out.println("Buffer: " + buffer);
            producerMemorizer.memorizeId(threadId);
            firstProducer.await();
        }
        buffer.increment(producedResources);
        producerMemorizer.forgetId();
        System.out.println("Produce end: " + threadId);
        System.out.println("Buffer: " + buffer);
        producerCondition.signal();
        firstConsumer.signal();
        lock.unlock();
    }

    public void consume(int threadId, int consumerResources) throws InterruptedException {
        lock.lock();
        System.out.println("\tConsumer: " + threadId);
        while (lock.hasWaiters(firstConsumer)) { //
        //while (consumerMemorizer.checkId(threadId)) {
            System.out.println("Consumer first lock: " + lock.getWaitQueueLength(firstConsumer));
            System.out.println("Not first consumer: " + threadId);
            System.out.println("Buffer: " + buffer);
            consumerCondition.await();
        }
        while (buffer.toLittleResources(consumerResources)) {
            System.out.println("Consumer first lock: " + lock.getWaitQueueLength(firstConsumer));
            System.out.println("First consumer: " + threadId);
            System.out.println("Buffer: " + buffer);
            consumerMemorizer.memorizeId(threadId);
            firstConsumer.await();
        }

        buffer.decrement(consumerResources);
        consumerMemorizer.forgetId();

        System.out.println("Consume end: " + threadId);
        System.out.println("Buffer: " + buffer);
        consumerCondition.signal();
        firstProducer.signal();
        lock.unlock();
    }
}