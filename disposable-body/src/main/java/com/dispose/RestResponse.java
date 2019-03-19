package com.dispose;

import reactor.core.publisher.Mono;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RestResponse implements Closeable {
    private boolean isClosed;

    public Map<String, String> headers() {
        return new HashMap<>();
    }

    public Mono<String> body() {
        if (isClosed) {
            throw new RuntimeException("channel closed.");
        }
        return Mono.just("hello from api");
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
        System.out.println("Closed");
    }
}
