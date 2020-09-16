package gh.hack.rest.verticles;

import gh.hack.rest.spring.SpringConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainVerticle extends AbstractVerticle {
    Logger logger= LoggerFactory.getLogger(MainVerticle.class);
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        logger.info("deploying application verticles");
        vertx.deployVerticle(applicationContext.getBean(HttpVerticle.class));
    }
}