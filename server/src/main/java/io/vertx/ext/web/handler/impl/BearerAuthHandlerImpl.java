package io.vertx.ext.web.handler.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authentication.Credentials;
import io.vertx.ext.auth.authentication.TokenCredentials;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BasicAuthHandler;

public class BearerAuthHandlerImpl extends HTTPAuthorizationHandler<AuthenticationProvider> implements BasicAuthHandler {

    public BearerAuthHandlerImpl(AuthenticationProvider authProvider, String realm) {
        super(authProvider, realm, Type.BEARER);
    }

    @Override
    public void parseCredentials(RoutingContext context, Handler<AsyncResult<Credentials>> handler) {

        parseAuthorization(context, false, parseAuthorization -> {
            if (parseAuthorization.failed()) {
                handler.handle(Future.failedFuture(parseAuthorization.cause()));
                return;
            }
            handler.handle(Future.succeededFuture(new TokenCredentials(parseAuthorization.result())));
        });
    }
}

