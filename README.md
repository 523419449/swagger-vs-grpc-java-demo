# Swagger vs gRPC Java demo

Demo project which demonstrates Swagger vs gRPC in Java.

## Usage

### Build and run benchmark

Execute in your shell see on console benchmark results:
```bash
./mvnw spring-boot:run -Drun.arguments=benchmark
```

### Build and run server

Execute in your shell and use the server with gRPC on port `8090` and REST on port `8080`:
```bash
./mvnw spring-boot:run -Drun.arguments=server
```
