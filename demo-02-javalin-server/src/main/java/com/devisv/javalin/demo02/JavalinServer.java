package com.devisv.javalin.demo02;

import io.javalin.Javalin;

import java.util.concurrent.CompletableFuture;

public class JavalinServer {

    private static final Javalin server = Javalin.create();

    public static void start() {
        server.get("/status", ctx -> {
                    ctx.json(
                            CompletableFuture.supplyAsync(() -> new StatusDao("ok"))
                    );
                }
        );

        server.start(9000);
    }

    public static void stop() {
        server.stop();
    }
}
