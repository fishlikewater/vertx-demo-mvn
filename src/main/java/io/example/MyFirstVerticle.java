package io.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.shiro.ShiroAuth;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class MyFirstVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
      /*  router.route("/test").handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain")
                    .end("HELLO WORD");
        });
        router.route().handler(BodyHandler.create());
        router.post("/upload").handler(ctx -> {
            Set<FileUpload> fileUploads = ctx.fileUploads();
            fileUploads.stream().forEach(it -> it.name());

        });*/
        JsonObject config = new JsonObject().put("properties_path", "classpath:test-auth.properties");

        AuthProvider authProvider = ShiroAuth.create(vertx, ShiroAuthRealmType.PROPERTIES, config);
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
        router.route().handler(UserSessionHandler.create(authProvider));
        AuthHandler basicAuthHandler = BasicAuthHandler.create(authProvider);
       /* vertx.createHttpServer().requestHandler(req -> {
           req.response()
                   .putHeader("content-type", "text/plain")
                   .end("HELLO WORD");
        }).listen(9000);*/
        server.requestHandler(router::accept).listen(9000);
    }
}
