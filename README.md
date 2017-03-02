# Swagger vs gRPC Java demo

Demo project which demonstrates Swagger vs gRPC in Java.

## Usage

### Build and run benchmark

Execute in your shell see on console benchmark results:
```bash
./mvnw spring-boot:run -Drun.arguments=benchmark
```

Result on my system:
```log
2017-03-02 15:22:21.813  INFO 4988 --- [           main] com.github.blaubaer.svgjd.Benchmark      : Running warm up...
2017-03-02 15:22:21.821  INFO 4988 --- [           main] com.github.blaubaer.svgjd.Benchmark      :     Running 500 times...
2017-03-02 15:22:25.367  INFO 4988 --- [           main] com.github.blaubaer.svgjd.Benchmark      : Running benchmark...
2017-03-02 15:22:25.375  INFO 4988 --- [           main] com.github.blaubaer.svgjd.Benchmark      :     Running 25000 times...
2017-03-02 15:22:56.960  INFO 4988 --- [           main] com.github.blaubaer.svgjd.Benchmark      : gRPC:    avg: 573,361.965ns / total: 14,334,049,127ns / runs: 25,000ns
2017-03-02 15:22:56.968  INFO 4988 --- [           main] com.github.blaubaer.svgjd.Benchmark      : Swagger: avg: 683,145.314ns / total: 17,078,632,850ns / runs: 25,000ns
2017-03-02 15:22:56.970  INFO 4988 --- [           main] com.github.blaubaer.svgjd.Benchmark      : gRPC was 1.191 times faster than Swagger
```

### Build and run server

Execute in your shell and use the server with gRPC on port `8090` and REST on port `8080`:
```bash
./mvnw spring-boot:run -Drun.arguments=server
```
