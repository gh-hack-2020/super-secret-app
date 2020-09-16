package gh.hack.service;

import gh.hack.data.dao.GithubInteractionDao;
import gh.hack.data.external.api.calls.LifxApiCaller;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventListenService {

    @Autowired
    private GithubInteractionDao githubInteractionDao;

    @Autowired
    private LifxApiCaller lifxApiCaller;

    public Future<List<String>> consumeEvent(String repoId) {
        Future<List<String>> future = Future.future();

        githubInteractionDao.getBulbId(repoId).setHandler(handler -> {
           if(handler.succeeded()) {
               List<String> bulbIds = handler.result();
               List<Future> glowBulbFutureList = new ArrayList<>();
               if(bulbIds == null) {
                   future.complete(new ArrayList<>());
               } else {
                   for (String bulbId : bulbIds) {
                       glowBulbFutureList.add(lifxApiCaller.glowBulb(bulbId));
                   }
                   CompositeFuture.all(glowBulbFutureList).setHandler(glowBulbFuturesHandler -> {
                       if (glowBulbFuturesHandler.succeeded()) {
                           future.complete(bulbIds);
                       } else {
                           future.fail(glowBulbFuturesHandler.cause());
                       }
                   });
               }
           } else {
               future.fail(handler.cause());
           }
        });

        return future;
    }
}
