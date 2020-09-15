package gh.hack.rest.handlers;

import gh.hack.service.EventListenService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListenHandler implements Handler<RoutingContext> {

    @Autowired
    private EventListenService eventListenService;

    @Override
    public void handle(RoutingContext event) {
        MultiMap multiMap = event.queryParams();
        String repoId = multiMap.get("repoId");

        eventListenService.consumeEvent(repoId).setHandler(handler -> {
            if(handler.succeeded()) {
                event.response().setStatusCode(HttpResponseStatus.OK.code()).end();
            } else {
                event.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
            }
        });

    }
}
