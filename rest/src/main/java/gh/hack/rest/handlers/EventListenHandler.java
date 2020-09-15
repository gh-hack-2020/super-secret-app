package gh.hack.rest.handlers;

import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class EventListenHandler implements Handler<RoutingContext> {
    @Override
    public void handle(RoutingContext event) {
        MultiMap multiMap = event.queryParams();
        String repoId = multiMap.get("repoId");


    }
}
