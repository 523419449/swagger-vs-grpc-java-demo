package com.github.blaubaer.svgjd.grpc;

import com.github.blaubaer.svgjd.grpc.GreeterGrpc.GreeterImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class HelloServer {

    private static final Logger LOG = LoggerFactory.getLogger(HelloServer.class);

    private Server server;

    @PostConstruct
    private void init() throws IOException {
        final int port = 8090;
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start()
        ;
    }

    @PreDestroy
    public void close() throws Exception {
        if (server != null) {
            server.shutdown();
        }
    }

    private static class GreeterImpl extends GreeterImplBase {

        @Override
        public void hello(HelloRequest request, StreamObserver<HelloMessage> responseObserver) {
            responseObserver.onNext(HelloMessage.newBuilder()
                    .setContent("Hello " + request.getName() + "!")
                    .build());
            responseObserver.onCompleted();
        }
    }
}
