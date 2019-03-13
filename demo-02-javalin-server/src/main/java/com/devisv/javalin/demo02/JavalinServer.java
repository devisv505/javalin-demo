package com.devisv.javalin.demo02;

import io.javalin.Javalin;

import java.util.concurrent.CompletableFuture;

public class JavalinServer {

    private static final Javalin server = Javalin.create();

    public static void start() {
        server.get("/status", ctx ->
                ctx.result(CompletableFuture.supplyAsync(() -> "{ \"status\": \"ok\"}"))
                   .contentType("application/json")
        );

        server.requestLogger((ctx, executionTimeMs) ->
                System.out.println(ctx.method() + " " + ctx.path() + " took " + executionTimeMs + " ms")
        );

        server.start(9000);
    }

    public static void stop() {
        server.stop();
    }
}
