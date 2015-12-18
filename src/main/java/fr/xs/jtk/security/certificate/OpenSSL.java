package fr.xs.jtk.security.certificate;

import fr.xs.jtk.security.certificate.bean.CertificateAuthorityBean;
import fr.xs.jtk.security.certificate.bean.CertificateIdentityBean;
import fr.xs.jtk.system.NativeProcess;
import fr.xs.jtk.system.RootProcess;
import fr.xs.jtk.system.SystemProcess;

public class OpenSSL {

	public void generatePrivateKey(String _path, int _size) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "genrsa", "-out", _path, String.valueOf(_size)) );
	}
	public void generatePrivateKey(String _path, int _size, String _password) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "genrsa", "-des3", "-out", _path, String.valueOf(_size)) );
	}

	public void generatePublicKey(String _privatePath, String __publicPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "rsa", "-in", _privatePath, "-outform", "PEM", "-pubout", "-out", __publicPath) );
	}

	public void generateCertificateSigningRequest(String _privatePath, CertificateIdentityBean _bean, String _certificatePath) {
		String subj = "/CN=" + _bean.commonName
					+ "/emailAddress=" + _bean.emailAddress
					+ "/O=" + _bean.organisation
					+ "/OU=" + _bean.organisationUnit
					+ "/streetAddress=" + _bean.streetAddress
					+ "/L=" + _bean.location
					+ "/postalCode=" + _bean.zipCode
					+ "/ST=" + _bean.state
					+ "/C=" + _bean.country;
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "req", "-new", "-key", _privatePath, "-subj", subj, "-out", _certificatePath) );

	}
	
	public void deliverSignedCertificate(String _authorityCfgPath, String requestPath, String _certificatePath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "ca", "-batch", "-config", _authorityCfgPath, "-in", requestPath, "-out", _certificatePath) );
		
		System.out.println("openssl ca -batch -config " + _authorityCfgPath + " -in " + requestPath + " -out " +_certificatePath);
		
		System.out.println(err);
	}
	
	public void revocateCertificate(String _certificatePath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "ca", "-revoke", _certificatePath) );
		
	}
	
	public String getHashCertificateName(String _pemPath) {
		return NativeProcess.getOutput( NativeProcess.runtime().exec("openssl", "x509", "-noout", "-hash", "-in", _pemPath) );
	}

	public void convertKey_PEMtoDER(String _pemPath, String _derPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "pkcs8", "-topk8", "-nocrypt", "-in", _pemPath, "-inform", "PEM", "-out", _derPath, "-outform", "DER") );

	}
	public void convertCertificate_PEMtoDER(String _pemPath, String _derPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "x509", "-outform", "der", "-in", _pemPath, "-out", _derPath) );

	}
	public void convertCertificate_PEMtoHASH(String _pemPath, String _derPath, String _hashPath) { // Still in DER Format
		String hashed = NativeProcess.getOutput( NativeProcess.runtime().exec("openssl", "x509", "-noout", "-hash", "-in", _pemPath) );
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("cp", _derPath, _hashPath + "/" + hashed + ".0") );

	}
	public void convertCertificate_PEMtoPFX(String _keyPath, String _pemPath, String _caPath, String _pfxPath) { // Require password...
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "pkcs12", "-export", "-inkey", _keyPath, "-in", _pemPath, "-certfile", _caPath, "-out", _pfxPath) );

	}
	public void convertCertificate_PEMtoP7B(String _pemPath, String _p7bPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "crl2pkcs7", "-nocrl", "-certfile", _pemPath, "-out", _p7bPath) );

	}

	public void convertCertificate_DERtoPEM(String _derPath, String _pemPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "x509", "-inform", "der", "-in", _derPath, "-out", _pemPath) );

	}

	public void convertCertificate_P7BtoPEM(String _p7bPath, String _pemPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "pkcs7", "-print_certs", "-in", _p7bPath, "-out", _pemPath) );

	}

	public void convertCertificate_PFXtoPEM(String _p7bPath, String _pemPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "pkcs12", "-in", _p7bPath, "-out", _pemPath, "-nodes") );

	}

	public void convertCertificate_P7BtoPFX(String _keyPath, String _authorityCrtPath, String _p7bPath, String _pfxPath) {
		String tmp = _p7bPath + ".pem";
		int err = 0;
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "pkcs7", "-print_certs", "-in", _p7bPath, "-out", tmp) );
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "pkcs12", "-export", "-in", tmp, "-inkey", _keyPath, "-out", _pfxPath, "-certfile", _authorityCrtPath) );

	}

	public void encodeFile(String _publicKeyPath, String _inPath, String _outPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "rsautl", "-encrypt", "-inkey", _publicKeyPath, "-pubin", "-in", _inPath, "-out", _outPath) );

	}

	public void decodeFile(String _privateKeyPath, String _inPath, String _outPath) {
		int err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "rsautl", "-decrypt", "-inkey", _privateKeyPath, "-in", _inPath, "-out", _outPath) );

	}
	
	
	
	
	public static void main(String... argv) {
		OpenSSL ssl = new OpenSSL();
		
		CertificateIdentityBean root = new CertificateIdentityBean();
		root.commonName       = "Internet Truster";
		root.emailAddress     = "authority@sp-web.fr";
		root.organisation     = "SP-WEB.FR";
		root.organisationUnit = "Administration";
		root.streetAddress    = "98, Boulevard de la Reine";
		root.location         = "Versailles";
		root.zipCode          = "78000";
		root.state            = "Ile de France";
		root.country          = "Fr";

		CertificateIdentityBean bean = new CertificateIdentityBean();
		bean.commonName       = "Steve PECHBERTI";
		bean.emailAddress     = "authority@sp-web.fr";
		bean.organisation     = "Software Manufacture";
		bean.organisationUnit = "Research & Development";
		bean.streetAddress    = "98, Boulevard de la Reine";
		bean.location         = "Versailles";
		bean.zipCode          = "78000";
		bean.state            = "Ile de France";
		bean.country          = "Fr";
		
		CertificateAuthorityBean ca = new CertificateAuthorityBean("/tmp/rootCA"); 
		/*
		ssl.initializeRootCertificateAuthority(ca, root, 1024);
		
		ssl.generatePrivateKey("/tmp/sanke/private.key", 1024);
		ssl.generateCertificateSigningRequest("/tmp/sanke/private.key", bean, "/tmp/sanke/request.csr");
		ssl.generatePublicKey("/tmp/sanke/private.key", "/tmp/sanke/public.key");
		
		ssl.deliverSignedCertificate(ca.configFilePath, "/tmp/sanke/request.csr", "/tmp/sanke/sanke-signed.pem");
		ssl.convertKey_PEMtoDER("/tmp/sanke/private.key", "/tmp/sanke/private.der.key");
		ssl.convertCertificate_PEMtoDER("/tmp/sanke/sanke-signed.pem", "/tmp/sanke/sanke-signed.der");
		ssl.convertCertificate_PEMtoHASH("/tmp/sanke/sanke-signed.pem", "/tmp/sanke/sanke-signed.der", "/tmp/sanke/");
		ssl.convertCertificate_PEMtoP7B("/tmp/sanke/sanke-signed.pem", "/tmp/sanke/sanke-signed.p7b");
		*/

		SystemProcess.createFile("/tmp/test.orig", "HelloWorld");
		ssl.encodeFile("/tmp/sanke/public.key", "/tmp/test.orig", "/tmp/test.encoded");
		ssl.decodeFile("/tmp/sanke/private.key", "/tmp/test.encoded", "/tmp/test.decoded");

		ssl.convertKey_PEMtoDER("/tmp/sanke/private.key", "/tmp/sanke/private.der.key");
		ImportKey.generateJavaKeyStore("/tmp/sanke/private.der.key", "/tmp/sanke/sanke-signed.der", "/tmp/sanke/JKS");
	}

	public void initializeRootCertificateAuthority(CertificateAuthorityBean _ca, CertificateIdentityBean _bean, int _size) {
		String distinguishedName = "root_ca_distinguished_name";
		String defaultCertificateAuthority = "defaultCA";
		
		int err = 0;
		// Création de l'arborescence de certification
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("mkdir", "-p", _ca.configPath) );
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("mkdir", "-p", _ca.privatePath) );
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("mkdir", "-p", _ca.publicPath) );
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("mkdir", "-p", _ca.signedKeysPath) );

		// Création de la 'Clef Racine' ('Root Key') au format PEM
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "genrsa", "-out", _ca.privateKeyFilePath, String.valueOf(_size)) );

		// Création du fichier de configuration
		String cfg = "# (c) SP-WEB.FR (2007-?XYZ) - Steve PECHBERTI <steve.pechberti@sp-web.fr>" + "\n";
		cfg += "[ req ]" + "\n";
		cfg += "default_bits = " + _size + "\n";
		cfg += "default_keyfile = " + _ca.privateKeyFilePath + "\n";
		cfg += "default_md = sha1" + "\n";
		cfg += "prompt = no" + "\n";
		cfg += "distinguished_name = " + distinguishedName + "\n";
		cfg += "x509_extensions = v3_ca" + "\n";

		cfg += "[ " + distinguishedName + " ]" + "\n";
		cfg += "commonName = " + _bean.commonName + "\n";
		cfg += "0.organizationName = " + _bean.organisation + "\n";
		cfg += "0.organizationalUnitName = " + _bean.organisationUnit + "\n";
		cfg += "emailAddress = " + _bean.emailAddress + "\n";
		cfg += "streetAddress = " + _bean.streetAddress + "\n";
		cfg += "localityName = " + _bean.location + "\n";
		cfg += "postalCode = " + _bean.zipCode + "\n";
		cfg += "stateOrProvinceName = " + _bean.state + "\n";
		cfg += "countryName = " + _bean.country + "\n";

		cfg += "[ v3_ca ]" + "\n";
		cfg += "subjectKeyIdentifier = hash" + "\n";
		cfg += "authorityKeyIdentifier = keyid:always,issuer:always" + "\n";
		cfg += "basicConstraints = CA:true" + "\n";

		cfg += "[ ca ]" + "\n";
		cfg += "default_ca = " + defaultCertificateAuthority + "\n";

		cfg += "[ " + defaultCertificateAuthority + " ]" + "\n";
		cfg += "dir = " + _ca.rootPath + "\n";
		cfg += "new_certs_dir = " + _ca.signedKeysPath + "\n";
		cfg += "database = " + _ca.indexFilePath + "\n";
		cfg += "certificate = " + _ca.pemCertFilePath + "\n";
		cfg += "serial = " + _ca.serialFilePath + "\n";
		cfg += "private_key = " + _ca.privateKeyFilePath + "\n";
		cfg += "x509_extensions = usr_cert" + "\n";
		cfg += "name_opt = ca_default" + "\n";
		cfg += "cert_opt = ca_default" + "\n";
		cfg += "default_crl_days = 30" + "\n";
		cfg += "default_days = 3650" + "\n";
		cfg += "default_md = sha1" + "\n";
		cfg += "preserve = no" + "\n";
		cfg += "policy = policy_match" + "\n";

		cfg += "[ policy_match ]" + "\n";
		cfg += "commonName               = supplied" + "\n";
		cfg += "organizationName         = optional" + "\n";
		cfg += "organizationalUnitName   = optional" + "\n";
		cfg += "emailAddress             = optional" + "\n";
		cfg += "streetAddress            = optional" + "\n";
		cfg += "localityName             = supplied" + "\n";
		cfg += "stateOrProvinceName      = optional" + "\n";
		cfg += "countryName              = match" + "\n";

		cfg += "[ usr_cert ]" + "\n";
		cfg += "basicConstraints         = CA:FALSE" + "\n";
		cfg += "subjectKeyIdentifier     = hash" + "\n";
		cfg += "authorityKeyIdentifier   = keyid,issuer:always" + "\n";
		cfg += "nsCaRevocationUrl        = http://sp-web.fr/certificates/sanke-ca.pem" + "\n";

		cfg += "[ crl_ext ]" + "\n";
		cfg += "authorityKeyIdentifier  = keyid:always,issuer:always" + "\n";

		RootProcess.createFile(_ca.configFilePath, cfg);

		// Création du 'Certificat Racine (Auto-signé)' ('Root Certificate') au format PEM
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "req", "-new", "-x509", "-nodes", "-key", _ca.privateKeyFilePath, "-config", _ca.configFilePath, "-out", _ca.pemCertFilePath, "-outform", "PEM") );

		// Création de la 'Clef Racine Publique' au format PEM
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("openssl", "rsa", "-in", _ca.privateKeyFilePath, "-pubout", "-out", _ca.publicKeyFilePath, "-outform", "PEM") );


		// Génération du certificat au format DER
		convertCertificate_PEMtoDER(_ca.pemCertFilePath, _ca.derCertFilePath);

		// Génération du certificat au format P7B
		convertCertificate_PEMtoP7B(_ca.pemCertFilePath, _ca.p7bCertFilePath);

		// Génération du certificat au format DER et dont le nom est concordant avec le hash du certificat? (pour d'autres clients)
		String hashed = NativeProcess.getOutput( NativeProcess.runtime().exec("openssl", "x509", "-noout", "-hash", "-in", _ca.pemCertFilePath) );
		_ca.hashCertFilePath = _ca.privatePath + "/" + hashed + ".0";
		err = NativeProcess.getExitCode( NativeProcess.runtime().exec("cp", _ca.derCertFilePath, _ca.hashCertFilePath) );

		SystemProcess.touchFile(_ca.indexFilePath);
		SystemProcess.createFile(_ca.serialFilePath, "01");
	}

}
