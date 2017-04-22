package com.padeoe.platformtools;

/**
 * Created by padeoe on 2017/4/22.
 */
public class InstallFailureException extends Exception{
    public InstallFailureException(String message) {
        super(message);
    }

    public InstallFailureException(Throwable cause) {
        super(cause);
    }

    public InstallFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
