package com.thehecklers.aircraftpositions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest(controllers = {PositionController.class})
class PositionControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private PositionService service;
    @MockBean
    private RSocketRequester requester;

    private Aircraft ac1, ac2, ac3;

    @BeforeEach
    void setUp(ApplicationContext context) {
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SFO", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        ac2 = new Aircraft(2L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8560963, -104.6759263, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        ac3 = new Aircraft(3L, "SAL002", "sqwk", "N54321", "SAL002",
                "SFO-STL", "LJ", "ct",
                40000, 65, 440, 0, 0,
                39.8412964, -105.0048267, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());



        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)); правильный варик


        //Hooks.onOperatorDebug();
        //Error has been observed at the following site(s):
        //|_         Flux.error ⇢ at com.thehecklers.aircraftpositions.PositionControllerTest.setUp(PositionControllerTest.java:62)
        //|_    Flux.concatWith ⇢ at com.thehecklers.aircraftpositions.PositionControllerTest.setUp(PositionControllerTest.java:62)
        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .concatWith(Flux.error(new Throwable("чот не ок"))));


        //обычный чек поинт показывает что над ним ошибка который ласт
        //Assembly trace from producer [reactor.core.publisher.FluxConcatArray] :
        //reactor.core.publisher.Flux.checkpoint(Flux.java:3212)
        //com.thehecklers.aircraftpositions.PositionControllerTest.setUp(PositionControllerTest.java:71)
        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint()
        //        .concatWith(Flux.error(new Throwable("чот не ок")))
        //        .checkpoint());


        //с стринг упрощенная
        //|_ checkpoint ⇢ чот не ок
        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint("всё ок")
        //        .concatWith(Flux.error(new Throwable("Нет ответа")))
        //        .checkpoint("чот не ок"));

        //с стринг стандартная с описанием
        //        Assembly trace from producer [reactor.core.publisher.FluxConcatArray], described as [чот не ок] :
        //        reactor.core.publisher.Flux.checkpoint(Flux.java:3277)
        //Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
        //        .checkpoint("все ок", true)
        //        .concatWith(Flux.error(new Throwable("Нет ответа")))
        //        .checkpoint("чот не ок", true)
        //);
        //реактор-тулс!
        //java.lang.Throwable: Нет ответа
        //	at com.thehecklers.aircraftpositions.PositionControllerTest.setUp(PositionControllerTest.java:98) ~[test-classes/:na]
        //	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException:
        //Assembly trace from producer [reactor.core.publisher.FluxConcatArray] :
        ReactorDebugAgent.init();//быстрее хукса
        Mockito.when(service.getAllAircraft()).thenReturn(Flux.just(ac1, ac2, ac3)
                .concatWith(Flux.error(new Throwable("Нет ответа"))) //умышленная ошибка, тест не ожидает ёё появления
        );
        Mockito.when(service.getAircraftById(1L)).thenReturn(Mono.just(ac1));
        Mockito.when(service.getAircraftById(2L)).thenReturn(Mono.just(ac2));
        Mockito.when(service.getAircraftById(3L)).thenReturn(Mono.just(ac3));
        Mockito.when(service.getAircraftByReg("N12345")).thenReturn(Flux.just(ac1));
        Mockito.when(service.getAircraftByReg("N54321")).thenReturn(Flux.just(ac2,ac3));

    }

    @Test
    void getAllACPositions() {
        StepVerifier.create(client.get()
                        .uri("/acpos")
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON)
                        .returnResult(Aircraft.class)
                        .getResponseBody())
                .expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();
    }

    @Test
    void getAircraftById() {
        StepVerifier.create(client.get()
                .uri("/acpos/search?id=1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac1)
                .verifyComplete();
    }

    @Test
    void getAircraftByReg() {
        StepVerifier.create(client.get()
        .uri("/acpos/search?reg=N54321")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();
    }
}
