package gh.hack.data.dao;

import io.vertx.core.Future;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GithubInteractionDao {
    public Future<Void> subscribe(String username, String repoToken, String bulbId) ;

    public Future<List<String>> getBulbId(String repoToken);
}
