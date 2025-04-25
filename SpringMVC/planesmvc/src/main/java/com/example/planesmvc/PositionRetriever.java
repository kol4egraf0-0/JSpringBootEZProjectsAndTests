package com.example.planesmvc;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
@Configuration
public class PositionRetriever {
    private final AircraftRepository repo;
    private final WebSocketHandler handler;
    @Bean
    Consumer<List<Aircraft>> retrieveAircraftPositions() {
     return acList -> {
         System.out.println("Received aircraft: " + acList.size());
       repo.deleteAll();

       repo.saveAll(acList);

       repo.findAll().forEach(System.out::println);//проверка
     };
    }

    private void sendPositions() {
        if(repo.count() > 0) {
            for(WebSocketSession sessionInList : handler.getSessionList()){
                try{
                    sessionInList.sendMessage(
                            new TextMessage(repo.findAll().toString())
                    );
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
