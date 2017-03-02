package com.github.blaubaer.svgjd;

import com.github.blaubaer.svgjd.grpc.HelloClient;
import com.github.blaubaer.svgjd.grpc.HelloRequest;
import com.github.blaubaer.svgjd.swagger.client.DefaultApi;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.concurrent.Callable;

import static java.math.BigInteger.valueOf;
import static java.util.Locale.US;

public class Benchmark {

    private static final Logger LOG = LoggerFactory.getLogger(Benchmark.class);
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(US);
    private static final BigInteger ZERO = valueOf(0);

    public void run(String... args) throws Exception {
        try (final ConfigurableApplicationContext context = Application.run(args)) {
            final HelloClient grpcClient = context.getBean(HelloClient.class);
            final DefaultApi swaggerClient = context.getBean(DefaultApi.class);

            runWarmUp(grpcClient, swaggerClient);
            final Pair<Statistic, Statistic> benchmarks = runBenchmark(grpcClient, swaggerClient);

            final Statistic grpcStatistics = benchmarks.getKey();
            final Statistic swaggerStatistics = benchmarks.getValue();
            LOG.info("gRPC:    {}", grpcStatistics);
            LOG.info("Swagger: {}", swaggerStatistics);
            if (grpcStatistics.totalDuration < swaggerStatistics.totalDuration) {
                LOG.info("gRPC was {} times faster than Swagger", NUMBER_FORMAT.format((double) swaggerStatistics.totalDuration / (double) grpcStatistics.totalDuration));
            } else {
                LOG.info("Swagger was {} times faster than gRPC", NUMBER_FORMAT.format((double) grpcStatistics.totalDuration / (double) swaggerStatistics.totalDuration));
            }
        }
    }

    private Pair<Statistic, Statistic> runWarmUp(HelloClient grpcClient, DefaultApi swaggerClient) throws Exception {
        LOG.info("Running warm up...");
        return run(grpcClient, swaggerClient, 500);
    }

    private Pair<Statistic, Statistic> runBenchmark(HelloClient grpcClient, DefaultApi swaggerClient) throws Exception {
        LOG.info("Running benchmark...");
        return run(grpcClient, swaggerClient, 25000);
    }

    private Pair<Statistic, Statistic> run(HelloClient grpcClient, DefaultApi swaggerClient, long times) throws Exception {
        LOG.info("\tRunning {} times...", times);
        Statistic grpcStatistic = new Statistic();
        Statistic swaggerStatistic = new Statistic();
        for (long run = 0; run < times; run++) {
            final Pair<Long, Long> durations = runOne(grpcClient, swaggerClient);
            grpcStatistic = grpcStatistic.addRun(durations.getKey());
            swaggerStatistic = swaggerStatistic.addRun(durations.getValue());
        }
        return new Pair<>(grpcStatistic, swaggerStatistic);
    }

    private Pair<Long, Long> runOne(HelloClient grpcClient, DefaultApi swaggerClient) throws Exception {
        final Long grpcDuration = runOne("Hello grpc!", () -> {
            final HelloRequest request = HelloRequest.newBuilder()
                    .setName("grpc")
                    .build();
            return grpcClient.hello(request).getContent();
        });
        final Long swaggerDuration = runOne("Hello swagger!", () -> swaggerClient.helloGet("swagger").getContent());
        return new Pair<>(grpcDuration, swaggerDuration);
    }

    private long runOne(String expectedResult, Callable<String> what) throws Exception {
        final long start = System.nanoTime();
        final String actual = what.call();
        final long end = System.nanoTime();
        if (!expectedResult.equals(actual)) {
            throw new IllegalStateException("Got illegal response. Expected: <" + expectedResult + "> but got <" + actual + ">.");
        }
        return end - start;
    }

    private static class Statistic {
        private final long totalDuration;
        private final long numberOfRuns;

        private Statistic() {
            this(0, 0);
        }

        private Statistic(long totalDuration, long numberOfRuns) {
            this.totalDuration = totalDuration;
            this.numberOfRuns = numberOfRuns;
        }

        private Statistic addRun(long duration) {
            return new Statistic(
                    totalDuration + duration,
                    numberOfRuns + 1
            );
        }

        @Override
        public String toString() {
            final double avg = numberOfRuns == 0 ? 0.0d : (double) totalDuration / (double) numberOfRuns;
            return "avg: " + NUMBER_FORMAT.format(avg) + "ns"
                    + " / total: " + NUMBER_FORMAT.format(totalDuration) + "ns"
                    + " / runs: " + NUMBER_FORMAT.format(numberOfRuns) + "ns"
                    ;
        }
    }
}