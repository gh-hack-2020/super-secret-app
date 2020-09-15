package gh.hack.data.impl;

import gh.hack.data.dao.GithubInteractionDao;
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
    public void subscribe(String username, String repoToken, String bulbId) {
        if(mapDb.get(repoToken) != null) {
            mapDb.get(repoToken).put(username, bulbId);
        } else {
            Map<String, String> usernameBulbMap = new HashMap<>();
            usernameBulbMap.put(username, bulbId);
            mapDb.put(repoToken, usernameBulbMap);
        }
    }

    @Override
    public String getBulbId(String username, String repoToken) {
        Map<String, String> usernameBulbMap = mapDb.get(repoToken);
        if(repoToken == null) {
            return  null;
        } else {
            return usernameBulbMap.get(username);
        }
    }
}
