package fr.xs.jtk.security.certificate.bean;

public class CertificateUserProfileBean {
	public String rootPath, jksFilePath;
	public String privateKeyFilePath, privateKeyDERFilePath;
	public String publicKeyFilePath;
	public String certRequestFilePath;
	public String pemCertFilePath, derCertFilePath, p7bCertFilePath, hashCertFilePath;
	
	public CertificateUserProfileBean(String _rootPath) {
		rootPath              = _rootPath;
		jksFilePath           = rootPath + "/JKS";
		privateKeyFilePath    = rootPath + "/private.key";
		privateKeyDERFilePath = rootPath + "/private.der.key";
		publicKeyFilePath     = rootPath + "/public.key";
		certRequestFilePath   = rootPath + "/request.csr";
		pemCertFilePath 	  = rootPath + "/certificate.pem.crt";
		derCertFilePath 	  = rootPath + "/certificate.der.crt";
		p7bCertFilePath 	  = rootPath + "/certificate.p7b.crt";
		hashCertFilePath 	  = rootPath + "/0000.0";
	}
	
}
