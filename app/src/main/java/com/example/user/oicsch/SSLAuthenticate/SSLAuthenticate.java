package com.example.user.oicsch.SSLAuthenticate;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;

public class SSLAuthenticate {
    public static OkHttpClient getAuthenticate(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        //for error Android java.security.cert.CertPathValidatorException: Trust anchor for certification path not found

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
// From https://www.washington.edu/itconnect/security/ca/load-der.crt
        InputStream caInput =  context.getAssets().open("cert.crt");
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
            //System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            caInput.close();
        }

// Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

// Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

// Create an SSLContext that uses our TrustManager
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, tmf.getTrustManagers(), null);

        return new OkHttpClient.Builder().sslSocketFactory(sslcontext.getSocketFactory()).build();
    }
}
