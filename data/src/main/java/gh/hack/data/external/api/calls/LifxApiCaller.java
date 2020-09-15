package gh.hack.data.external.api.calls;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LifxApiCaller {
    private WebClient webClient;

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

        return  future;
    }
}
