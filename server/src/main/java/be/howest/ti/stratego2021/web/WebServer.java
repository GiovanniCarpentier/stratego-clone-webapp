package be.howest.ti.stratego2021.web;

import be.howest.ti.stratego2021.web.bridge.StrategoBridge;
import io.vertx.config.ConfigRetriever;
import io.vertx.core.*;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WebServer is the main Verticle:
 *  - loads configuration
 *  - loads api specification
 *  - starts an http-webserver.
 *
 *  No need to update anything in this class !!!
 */
public class WebServer extends AbstractVerticle {

    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());

    private Promise<Void> startPromise;

    public Future<Void> started() {
        return startPromise.future();
    }

    private final StrategoBridge bridge;

    public WebServer(StrategoBridge bridge) {
        this.bridge = bridge;
    }

    public WebServer() {
        this(new StrategoBridge());
    }

    @Override
    public void start(Promise<Void> startPromise) {
        LOGGER.log(Level.INFO, "Starting server ...");
        this.startPromise = startPromise;
        ConfigRetriever.create(vertx).getConfig()
                .onFailure(cause -> shutDown("Failed to load configuration", cause))
                .onSuccess(configuration -> {
                    LOGGER.log(Level.INFO, "Configuration loaded: {0}", configuration);
                    final int port = configuration.getInteger("webserver-port");

                    LOGGER.log(Level.INFO, "Configuration loaded");
                    LOGGER.log(Level.INFO, "Server will be listening at port {0}", port);

                    RouterBuilder.create(vertx, "./stratego-api.yaml")
                            .onFailure(cause -> shutDown("Failed to load API specification", cause))
                            .onSuccess(routerBuilder -> {
                                LOGGER.log(Level.INFO, "API specification loaded: {0}", routerBuilder.getOpenAPI().getOpenAPI().getJsonObject("info").getString("version"));
                                vertx.createHttpServer()
                                        .requestHandler(bridge.buildRouter(routerBuilder))
                                        .listen(port)
                                        .onFailure(cause -> shutDown("Failed to start server", cause))
                                        .onSuccess(server -> {
                                            LOGGER.log(Level.INFO, "Server is listening on port: {0}", server.actualPort());
                                            startPromise.complete();
                                        });
                            });


                });
    }

    private void shutDown(String message, Throwable cause) {
        LOGGER.log(Level.SEVERE, message, cause);
        LOGGER.info("Shutting down");
        vertx.close();
        startPromise.fail(cause);
    }

}
