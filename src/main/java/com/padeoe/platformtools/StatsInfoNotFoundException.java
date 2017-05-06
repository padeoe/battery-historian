package com.padeoe.platformtools;

/**
 * Created by padeoe on 2017/5/5.
 */
public class StatsInfoNotFoundException extends Exception{
    public StatsInfoNotFoundException(String message) {
        super(message);
    }

    public StatsInfoNotFoundException(Throwable cause) {
        super(cause);
    }

    public StatsInfoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
