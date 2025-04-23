package com.example.sbur_mongo

import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux

@Component
@EnableScheduling
class PlaneFinderPoller(private val reposritory: AircraftReposritory) {
    private val client = WebClient.create("http://localhost:7634/aircraft")

    @Scheduled(fixedRate = 1000)
    private fun pollPlanes() {
        reposritory.deleteAll()

        client.get()
            .retrieve()
            .bodyToFlux<Aircraft>()
            .filter {!it.reg.isNullOrBlank()}
            .toStream()
            .forEach{reposritory.save(it)}

        println("--- All Samoletes")
        reposritory.findAll().forEach{println(it)}
    }
}