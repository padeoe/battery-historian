package com.padeoe.platformtools;

/**
 * Created by padeoe on 2017/4/22.
 */
public class NoDeviceException extends Exception{
    public NoDeviceException(String message) {
        super(message);
    }

    public NoDeviceException(Throwable cause) {
        super(cause);
    }

    public NoDeviceException(String message, Throwable cause) {
        super(message, cause);
    }
}
