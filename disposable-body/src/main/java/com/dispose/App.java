package com.dispose;

import reactor.core.publisher.Mono;
import java.util.Map;

public class App {
    public static void main( String[] args ) {
        FooClient client = new FooClient();
        //
        Mono<Map<String, String>> r1 =  client.createFoo("A", response -> Mono.just(response.headers()));
        //
        Mono<String> r3  = r1.flatMap(headers -> {
            Mono<Map<String, String>> r2 = client.createFoo("B", response -> Mono.just(response.headers()));
            return r2;
        }).flatMap(headers -> client.createFoo("C", response -> response.body()));
        //
        String rr4 = r3.block();
        System.out.println(rr4);
    }
}
