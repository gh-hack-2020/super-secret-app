package gh.hack.rest.handlers;

import gh.hack.rest.verticles.HttpVerticle;
import gh.hack.service.EventListenService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventListenHandler implements Handler<RoutingContext> {

    private Logger logger = LoggerFactory.getLogger(EventListenService.class);

    @Autowired
    private EventListenService eventListenService;

    @Override
    public void handle(RoutingContext event) {
        MultiMap multiMap = event.queryParams();
        String repoId = multiMap.get("repoId");

        logger.info("event from repoId " + repoId);

        eventListenService.consumeEvent(repoId).setHandler(handler -> {
            if(handler.succeeded()) {
                logger.info("event from repoId " + repoId + " SUCCESS. Bulb interation with " + handler.result().toString());
                event.response().setStatusCode(HttpResponseStatus.OK.code()).end(handler.result().toString());
            } else {
                logger.info("event from repoId " + repoId + "FAILED");
                event.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
            }
        });

    }
}
