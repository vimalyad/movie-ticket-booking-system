package models.locks;

import java.time.Instant;
import java.util.UUID;

public class SeatLock {
    public static final int DEFAULT_TTL_SECONDS = 600;

    private final String id = UUID.randomUUID().toString();
    private final String showSeatId;
    private final String userId;
    private final Instant expiresAt;

    public SeatLock(String showSeatId, String userId) {
        this.showSeatId = showSeatId;
        this.userId = userId;
        this.expiresAt = Instant.now().plusSeconds(DEFAULT_TTL_SECONDS);
    }

    public boolean isActive() {
        return Instant.now().isBefore(expiresAt);
    }

    public boolean isExpired() {
        return !isActive();
    }

    public String getId() {
        return id;
    }

    public String getShowSeatId() {
        return showSeatId;
    }

    public String getUserId() {
        return userId;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}