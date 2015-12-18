package fr.xs.jtk.security.certificate.bean;

public class CertificateAuthorityBean {
	public String rootPath, configPath, privatePath, publicPath, signedKeysPath;
	public String configFilePath;
	public String indexFilePath;
	public String serialFilePath;
	public String privateKeyFilePath;
	public String publicKeyFilePath;
	public String pemCertFilePath, derCertFilePath, p7bCertFilePath, hashCertFilePath;
	
	public CertificateAuthorityBean(String _rootPath) {
		rootPath            = _rootPath;
		configPath          = _rootPath + "/conf";
		privatePath         = _rootPath + "/private";
		publicPath          = _rootPath + "/public";
		signedKeysPath      = _rootPath + "/signed-keys";
		configFilePath      = configPath  + "/rootCA.cfg";
		indexFilePath       = configPath  + "/index";
		serialFilePath      = configPath  + "/serial";
		privateKeyFilePath  = privatePath + "/rootCA.key";
		publicKeyFilePath   = publicPath  + "/rootCA.key";
		pemCertFilePath 	= privatePath + "/rootCA.pem.crt";
		derCertFilePath 	= privatePath + "/rootCA.der.crt";
		p7bCertFilePath 	= privatePath + "/rootCA.p7b.crt";
		hashCertFilePath 	= privatePath + "/0000.0";
	}
	
}
