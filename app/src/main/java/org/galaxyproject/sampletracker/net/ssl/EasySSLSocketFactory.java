package org.galaxyproject.sampletracker.net.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Socket factory that will create a SSL socket that accepts any certificate.
 * 
 * @author Pavel Sveda <xsveda@gmail.com>
 */
public final class EasySSLSocketFactory extends SSLSocketFactory {

    private SSLContext mSslContext = null;

    private static SSLContext createEasySSLContext() throws IOException {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[] {new EasyX509TrustManager()}, null);
            return context;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private SSLContext getSSLContext() throws IOException {
        if (mSslContext == null) {
            mSslContext = createEasySSLContext();
        }
        return mSslContext;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return HttpsURLConnection.getDefaultSSLSocketFactory().getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return HttpsURLConnection.getDefaultSSLSocketFactory().getDefaultCipherSuites();
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(s, host, port, autoClose);
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException,
            UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
            throws IOException {
        return getSSLContext().getSocketFactory().createSocket(address, port, localAddress, localPort);
    }
}
