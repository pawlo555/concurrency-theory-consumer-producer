package monitors;

import utils.Buffer;

import java.io.IOException;

public abstract class Monitor {
    protected final Buffer buffer;

    public Monitor(Buffer buffer) {
        this.buffer = buffer;
    }

    public abstract void consume(int threadId, int consumeValue) throws IOException, InterruptedException;

    public abstract void produce(int threadId, int producedResources) throws IOException, InterruptedException;

}
