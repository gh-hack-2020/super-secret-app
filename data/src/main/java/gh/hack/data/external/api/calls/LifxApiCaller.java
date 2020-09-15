package gh.hack.data.external.api.calls;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LifxApiCaller {
    private WebClient webClient;

    private String authKey = "c2062372de4c5d007070fb7ded6e935c3b7e0c60acf2029a9fee39eb10c9fe5e";

    @PostConstruct
    public void init() {
        webClient = WebClient.create(
                Vertx.vertx(),
                new WebClientOptions()
                .setDefaultHost("api.lifx.com")
                .setSsl(true)
                .setMaxPoolSize(30)
                .setKeepAlive(true)
        );
    }

    public Future<Void> glowBulb(String bulbId) {
        Future<Void> future = Future.future();
        //Ref: https://api.lifx.com/v1/lights/:selector/state

        JsonObject bodyJson = new JsonObject()
                .put("power", "on")
                .put("color", "red");

        webClient.put("/v1/lights/" + bulbId + "/state")
                .putHeader("Authorization", authKey)
                .sendJsonObject(bodyJson, handler -> {
                    if(handler.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(handler.cause());
                    }
                });

        return  future;
    }
}
