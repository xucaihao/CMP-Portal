package com.cmp.portal.common;

public class PortalException extends RuntimeException {

    private int code;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public PortalException(String message, int code) {
        super(message);
        this.code = code;
    }

    public PortalException(String message) {
        super(message);
    }

    public PortalException(Throwable e) {
        super(e);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
