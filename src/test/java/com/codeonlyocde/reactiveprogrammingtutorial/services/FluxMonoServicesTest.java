package com.codeonlyocde.reactiveprogrammingtutorial.services;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;
import reactor.tools.agent.ReactorDebugAgent;

import static org.junit.jupiter.api.Assertions.*;

class FluxMonoServicesTest {

    FluxMonoServices fluxMonoServices
            = new FluxMonoServices();

    @Test
    void fruitsFlux() {
        var fruitsFlux = fluxMonoServices.fruitsFlux();

        StepVerifier.create(fruitsFlux)
                .expectNext("Mango","Orange","Banana")
                .verifyComplete();

    }

    @Test
    void fruitsMono() {
        var fruitMono  = fluxMonoServices.fruitsMono();

        StepVerifier.create(fruitMono)
                .expectNext("Mango")
                .verifyComplete();
    }

    void fruitsMonoError() {
        var fruitMono  = fluxMonoServices.fruitsMono();

        StepVerifier.create(fruitMono)
                .expectNext("Mangoa")
                .verifyError();
    }

    @Test
    void fruitsFluxMap() {
        var fruitsFlux = fluxMonoServices.fruitsFluxMap();
        StepVerifier.create(fruitsFlux)
                .expectNext("MANGO","ORANGE","BANANA")
                .verifyComplete();

    }

    @Test
    void fruitsFluxFilter() {
        var fruitsFlux = fluxMonoServices.fruitsFluxFilter(5);
        StepVerifier.create(fruitsFlux)
                .expectNext("Orange","Banana")
                .verifyComplete();
    }
    @Test
    void fruitsFluxFilterMap() {
        var fruitsFlux = fluxMonoServices.fruitsFluxFilterMap(5);
        StepVerifier.create(fruitsFlux)
                .expectNext(6,6)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMap() {
        var fruitsFlux = fluxMonoServices.fruitsFluxFlatMap();
        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMapAsync() {
        var fruitsFlux = fluxMonoServices.fruitsFluxFlatMapAsync();
        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsMonoFlatMap() {
        var fruitsMono = fluxMonoServices.fruitsMonoFlatMap();
        StepVerifier.create(fruitsMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatMapAsync() {
        var fruitsFlux = fluxMonoServices.fruitsFluxConcatMapAsync();
        StepVerifier.create(fruitsFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMapMany() {

        var fruitsFlux = fluxMonoServices.fruitsFluxFlatMapMany();
        StepVerifier.create(fruitsFlux)
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransform() {
        var fruitsFlux = fluxMonoServices.fruitsFluxTransform(5);
        StepVerifier.create(fruitsFlux)
                .expectNext("Orange","Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {

        var fruitsFlux = fluxMonoServices.fruitsFluxTransformDefaultIfEmpty(511);
        StepVerifier.create(fruitsFlux)
                .expectNext("default")
                .verifyComplete();

    }

    @Test
    void fruitsFluxTransformSwitchIfEmpty() {
        var fruitsFlux = fluxMonoServices.fruitsFluxTransformSwitchIfEmpty(7);
        StepVerifier.create(fruitsFlux)
                .expectNext("Pineapple","Jack Fruit")
                .verifyComplete();

    }

    @Test
    void fruitsFluxConcat() {
        var contactFlux = fluxMonoServices.fruitsFluxConcat();
        StepVerifier.create(contactFlux)
                .expectNext("Mango", "Orange","Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatWith() {
        var contactFlux = fluxMonoServices.fruitsFluxConcatWith();
        StepVerifier.create(contactFlux)
                .expectNext("Mango", "Orange","Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsMonoConcat() {
        var monoConcat = fluxMonoServices.fruitsMonoConcat();
        StepVerifier.create(monoConcat)
                .expectNext("Mango","Orange")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMerge() {
        var contactFlux = fluxMonoServices.fruitsFluxMerge();
        StepVerifier.create(contactFlux)
                .expectNext("Mango", "Tomato","Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergeWith() {
        var contactFlux = fluxMonoServices.fruitsFluxMergeWith();
        StepVerifier.create(contactFlux)
                .expectNext("Mango", "Tomato","Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergeWithSequential() {
        var contactFlux = fluxMonoServices.fruitsFluxMergeWithSequential();
        StepVerifier.create(contactFlux)
                .expectNext("Mango", "Orange","Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZip() {

        var contactFlux = fluxMonoServices.fruitsFluxZip();
        StepVerifier.create(contactFlux)
                .expectNext("MangoTomato","OrangeLemon")
                .verifyComplete();
    }
    @Test
    void fruitsFluxZipWith() {

        var contactFlux = fluxMonoServices.fruitsFluxZipWith();
        StepVerifier.create(contactFlux)
                .expectNext("MangoTomato","OrangeLemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxZipWithTuple() {

        var contactFlux = fluxMonoServices.fruitsFluxZipWithTuple();
        StepVerifier.create(contactFlux)
                .expectNext("MangoTomatoPotato","OrangeLemonCucumber")
                .verifyComplete();
    }

    @Test
    void fruitsZipMono() {

        var contactMono = fluxMonoServices.fruitsZipMono();
        StepVerifier.create(contactMono)
                .expectNext("MangoTomato")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilterDoOn() {
        var fruitsFlux = fluxMonoServices.fruitsFluxFilterDoOn(5);
        StepVerifier.create(fruitsFlux)
                .expectNext("Orange","Banana")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorReturn() {

        var fruitsFlux = fluxMonoServices.fruitsFluxOnErrorReturn();
        StepVerifier.create(fruitsFlux)
                .expectNext("Apple","Mango","Orange")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorContinue() {
        var fruitsFlux = fluxMonoServices.fruitsFluxOnErrorContinue();
        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE","ORANGE")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorMap() {
       // Hooks.onOperatorDebug();
        ReactorDebugAgent.init();
        ReactorDebugAgent.processExistingClasses();
        var fruitsFlux = fluxMonoServices.fruitsFluxOnErrorMap().log();
        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE")
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void fruitsFluxDoOnError() {

        var fruitsFlux = fluxMonoServices.fruitsFluxDoOnError();
        StepVerifier.create(fruitsFlux)
                .expectNext("APPLE")
                .expectError(RuntimeException.class)
                .verify();
    }
}