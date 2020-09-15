package gh.hack.rest.verticles;

import gh.hack.rest.handlers.EventListenHandler;
import gh.hack.rest.handlers.GithubRepoSubsribeHandler;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpVerticle extends AbstractVerticle {
    private Logger logger = LoggerFactory.getLogger(HttpVerticle.class);

    @Autowired
    private GithubRepoSubsribeHandler githubRepoSubsribeHandler;

    @Autowired
    private EventListenHandler eventListenHandler;


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        startWebApp(new Handler<AsyncResult<HttpServer>>() {
            @Override
            public void handle(AsyncResult<HttpServer> httpServerAsyncResult) {
                completeStartup(httpServerAsyncResult, startFuture);
            }
        });
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        Router router = createRouter();
        HttpServerOptions options = new HttpServerOptions();
        options.setTcpKeepAlive(true);
        vertx.createHttpServer(options).requestHandler(router::accept).exceptionHandler(
                event -> {
                    logger.error("Exception for request: error: {}", event);
                }
        ).listen(
                config().getInteger("event.http.port", 8888), next
        );
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
        if (http.succeeded()) {
            logger.info("Application is up");
            fut.complete();
        } else {
            fut.fail(http.cause());
        }
    }

    private Router createRouter() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        getHealthCheck(router);
        subscribeToRepo(router);
        getEventFromGithubAction(router);
        return router;
    }

    private void getHealthCheck(Router router) {
        router.get("/health").handler(routingContext -> routingContext.response().setStatusCode(HttpResponseStatus.
                OK.code()).end("{\"health\":\"true\"}"));
    }

    private void subscribeToRepo(Router router) {
        router.post("/github/subscribe").handler(githubRepoSubsribeHandler);
    }

    private  void getEventFromGithubAction(Router router) {
        router.get("/github/event").handler(eventListenHandler);
    }

}