package gh.hack.data.impl;

import gh.hack.data.dao.GithubInteractionDao;
import io.vertx.core.Future;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Component
public class GithubInteractionMapImpl implements GithubInteractionDao {

    Map<String, Map<String, String>> mapDb;

    @PostConstruct
    public void init() {
        mapDb = new HashMap<>();
    }

    @Override
    public Future<Void> subscribe(String username, String repoToken, String bulbId) {
        Future<Void> future = Future.future();
        if(mapDb.get(repoToken) != null) {
            mapDb.get(repoToken).put(username, bulbId);
            future.complete();
        } else {
            Map<String, String> usernameBulbMap = new HashMap<>();
            usernameBulbMap.put(username, bulbId);
            mapDb.put(repoToken, usernameBulbMap);
            future.complete();
        }
        return future;
    }

    @Override
    public Future<String> getBulbId(String username, String repoToken) {
        Future<String> future = Future.future();
        Map<String, String> usernameBulbMap = mapDb.get(repoToken);
        if(repoToken == null) {
            future.complete(null);
        } else {
            future.complete(usernameBulbMap.get(username));
        }

        return  future;
    }
}
