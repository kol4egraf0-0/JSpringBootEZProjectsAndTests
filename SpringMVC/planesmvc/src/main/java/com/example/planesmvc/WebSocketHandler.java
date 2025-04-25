package com.example.planesmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    @Getter
    private final List<WebSocketSession> sessionList = new ArrayList<>();
    @NonNull
    private final AircraftRepository repository;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        sessionList.add(session);
        //sendAircraftDataToAll();
        System.out.println("Соединение исходит из " + session.toString() +
                " @ " + Instant.now().toString());
    }

    /*public void sendAircraftDataToAll() {
        sessionList.forEach(session -> {
            try {
                sendAircraftData(session);
            } catch (IOException e) {
                System.err.println("Ошибка отправки данных: " + e.getMessage());
            }
        });
    }

    private void sendAircraftData(WebSocketSession session) throws IOException {
        List<Aircraft> aircraftList = (List<Aircraft>) repository.findAll();
        String json = new ObjectMapper().writeValueAsString(aircraftList); // Конвертируем в JSON
        session.sendMessage(new TextMessage(json));
    }
     */


    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            System.out.println("Сообщение получено: '" + message + "', с " +
                    session.toString());

            for (WebSocketSession sessionInList : sessionList) {
                if (sessionInList != session) {
                    sessionInList.sendMessage(message);
                    System.out.println("--> Cообщение отпрлвено '" + message + "' этому-> " +
                            sessionInList.toString());
                }
            }
        }
        catch(Exception e){
            System.out.println("Exception handling message: " + e.getLocalizedMessage());
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        sessionList.remove(session);
        System.out.println("Connection закрыто б " + session.toString() +
                " @ " + Instant.now().toString());
    }
}
