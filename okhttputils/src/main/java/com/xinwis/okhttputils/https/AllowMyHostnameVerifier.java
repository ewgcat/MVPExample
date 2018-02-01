package com.xinwis.okhttputils.https;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

/**
 * Created by lenovo on 2017/2/13.
 */
public class AllowMyHostnameVerifier implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        try {
            String peerHost = session.getPeerHost(); //服务器返回的主机名
            X509Certificate[] peerCertificates = (X509Certificate[]) session.getPeerCertificates();
            for (X509Certificate certificate : peerCertificates) {
                X500Principal subjectX500Principal = certificate.getSubjectX500Principal();
                String name = subjectX500Principal.getName();
                String[] split = name.split(",");
                for (String str : split) {
                    if (str.startsWith("CN")) {//证书绑定的域名或者ip
                        if (str.contains(hostname) && str.contains(peerHost)) {
                            return true;
                        }
                    }
                }
            }
        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
