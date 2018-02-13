package bernard.tatin.common;

import java.time.Instant;

public class Chrono {
    private long startMS;
    private long endMS;
    public boolean isRunning;
    public boolean isInited = false;

    public Chrono(boolean startNow) {
        if (startNow) {
            start();
        } else {
            isRunning = false;
        }
    }

    public void start() {
        isRunning = true;
        isInited = true;
        startMS = Instant.now().toEpochMilli();
    }

    public long get() throws ChronoException {
        if (isRunning) {
            return Instant.now().toEpochMilli() - startMS;
        } else {
            throw(new ChronoException("Chrono not running"));
        }
    }

    public long stop() throws ChronoException {
        if (isRunning) {
            endMS = Instant.now().toEpochMilli();
            isRunning = false;
            return endMS - startMS;
        } else {
            throw(new ChronoException("Chrono not running"));
        }
    }

    public long last() throws ChronoException {
        if (isRunning) {
            throw(new ChronoException("Chrono not running"));
        } else if (!isInited) {
            throw (new ChronoException("Chrono has never run"));
        } else {
            return endMS - startMS;
        }

    }
}
