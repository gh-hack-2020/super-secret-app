package gh.hack.data.impl;

import gh.hack.data.dao.GithubInteractionDao;
import io.vertx.core.Future;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public Future<List<String>> getBulbId(String repoToken) {
        Future<List<String>> future = Future.future();
        Map<String, String> usernameBulbMap = mapDb.get(repoToken);
        if(usernameBulbMap == null) {
            future.complete(null);
        } else {
            List<String> bulbIds = new ArrayList<>();
            for(String username : usernameBulbMap.keySet()) {
                bulbIds.add(usernameBulbMap.get(username));
            }
            future.complete(bulbIds);
        }

        return  future;
    }
}
