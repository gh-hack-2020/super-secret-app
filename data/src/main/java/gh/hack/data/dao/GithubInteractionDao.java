package gh.hack.data.dao;

import org.springframework.stereotype.Component;

@Component
public interface GithubInteractionDao {
    public void subscribe(String username, String repoToken, String bulbId) ;

    public String getBulbId(String username, String repoToken);
}
