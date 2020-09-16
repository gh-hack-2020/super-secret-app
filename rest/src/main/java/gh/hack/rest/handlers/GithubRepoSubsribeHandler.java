package gh.hack.rest.handlers;

import gh.hack.rest.verticles.HttpVerticle;
import gh.hack.service.GithubRepoSubscribeService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GithubRepoSubsribeHandler implements Handler<RoutingContext> {

    private Logger logger = LoggerFactory.getLogger(GithubRepoSubsribeHandler.class);

    @Autowired
    private GithubRepoSubscribeService githubRepoSubsribeService;

    @Override
    public void handle(RoutingContext event) {

        JsonObject jsonObject = event.getBodyAsJson();
        String repoId = jsonObject.getString("repoId");
        String bulbId = jsonObject.getString("bulbId");
        String username = jsonObject.getString("username");


        logger.info("Request for " + username + " to subscribe to " + repoId + " and bulb-interaction is with " + bulbId);

        githubRepoSubsribeService.subscribe(repoId, username, bulbId).setHandler(handler -> {
           if(handler.succeeded()) {
               logger.info("Request for " + username + " to subscribe to " + repoId + " and bulb-interaction is with " +
                       bulbId + " SUCCESS");
               event.response().setStatusCode(HttpResponseStatus.OK.code()).end();
           } else {
               logger.error("Request for " + username + " to subscribe to " + repoId + " and bulb-interaction is with "
                       + bulbId + " FAILED", handler.cause());
               event.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
           }
        });

    }
}
