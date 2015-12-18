package fr.xs.jtk.security;

import fr.xs.jtk.security.certificate.ImportKey;
import fr.xs.jtk.security.certificate.OpenSSL;
import fr.xs.jtk.security.certificate.bean.CertificateAuthorityBean;
import fr.xs.jtk.security.certificate.bean.CertificateIdentityBean;
import fr.xs.jtk.security.certificate.bean.CertificateUserProfileBean;
import fr.xs.jtk.system.SystemProcess;

public class CertificatesGenerator {

	public static final String projectDirectory    = "/media/sanke/home_usb/home/sanke/projects.dir/Java/SPWeb/fr.xs.spweb.coresite";
	public static final String pathToCertification = projectDirectory + "/src/certificates";
	public static final String copyToDirectory     = projectDirectory + "/src/main/webapp/certificates";

	public static void main(String... argv) {
		OpenSSL ssl = new OpenSSL();
		
		CertificateIdentityBean root = new CertificateIdentityBean();
		root.commonName       = "Steve Pechberti SA Root CA";
		root.emailAddress     = "root_authority@sp-web.fr";
		root.organisation     = "Software Manufacture";
		root.organisationUnit = "Direction";
		root.streetAddress    = "98, Boulevard de la Reine";
		root.location         = "Versailles";
		root.zipCode          = "78000";
		root.state            = "Ile de France";
		root.country          = "Fr";

		CertificateIdentityBean bean = new CertificateIdentityBean();
		bean.commonName       = "Steve PECHBERTI";
		bean.emailAddress     = "authority@sp-web.fr";
		bean.organisation     = "Digital Craftsman";
		bean.organisationUnit = "Research & Development";
		bean.streetAddress    = "98, Boulevard de la Reine";
		bean.location         = "Versailles";
		bean.zipCode          = "78000";
		bean.state            = "Ile de France";
		bean.country          = "Fr";

		CertificateIdentityBean mailserver = new CertificateIdentityBean();
		mailserver.commonName       = "mail.sp-web.fr";
		mailserver.emailAddress     = "mailmaster@sp-web.fr";
		mailserver.organisation     = "Digital Craftsman";
		mailserver.organisationUnit = "Research & Development";
		mailserver.streetAddress    = "98, Boulevard de la Reine";
		mailserver.location         = "Versailles";
		mailserver.zipCode          = "78000";
		mailserver.state            = "Ile de France";
		mailserver.country          = "Fr";
		
		CertificateAuthorityBean   ca     = new CertificateAuthorityBean(pathToCertification + "/rootCA");
		CertificateUserProfileBean user   = new CertificateUserProfileBean(pathToCertification + "/sanke");
		CertificateUserProfileBean server = new CertificateUserProfileBean(pathToCertification + "/mailserver");
		
		if(!SystemProcess.isExist(ca.rootPath))
			ssl.initializeRootCertificateAuthority(ca, root, 1024);

		if(!SystemProcess.isExist(user.rootPath)) {
			SystemProcess.createDirectory(user.rootPath, true);

			ssl.generatePrivateKey(user.privateKeyFilePath, 1024);
			ssl.generateCertificateSigningRequest(user.privateKeyFilePath, bean, user.certRequestFilePath);
			ssl.generatePublicKey(user.privateKeyFilePath, user.publicKeyFilePath);
			
			ssl.deliverSignedCertificate(ca.configFilePath, user.certRequestFilePath, user.pemCertFilePath);
			ssl.convertCertificate_PEMtoDER(user.pemCertFilePath, user.derCertFilePath);
			ssl.convertCertificate_PEMtoHASH(user.pemCertFilePath, user.derCertFilePath, user.rootPath);
			ssl.convertCertificate_PEMtoP7B(user.pemCertFilePath, user.p7bCertFilePath);

			ssl.convertKey_PEMtoDER(user.privateKeyFilePath, user.privateKeyDERFilePath);
			ImportKey.generateJavaKeyStore(user.privateKeyDERFilePath, user.derCertFilePath, user.jksFilePath);

			user.hashCertFilePath = user.hashCertFilePath.substring(0, user.hashCertFilePath.lastIndexOf("/") + 1) + ssl.getHashCertificateName(user.pemCertFilePath) + ".0";
		}

		if(!SystemProcess.isExist(server.rootPath)) {
			SystemProcess.createDirectory(server.rootPath, true);

			ssl.generatePrivateKey(server.privateKeyFilePath, 1024);
			ssl.generateCertificateSigningRequest(server.privateKeyFilePath, mailserver, server.certRequestFilePath);
			ssl.generatePublicKey(server.privateKeyFilePath, server.publicKeyFilePath);
			
			ssl.deliverSignedCertificate(ca.configFilePath, server.certRequestFilePath, server.pemCertFilePath);
			ssl.convertCertificate_PEMtoDER(server.pemCertFilePath, server.derCertFilePath);
			ssl.convertCertificate_PEMtoHASH(server.pemCertFilePath, server.derCertFilePath, server.rootPath);
			ssl.convertCertificate_PEMtoP7B(server.pemCertFilePath, server.p7bCertFilePath);

			ssl.convertKey_PEMtoDER(server.privateKeyFilePath, server.privateKeyDERFilePath);

			server.hashCertFilePath = server.hashCertFilePath.substring(0, server.hashCertFilePath.lastIndexOf("/") + 1) + ssl.getHashCertificateName(server.pemCertFilePath) + ".0";
		}

		if(!SystemProcess.isExist(copyToDirectory)) {
			SystemProcess.createDirectory(copyToDirectory, true);
			SystemProcess.copyFile(user.pemCertFilePath,  copyToDirectory + "/sanke-signed.pem");
			SystemProcess.copyFile(ca.pemCertFilePath,    copyToDirectory + "/sanke-ca.pem");
			SystemProcess.copyFile(ca.hashCertFilePath,   copyToDirectory + ca.hashCertFilePath.substring(ca.hashCertFilePath.lastIndexOf("/")));
			SystemProcess.copyFile(user.hashCertFilePath, copyToDirectory + user.hashCertFilePath.substring(user.hashCertFilePath.lastIndexOf("/")));

		}

		SystemProcess.createFile("/tmp/test.orig", "HelloWorld");
		ssl.encodeFile(user.publicKeyFilePath, "/tmp/test.orig", "/tmp/test.encoded");
		ssl.decodeFile(user.privateKeyFilePath, "/tmp/test.encoded", "/tmp/test.decoded");

	}
	
}
