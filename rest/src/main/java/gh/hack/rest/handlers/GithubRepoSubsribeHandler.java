package gh.hack.rest.handlers;

import gh.hack.service.GithubRepoSubscribeService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GithubRepoSubsribeHandler implements Handler<RoutingContext> {

    @Autowired
    private GithubRepoSubscribeService githubRepoSubsribeService;

    @Override
    public void handle(RoutingContext event) {

        JsonObject jsonObject = event.getBodyAsJson();
        String repoId = jsonObject.getString("repoId");
        String bulbId = jsonObject.getString("bulbId");
        String username = jsonObject.getString("username");


        githubRepoSubsribeService.subscribe(repoId, username, bulbId).setHandler(handler -> {
           if(handler.succeeded()) {
               event.response().setStatusCode(HttpResponseStatus.OK.code()).end();
           } else {
               event.response().setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).end();
           }
        });

    }
}
