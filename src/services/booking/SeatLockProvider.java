package services.booking;

import models.ShowSeat;
import models.locks.SeatLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SeatLockProvider {
    private final ConcurrentHashMap<String, SeatLock> locks = new ConcurrentHashMap<>();

    public boolean lockSeats(List<String> showSeatIds, String userId, List<ShowSeat> showSeats) {
        for (String seatId : showSeatIds) {
            SeatLock existing = locks.get(seatId);
            if (existing != null && existing.isActive()) {
                return false;
            }
        }

        List<String> sorted = showSeatIds.stream().sorted().toList();
        List<String> acquired = new ArrayList<>();

        synchronized (this) {
            for (String seatId : sorted) {
                SeatLock existing = locks.get(seatId);
                if (existing != null && existing.isActive()) {
                    acquired.forEach(locks::remove);
                    return false;
                }
                locks.put(seatId, new SeatLock(seatId, userId));
                acquired.add(seatId);
            }
        }

        showSeats.forEach(ShowSeat::lock);
        return true;
    }

    public void unlockSeats(List<String> showSeatIds) {
        showSeatIds.forEach(locks::remove);
    }

    public boolean isSeatLocked(String showSeatId) {
        SeatLock lock = locks.get(showSeatId);
        if (lock == null) {
            return false;
        }
        if (lock.isExpired()) {
            locks.remove(showSeatId);
            return false;
        }
        return true;
    }
}