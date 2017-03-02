package com.github.blaubaer.svgjd.grpc;

import com.github.blaubaer.svgjd.grpc.GreeterGrpc.GreeterBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;

@Service
public class HelloClient {

    private final ManagedChannel channel;
    private final GreeterBlockingStub blockingStub;

    public HelloClient() {
        channel = ManagedChannelBuilder.forAddress("localhost", 8090)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext(true)
                .build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public HelloMessage hello(HelloRequest req) {
        return blockingStub.hello(req);
    }

}
