package fr.xs.jtk.security.certificate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.xs.jtk.helpers.MediaHelper;

public class CertificateHelper {
	private static final String   storage          = "/snkbox/var/certificates/Users/Sanke/";
	private static final String   output           = "/media/sanke/share/home/Xplore Solution/Projets/fr.xs.eCompanion/src/keystore/";

	private static final String   keystoreFile     = output + "GeneratedKeyStore";

    public static void testKey(PrivateKey privateKey) {
        byte[] data = "Security test with imported keys from openSSL".getBytes();
        byte[] sign = "null".getBytes();

        try {
            Signature rsaSigner = Signature.getInstance("SHA1withRSA");

            rsaSigner.initSign(privateKey);
            rsaSigner.update(data, 0, data.length);
            sign = rsaSigner.sign();

        } catch(Exception ex) {
            Logger.getLogger(CertificateHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(new String(sign));
    }

	public static void main(String[] args) {
		final String keyPswd   = "azerty";
		final String storePswd = "azerty";

		final String alias     = "spweb";

		PrivateKey    key   = loadOpenSSLKeyInDERFormat(storage + "PrivateKey.der");
		Certificate[] certs = loadOpenSSLCertificateInDERFormat(storage + "Certificate.der");

		KeyStore ks = prepareKeyStore(keystoreFile, storePswd);
		addToKeyStore(ks, storePswd, key, keyPswd, certs, "spweb");
		
		testKey(key);
	}

	static PrivateKey loadOpenSSLKeyInDERFormat(String _key_path) {
		PrivateKey privateKey = null;

		try {
			byte[] key = MediaHelper.getContentAsByteArrays(_key_path);

	        KeyFactory kf = KeyFactory.getInstance("RSA");
	        privateKey = kf.generatePrivate( new PKCS8EncodedKeySpec(key) );
		} catch(Exception e) {
			e.printStackTrace();
		}

		return privateKey;
	}
	
	static Certificate[] loadOpenSSLCertificateInDERFormat(String _cert_path) {
		Certificate[] certs = null;

		try {
	        InputStream certstream = MediaHelper.getContent (_cert_path);

	        CertificateFactory cf = CertificateFactory.getInstance("X.509");
	        Collection<? extends Certificate> c = cf.generateCertificates(certstream) ;
	        certs = new Certificate[c.toArray().length];
	
	        if(c.size() == 1) {
	            certstream = MediaHelper.getContent (_cert_path);
	            System.out.println("One certificate, no chain.");
	            Certificate cert = cf.generateCertificate(certstream) ;
	            certs[0] = cert;
	        } else {
	            System.out.println("Certificate chain length: "+c.size());
	            certs = (Certificate[])c.toArray();
	        }
	   
		} catch(Exception e) {
			e.printStackTrace();
		}

		return certs;
	}
	
	static KeyStore prepareKeyStore(String _path, String _storePWD) {
        KeyStore ks = null;
		try {
			ks = KeyStore.getInstance("JKS", "SUN");

	        ks.load  (null, _storePWD.toCharArray());
	        ks.store (new FileOutputStream(_path), _storePWD.toCharArray());
	        ks.load  (new FileInputStream(_path), _storePWD.toCharArray());
	        
		} catch(Exception e) {
			e.printStackTrace();
		}
        
		return ks;
	}
	
	static void addToKeyStore(KeyStore _store, String _storePWD, PrivateKey _key, String _keyPWD, Certificate[] _certs, String _alias) {
		try {
			_store.setKeyEntry(_alias, _key, _keyPWD.toCharArray(), _certs);
	        _store.store(new FileOutputStream (keystoreFile), _storePWD.toCharArray());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
