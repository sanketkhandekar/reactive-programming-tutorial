package com.codeonlyocde.reactiveprogrammingtutorial.services;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdStreamTest {

    @Test
    public void colSteamTest(){
        var numbers = Flux.range(1,10);

        numbers.subscribe(integer -> System.out.println("integer 1= " + integer));

        numbers.subscribe(integer -> System.out.println("integer  2= " + integer));

    }


    @Test
    @SneakyThrows
    public void hotStreamTest(){
        var numbers = Flux.range(1,10)
                .delayElements(Duration.ofMillis(1000));

        ConnectableFlux<Integer> publish = numbers.publish();

        publish.connect();

        publish.subscribe(integer -> System.out.println("subscribe 1= " + integer));
        Thread.sleep(4000);

        publish.subscribe(integer -> System.out.println("subscribe  2= " + integer));
        Thread.sleep(10000);



    }
}
