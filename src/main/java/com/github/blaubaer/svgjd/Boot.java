package com.github.blaubaer.svgjd;

import static java.util.Arrays.copyOfRange;

public class Boot {

    public static void main(String[] args) throws Exception {
        if (args.length <= 0) {
            printUsage();
            return;
        }
        switch (args[0]) {
            case "server":
                Application.run(copyOfRange(args, 1, args.length));
                break;
            case "benchmark":
                new Benchmark().run(copyOfRange(args, 1, args.length));
                break;
            default:
                printUsage();
                break;
        }
    }

    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "CallToSystemExit"})
    private static void printUsage() {
        System.out.print("Usage: java -jar <this jar> <command> [command specific arguments]" +
                "\nCommands:" +
                "\n\tserver    - Starts the gRPC (on port 8090) and REST (on port 8080) server" +
                "\n\tbenchmark - Run the gRPC vs REST benchmark and return results" +
                "\n");
        System.exit(1);
    }

}