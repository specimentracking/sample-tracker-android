package org.galaxyproject.sampletracker.logic.security;

/**
 * An exception thrown in case of any problem in crypto class.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class CryptoException extends Exception {

    public CryptoException(Throwable throwable) {
        super(throwable);
    }
}
