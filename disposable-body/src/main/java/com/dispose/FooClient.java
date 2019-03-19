package com.dispose;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.function.Function;

public class FooClient {
    public <T> Mono<T> createFoo(String name, Function<RestResponse, Mono<T>> function) {
        return
                innerCreateFoo(name).flatMap(restResponse1 -> {
                    Mono<T> user = function.apply(restResponse1);
                    return user.doFinally(t -> {
                        try {
                            System.out.println("Disposing: (" + name + ")");
                            restResponse1.close();
                        } catch (IOException ioe) {
                            throw Exceptions.propagate(ioe);
                        }
                    });
                });
    }

    // Actual API call through Proxy
    private Mono<RestResponse> innerCreateFoo(String name) {
        // The API call
        Mono<RestResponse> mi = Mono.defer(() -> {
            System.out.println("invoking API to create'" + name + "'");
            return  Mono.just(new RestResponse());
        });
        return mi;
    }
}