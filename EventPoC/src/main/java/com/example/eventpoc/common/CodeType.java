package com.example.eventpoc.common;

/**
 * Groups the different errors in categories
 */
public enum CodeType {
    /**
     * Client error, the client did something bad
     */
    CLIENT(1),
    /**
     * Serivce error, no one else to blame
     */
    SERVICE(2),
    /**
     * Remote error, some remote service did not work as expected (MicroService/ foreign service)
     */
    REMOTE(3);

    int type;

    CodeType(int type) {
        this.type = type;
    }
}
