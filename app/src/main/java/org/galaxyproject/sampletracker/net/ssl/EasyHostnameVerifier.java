package org.galaxyproject.sampletracker.net.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Hostname verifier that will accept any hostname.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class EasyHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
