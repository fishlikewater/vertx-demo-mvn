package io.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;

public class MySeconedVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.route().failureHandler(ErrorHandler.create());
        router.get("/test").handler(ctx -> {
            throw new RuntimeException("aaaaaaaaa");
           /* HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain")
                    .end("HELLO WORD")*/});
      server.requestHandler(router::accept).listen(9000);
    }
}
