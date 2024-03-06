package com.example.demo.Controller;

import com.example.demo.domain.WebSocketDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
public class WebSocketController extends TextWebSocketHandler {

    private HashMap<String, WebSocketSession> userSession = new HashMap<>(); // key : cctv id , value : session

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: " + payload);

        // 콤마로 구분된 값을 분리하여 배열로 저장
        String[] payloadValues = payload.split(",");

        for (String value : payloadValues) {
            userSession.put(value.trim(), session); // 공백을 제거하고 저장
        }
    }

    public void sendVisionMsg(String key, String status, String vision) {
        WebSocketSession session = userSession.get(key);
        if (session != null && session.isOpen()) {
            try {
                // 객체를 JSON 문자열로 변환
                String jsonMessage = objectMapper.writeValueAsString(new WebSocketDto(key, status, vision));

                session.sendMessage(new TextMessage(jsonMessage));
            } catch (IOException e) {
                log.error("Failed to send message to user with key: " + key, e);
            }
        } else {
            log.warn("Session not found or closed for user with key: " + key);
        }
    }

    public void sendDisconnectedMsg(String key) {
        String status = "This cctv is disconnected";
        WebSocketSession session = userSession.get(key);
        if (session != null && session.isOpen()) {
            try {
                // 객체를 JSON 문자열로 변환
                String jsonMessage = objectMapper.writeValueAsString(new WebSocketDto(key, status, status));

                session.sendMessage(new TextMessage(jsonMessage));
            } catch (IOException e) {
                log.error("Failed to send message to user with key: " + key, e);
            }
        } else {
            log.warn("Session not found or closed for user with key: " + key);
        }
    }

    /* Client가 접속 시 호출되는 메서드 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println(session + " 클라이언트 접속");
        log.info(session + " 클라이언트 접속");
    }

    /* Client가 접속 해제 시 호출되는 메서드드 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        Iterator<Entry<String, WebSocketSession>> iterator = userSession.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WebSocketSession> entry = iterator.next();
            if (entry.getValue().equals(session)) {
                iterator.remove(); // 해당 key-value 쌍을 제거
            }
        }
        log.info(session + " 클라이언트 접속 해제");
    }
}