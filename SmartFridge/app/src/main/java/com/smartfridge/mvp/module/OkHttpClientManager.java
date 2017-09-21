package com.smartfridge.mvp.module;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class OkHttpClientManager {

	public static SSLSocketFactory getSslSocketFactory(
			ArrayList<InputStream> certificates) {

		try {
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509");
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null);
			int index = 0;
			for (InputStream certificate : certificates) {
				String certificateAlias = Integer.toString(index++);
				keyStore.setCertificateEntry(certificateAlias,
						certificateFactory.generateCertificate(certificate));

				try {
					if (certificate != null)
						certificate.close();
				} catch (IOException e) {
				}
			}

			SSLContext sslContext = SSLContext.getInstance("TLS");

			TrustManagerFactory trustManagerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(),
					new SecureRandom());
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
