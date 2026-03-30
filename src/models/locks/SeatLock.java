package models.locks;

import java.time.Instant;
import java.util.UUID;

public class SeatLock {
    public static final int DEFAULT_TTL_SECONDS = 600;

    private final String id;
    private final String showSeatId;
    private final String userId;
    private final Instant lockedAt;
    private final Instant expiresAt;

    public SeatLock(String showSeatId, String userId) {
        this(showSeatId, userId, DEFAULT_TTL_SECONDS);
    }

    public SeatLock(String showSeatId, String userId, int ttlSeconds) {
        this.id = UUID.randomUUID().toString();
        this.showSeatId = showSeatId;
        this.userId = userId;
        this.lockedAt = Instant.now();
        this.expiresAt = lockedAt.plusSeconds(ttlSeconds);
    }

    public boolean isActive() {
        return Instant.now().isBefore(expiresAt);
    }

    public boolean isExpired() {
        return !isActive();
    }

    public long secondsRemaining() {
        return expiresAt.getEpochSecond() - Instant.now().getEpochSecond();
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

    public Instant getLockedAt() {
        return lockedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    @Override
    public String toString() {
        return "SeatLock[seat=" + showSeatId + ", user=" + userId
                + ", expires=" + expiresAt
                + (isExpired() ? " EXPIRED" : " active(" + secondsRemaining() + "s)") + "]";
    }
}
