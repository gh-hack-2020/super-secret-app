package gh.hack.service;

import gh.hack.data.dao.GithubInteractionDao;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GithubRepoSubscribeService {

    @Autowired
    private GithubInteractionDao githubInteractionDao;

    public Future<Void> subscribe(String repoToken, String username, String bulbId) {
        Future<Void> future = Future.future();

        githubInteractionDao.subscribe(username, repoToken, bulbId).setHandler(handler->{
           if(handler.succeeded()) {
               future.complete();
           } else {
               future.fail(handler.cause());
           }
        });

        return future;
    }
}
