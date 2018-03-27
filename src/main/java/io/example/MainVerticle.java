package io.example;

import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        vertx.getOrCreateContext().runOnContext(res -> {

        });
        vertx.deployVerticle(MyFirstVerticle.class.getName(), res -> {
            if(res.succeeded()){
                String id = res.result();
                System.out.println("success");
            }else{
                System.out.println("failed");
            }
        });
    }
}
