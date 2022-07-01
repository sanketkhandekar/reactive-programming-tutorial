package com.codeonlyocde.reactiveprogrammingtutorial.services;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxMonoServices {


    public Flux<String> fruitsFlux(){
       return  Flux.fromIterable(List.of("Mango","Orange","Banana")).log();
              // .delayElements(Duration.ofSeconds(1));
    }

    public Flux<String> fruitsFluxMap(){
        return  Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .map(String::toUpperCase)
                .log();

        // .delayElements(Duration.ofSeconds(1));
    }

    public Flux<String> fruitsFluxFilter(Integer length){
        return  Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .filter(s->s.length() > length)
                .log();

        // .delayElements(Duration.ofSeconds(1));
    }
    public Flux<Integer> fruitsFluxFilterMap(Integer length){
        return  Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .filter(s->s.length() > length)
                .map(String::length)
                .log();
        // .delayElements(Duration.ofSeconds(1));
    }

    public Flux<String> fruitsFluxFlatMap(){
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .flatMap(s->Flux.just(s.split("")))
                .log();
    }
    public Flux<String> fruitsFluxFlatMapAsync(){
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .flatMap(s->Flux.just(s.split(""))

                .delayElements(Duration.ofMillis(
                        new Random().nextInt(1000)
                )))
                .log();
    }
    public Flux<String> fruitsFluxConcatMapAsync(){
        return Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .concatMap(s->Flux.just(s.split(""))

                        .delayElements(Duration.ofMillis(
                                new Random().nextInt(1000)
                        )))
                .log();
    }
    public Flux<String> fruitsFluxFlatMapMany(){
        return Mono.just("Mango").
                flatMapMany(s->Flux.just(s.split("")))
                .log();
    }

    public Flux<String> fruitsFluxTransform(Integer length){
        Function<Flux<String>,Flux<String>> filterData = data -> data.filter(s->s.length() > length);

        return  Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .transform(filterData)
                .log();
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(Integer length){
        Function<Flux<String>,Flux<String>> filterData = data -> data.filter(s->s.length() > length);

        return  Flux.fromIterable(List.of("Mango","Orange","Banana"))
                .transform(filterData)
                .defaultIfEmpty("default")
                .log();
    }
    public Flux<String> fruitsFluxTransformSwitchIfEmpty(Integer length) {
        Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > length);

        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .transform(filterData)
                .switchIfEmpty(Flux.fromIterable(List.of("Pineapple", "Jack Fruit")))
                .transform(filterData)
                .defaultIfEmpty("default")
                .log();
    }
    public Flux<String> fruitsFluxConcat(){
        Flux<String> fruit = Flux.just("Mango", "Orange");
        Flux<String> veggies = Flux.just("Tomato", "Lemon");
        return Flux.concat(fruit,veggies)
                .log();
    }
    public Flux<String> fruitsFluxConcatWith(){
        Flux<String> fruit = Flux.just("Mango", "Orange");
        Flux<String> veggies = Flux.just("Tomato", "Lemon");
        return fruit.concatWith(veggies).log();
    }

    public Flux<String> fruitsMonoConcat(){
        Mono<String> mango = Mono.just("Mango");
        Mono<String> orange = Mono.just("Orange");

        return mango.concatWith(orange).log();
    }
    public Flux<String> fruitsFluxMerge(){
        Flux<String> fruit = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));
        return Flux.merge(fruit,veggies).log();
    }

    public Flux<String> fruitsFluxMergeWith(){
        Flux<String> fruit = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));
        return fruit.mergeWith(veggies).log();
    }

    public Flux<String> fruitsFluxMergeWithSequential(){
        Flux<String> fruit = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(50));
        Flux<String> veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(75));
        return Flux.mergeSequential(fruit,veggies).log();
    }

    public Flux<String> fruitsFluxZip(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        return Flux.zip(fruits,veggies,(first,second) -> first+second).log();
    }
    public Flux<String> fruitsFluxZipWith(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");

        return fruits.zipWith(veggies,(first,second) -> first+second).log();
    }
    public Flux<String> fruitsFluxZipWithTuple(){
        var fruits = Flux.just("Mango","Orange");
        var veggies = Flux.just("Tomato","Lemon");
        var moreVeggies = Flux.just("Potato","Cucumber");

        return Flux.zip(fruits,veggies,moreVeggies)
                .map(objects -> objects.getT1() + objects.getT2() + objects.getT3()).log();

    }

    public  Mono<String> fruitsZipMono(){
        var fruits = Mono.just("Mango");
        var veggies  = Mono.just("Tomato");
        return fruits.zipWith(veggies,(first,second)->first+second).log();

    }

    public  Flux<String> fruitsFluxOnErrorReturn(){
        var fruits = Flux.just("Apple","Mango");

        return fruits.concatWith(Flux.error(
                    new RuntimeException("Runtime Exception")
        )).onErrorReturn("Orange").log();
    }

    public  Flux<String> fruitsFluxOnErrorContinue(){
        var fruits = Flux.just("Apple","Mango","Orange");

        return fruits.map( s-> {
            if (s.equalsIgnoreCase("Mango"))
                throw new RuntimeException("Exception occurred");
            return s.toUpperCase();
        })
        .onErrorContinue((e,f) -> {
            System.out.println("e = " + e);
            System.out.println("f = " + f);
        }).log();

    }

    public  Flux<String> fruitsFluxOnErrorMap(){
        var fruits = Flux.just("Apple","Mango","Orange");
        return fruits
                .checkpoint("Error CheckPoint1")
                .map( s-> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred");
                    return s.toUpperCase();
                })
                .checkpoint("Error CheckPoint2")
                .onErrorMap(throwable -> {
                    System.out.println("throwable = " + throwable);

                    return new IllegalStateException("From OnError Map");

                }).log();
    }

    public  Flux<String> fruitsFluxDoOnError(){
        var fruits = Flux.just("Apple","Mango","Orange");
        return fruits.map( s-> {
                    if (s.equalsIgnoreCase("Mango"))
                        throw new RuntimeException("Exception occurred");
                    return s.toUpperCase();
                })
                .doOnError(throwable -> {
                    System.out.println("throwable = " + throwable);
                }).log();
    }

    public  Mono<String> fruitsMono(){
        return Mono.just("Mango").log();
    }
    public Mono<List<String>> fruitsMonoFlatMap(){
        return Mono.just("Mango").
                flatMap(s->Mono.just(List.of(s.split(""))))
                .log();
    }
    public Flux<String> fruitsFluxFilterDoOn(Integer length) {
        return Flux.fromIterable(List.of("Mango", "Orange", "Banana"))
                .filter(s -> s.length() > length)
                .doOnNext(s -> {
                    System.out.println(" s " + s);
                })
                .doOnSubscribe(subscription -> {
                    System.out.println("subscription = " + subscription);
                })
                .doOnComplete(() -> System.out.println("Completed!!"))
                .log();

        // .delayElements(Duration.ofSeconds(1));
    }




    public static  void main (String [] args) throws Exception{
        FluxMonoServices fluxMonServices =
                new FluxMonoServices();
        fluxMonServices.fruitsFlux().
                subscribe( s ->{
                    System.out.println("  s = "+s);
                });


        fluxMonServices.fruitsMono().subscribe(
                    s-> {
                        System.out.println("Mono -> s = "+s);
                    }
        );
        //System.out.println("Press a key to end");
       // System.in.read();
    }
}
