package com.padeoe.platformtools;

/**
 * Created by padeoe on 2017/4/20.
 */
public class EnvironmentNotConfiguredException extends Exception{
    public EnvironmentNotConfiguredException(String message) {
        super(message);
    }

    public EnvironmentNotConfiguredException(Throwable cause) {
        super(cause);
    }

    public EnvironmentNotConfiguredException(String message, Throwable cause) {
        super(message, cause);
    }
}
