package com.fimory.api.socket;

import com.fimory.api.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/socket")
public class SocketController {

    private final ChatWebSocketHandler chatWebSocketHandler;

    public SocketController(ChatWebSocketHandler chatWebSocketHandler) {
        this.chatWebSocketHandler = chatWebSocketHandler;
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> info() {
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
                "wsUrl", "ws://localhost:4000/ws/chat",
                "wsUrlWithApiPrefix", "ws://localhost:4000/api/ws/chat",
                "samplePayload", Map.of(
                        "sender", "student",
                        "message", "hello socket"
                )
        )));
    }

    @PostMapping("/broadcast")
    public ResponseEntity<ApiResponse<Map<String, Object>>> broadcast(@RequestBody(required = false) Map<String, Object> payload) {
        String message = payload != null && payload.get("message") != null
                ? String.valueOf(payload.get("message")).trim()
                : "";
        if (message.isBlank()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, Map.of("error", "message is required"), Map.of()));
        }
        chatWebSocketHandler.broadcastSystem(message);
        return ResponseEntity.ok(ApiResponse.ok(Map.of("sent", true, "message", message)));
    }
}
