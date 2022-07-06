package com.example.eventpoc;


public interface IsEqual<T> {
    /**
     * Compares two objects of the same class
     * Returns false, if only one of the class fields is different.
     * Returns true if the objects value are the same.
     */
    boolean isEqual(T obj);
}
