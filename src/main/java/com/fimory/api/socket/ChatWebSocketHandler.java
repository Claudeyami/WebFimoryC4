package com.fimory.api.socket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        sendToSession(session, Map.of(
                "type", "system",
                "message", "Connected to Fimory socket",
                "timestamp", Instant.now().toString()
        ));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String raw = message.getPayload() == null ? "" : message.getPayload().trim();
        if (raw.isBlank()) {
            return;
        }

        String sender = "anonymous";
        String content = raw;
        if (raw.startsWith("{")) {
            try {
                Map<String, Object> payload = objectMapper.readValue(raw, new TypeReference<>() {
                });
                Object senderRaw = payload.get("sender");
                Object messageRaw = payload.get("message");
                if (senderRaw != null && !String.valueOf(senderRaw).isBlank()) {
                    sender = String.valueOf(senderRaw).trim();
                }
                if (messageRaw != null && !String.valueOf(messageRaw).isBlank()) {
                    content = String.valueOf(messageRaw).trim();
                }
            } catch (Exception ignored) {
                content = raw;
            }
        }

        if (content.isBlank()) {
            return;
        }

        broadcast(Map.of(
                "type", "chat",
                "sender", sender,
                "message", content,
                "timestamp", Instant.now().toString()
        ));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        sessions.remove(session);
        if (session.isOpen()) {
            try {
                session.close(CloseStatus.SERVER_ERROR);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    public void broadcastSystem(String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        broadcast(Map.of(
                "type", "system",
                "message", message.trim(),
                "timestamp", Instant.now().toString()
        ));
    }

    private void broadcast(Map<String, Object> payload) {
        for (WebSocketSession client : sessions) {
            if (!client.isOpen()) {
                sessions.remove(client);
                continue;
            }
            sendToSession(client, payload);
        }
    }

    private void sendToSession(WebSocketSession session, Map<String, Object> payload) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            session.sendMessage(new TextMessage(json));
        } catch (Exception ignored) {
            sessions.remove(session);
        }
    }
}
