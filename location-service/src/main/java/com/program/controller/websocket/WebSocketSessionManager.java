package com.program.controller.websocket;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private final Map<String, String> rideSessions = new ConcurrentHashMap<>();

    public void registerSession(String rideId, String sessionId) {
        rideSessions.put(rideId, sessionId);
    }

    public void removeSession(String rideId) {
        rideSessions.remove(rideId);
    }

    public String getSession(String rideId) {
        return rideSessions.get(rideId);
    }
}
