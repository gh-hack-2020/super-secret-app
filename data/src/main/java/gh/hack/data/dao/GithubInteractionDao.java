package gh.hack.data.dao;

import io.vertx.core.Future;
import org.springframework.stereotype.Component;

@Component
public interface GithubInteractionDao {
    public Future<Void> subscribe(String username, String repoToken, String bulbId) ;

    public Future<String> getBulbId(String username, String repoToken);
}
