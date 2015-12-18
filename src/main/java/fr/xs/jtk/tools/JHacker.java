package fr.xs.jtk.tools;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JHacker {

	public static class ClassPath {
		/* see URLClassLoader SecureClassLoader */
		private static final Class<?>[] parameters = new Class[] { URL.class };

		private static String rootPath;
		private static HashSet<String> classPathElements;
		private static String classPath;

		private static boolean debug = false;

		static {
			rootPath = "";
			classPath = "";
			classPathElements = new HashSet<String>();
		}

		public static void addClassPath(String[] _cps) {
			for(String cp : _cps)
				addClassPath(cp);
		}
		public static void addClassPath(String cp) {
			String seps = File.pathSeparator;

			if (!File.pathSeparator.equals(";"))
				seps += ";";

			for (StringTokenizer st = new StringTokenizer(cp, seps, false); st.hasMoreTokens();) {
				String pe = st.nextToken();
				File fe;
				String bn = null;

				if (pe.length() == 0)
					continue;

				fe = new File(pe);

				if (fe.getName().indexOf('*') != -1) {
					bn = fe.getName();
					fe = fe.getParentFile();
				}

				if (!fe.isAbsolute() && pe.charAt(0) != '\\')
					fe = new File(rootPath, fe.getPath());

				try {
					fe = fe.getCanonicalFile();
				} catch (IOException thr) {
					if (debug)
						System.out.println("Skipping non-existent classpath element '" + fe + "' (" + thr + ").");
					continue;
				}

				if (bn != null)
					if (bn.length() != 0)
						fe = new File(fe, bn);

				if (classPathElements.contains(fe.getPath())) {
					if (debug)
						System.out.println("Skipping duplicate classpath element '" + fe + "'.");
					continue;
				} else {
					classPathElements.add(fe.getPath());
				}

				if (bn != null) {
					if (bn.length() != 0)
						addJars(fe.getParentFile(), bn);
				} else if (!fe.exists()) {
					if (debug)
						System.out.println("Could not find classpath element '" + fe + "'");
				} else if (fe.isDirectory()) {
					addURL(createUrl(fe));
				} else if (fe.getName().toLowerCase().endsWith(".zip") || fe.getName().toLowerCase().endsWith(".jar") || fe.getName().toLowerCase().endsWith(".war")) {
					addURL(createUrl(fe));
				} else {
					if (debug)
						System.out.println("ClassPath element '" + fe + "' is not an existing directory and is not a file ending with '.zip', '.jar' or '.war'");
				}
			}
			if (debug)
				System.out.println("Class loader is using classpath: \"" + classPath + "\".");
		}

		public static void addURL(URL aURL) {
			URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<?> sysclass = URLClassLoader.class;
			try {
				Method method = sysclass.getDeclaredMethod("addURL", parameters);
				method.setAccessible(true);
				method.invoke(sysloader, new Object[] { aURL });
			} catch (Throwable t) {
				t.printStackTrace();
				if (debug)
					System.out.println("Error adding " + aURL + " to system classloader");
			}
		}

		public static void addJars(File dir, String nam) {
			String[] jars; // matching jar files

			if (nam.endsWith(".jar"))
				nam = nam.substring(0, (nam.length() - 4));

			if (!dir.exists()) {
				if (debug)
					System.out.println("Could not find directory for Class Path element '" + dir + File.separator + nam + ".jar'");
				return;
			}
			if (!dir.canRead()) {
				if (debug)
					System.err.println("Could not read directory for Class Path element '" + dir + File.separator + nam + ".jar'");
				return;
			}

			if ((jars = getListOfFile(dir.getPath(), ".zip;.jar;.war")) == null) {
				if (debug)
					System.err.println("Error accessing directory for Class Path element '" + dir + File.separator + nam + ".jar'");
			} else if (jars.length == 0) {
				if (debug)
					System.out.println("No JAR files match specification '" + new File(dir, nam) + ".jar'");
			} else {
				if (debug)
					System.out.println("Adding files matching specification '" + dir + File.separator + nam + ".jar'");
				Arrays.sort(jars, String.CASE_INSENSITIVE_ORDER);
				for (int xa = 0; xa < jars.length; xa++) {
					addURL(createUrl(new File(dir, jars[xa])));
				}
			}
		}

		private static URL createUrl(File fe) {
			try {
				URL url = fe.toURI().toURL();
				if (debug)
					System.out.println("Added URL: '" + url.toString() + "'");
				if (classPath.length() > 0) {
					classPath += File.pathSeparator;
				}
				classPath += fe.getPath();
				return url;
			} catch (MalformedURLException thr) {
				if (debug)
					System.out.println("Classpath element '" + fe + "' could not be used to create a valid file system URL");
				return null;
			}
		}

		private static String[] getListOfFile(String _dir, String _ext) {
			ArrayList<String> ext_tic = new ArrayList<String>(); // Taken Into
																	// Account
			ArrayList<String> files = new ArrayList<String>();

			for (StringTokenizer st = new StringTokenizer(_ext, ";", false); st.hasMoreTokens();)
				ext_tic.add(st.nextToken());

			File folder = new File(_dir);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					for (String ext : ext_tic)
						if (listOfFiles[i].getName().endsWith(ext)) {
							files.add(listOfFiles[i].getName());
						}
				} else if (listOfFiles[i].isDirectory()) {
					String[] subs = getListOfFile(listOfFiles[i].getPath(), _ext);
					if (subs == null)
						continue;
					for (String file : subs)
						files.add(file);
				}
			}

			if (files.size() != 0) {
				String[] returnStrings = new String[files.size()];
				int i = 0;
				for (Object o : files)
					returnStrings[i++] = (String) o;
				return returnStrings;
			}
			return null;
		}

	}

	public static class Libraries  {

		public static void loadNativeLibrary(String _dllPath) {
			try {
				System.load(_dllPath);
			} catch (UnsatisfiedLinkError e) {
				System.err.println("Native code library [" + _dllPath + "] failed to load.\n" + e);
				System.exit(1);
			}
		}

		public static void addToLibraryPath(String _libPath) {
			System.getProperties().setProperty("java.library.path", System.getProperty("java.library.path") + ";" + _libPath);
		}

	}

	public static class SystemProperties {

		public static void setDefault() {
			Properties defaultSet = new Properties();
			System.setProperties(defaultSet);
		}

		public static void set(String[] _p) {
			Properties defaultSet = new Properties();
			for(int i = 0; i < _p.length / 2; ++i)
				defaultSet.setProperty(_p[2 * i], _p[2 * i + 1]);
			System.setProperties(defaultSet);
		}

		public static void set(Properties _p) {
			System.setProperties(_p);
		}

		public static void add(String _key, String _value) {
			System.getProperties().put(_key, _value);
		}
		public static void add(String[] _p) {
			for(int i = 0; i < _p.length / 2; ++i)
				System.getProperties().put(_p[2 * i], _p[2 * i + 1]);
		}
		public static void add(Properties _p) {
			Properties systemSet = System.getProperties();
			for(Object key : _p.keySet())
				systemSet.put(key, _p.getProperty((String) key));
			System.setProperties(systemSet);
		}

		public static void display() {
			System.out.println("         =============================================================");
			System.out.println("    ======================= System information ============================");
			System.out.println("===============================================================================");
			for (Enumeration<?> e = System.getProperties().keys(); e.hasMoreElements();) {
				String key   = (String) e.nextElement(), tab = "";
				int    nTab  = (int) ( (32 - key.length()) / 8.0 ) + ( (32 - key.length()) % 8 != 0 ? 1 : 0 );
				for(int i = 0; i < nTab; ++i) tab += '\t';
				System.out.println(key + tab + System.getProperty(key));
			}
			System.out.println("===============================================================================");
			System.out.println("    =======================================================================");
			System.out.println("         =============================================================");
		}

	}

	public static class Invoker {

		public static void main(String[] _args) {
			if (_args.length < 1)
				return ;
			
			Class<?>[] argTypes = new Class[1];
			argTypes[0] = String[].class;
			try {
				Method mainMethod = Class.forName(_args[0]).getDeclaredMethod("main", argTypes);
				Object[] argListForInvokedMain = new Object[1];
				argListForInvokedMain[0] = new String[0];
				mainMethod.invoke(null, argListForInvokedMain);
			} catch (ClassNotFoundException ex) {
				System.err.println("Class " + _args[0] + " not found in classpath.");
			} catch (NoSuchMethodException ex) {
				System.err.println("Class " + _args[0] + " does not define public static void main(String[])");
			} catch (InvocationTargetException ex) {
				System.err.println("Exception while executing " + _args[0] + ":" + ex.getTargetException());
			} catch (IllegalAccessException ex) {
				System.err.println("main(String[]) in class " + _args[0] + " is not public");
			}
		}

	}

	public static class SeparateProcessInvoker {

		public static Process main(String[] _args, String _classPath) {
			if (_args.length < 1)
				return null;
			Process process = null;
			try {
				String cmd = "java " + "-cp " + _classPath + " " + _args[0];
				System.out.println(cmd);
				process = Runtime.getRuntime().exec(cmd);
			} catch (java.io.IOException ex) {
				System.err.println("Problems invoking class " + _args[0] + ": " + ex);
			}
			return process;
		}

	}

	public static class KeyStore {

/**
 * Key format:
 * openssl pkcs8 -topk8 -nocrypt -in YOUR.KEY -out YOUR.KEY.der -outform der
 * 
 * Format of the certificate:
 * openssl x509 -in YOUR.CERT -out YOUR.CERT.der -outform der
 * 
 * Import key and certificate:
 * java comu.ImportKey YOUR.KEY.der YOUR.CERT.der
 * 
 * Caution: the old keystore.ImportKey-file is deleted and replaced with a
 * keystore only containing YOUR.KEY and YOUR.CERT. The keystore and the key has
 * no password; they can be set by the keytool -keypasswd-command for setting
 * the key password, and the keytool -storepasswd-command to set the keystore
 * password.
 * 
 * The key and the certificate is stored under the alias importkey; to change
 * this, use keytool -keyclone.
 * 
 * @author Joachim Karrer, Jens Carlberg
 * @version 1.1
 **/
		public static class ImportKey {

			private static InputStream fullStream(String fname) throws IOException {
				FileInputStream fis = new FileInputStream(fname);
				DataInputStream dis = new DataInputStream(fis);
				byte[] bytes = new byte[dis.available()];
				dis.readFully(bytes);
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				return bais;
			}

			public static void main(String args[]) {
				// change this if you want another password by default
				String keypass = "importkey";
				// change this if you want another alias by default
				String defaultalias = "importkey";
				// change this if you want another keystorefile by default
				String keystorename = System.getProperty("keystore");

				if (keystorename == null)
					keystorename = System.getProperty("user.home") + System.getProperty("file.separator") + "keystore.ImportKey"; // especially
																																	// this
																																	// ;-)

				// parsing command line input
				String keyfile = "";
				String certfile = "";
				if (args.length < 2 || args.length > 3) {
					System.out.println("Usage: java comu.ImportKey keyfile certfile [alias]");
					System.exit(0);
				} else {
					keyfile = args[0];
					certfile = args[1];
					if (args.length > 2)
						defaultalias = args[2];
				}

				try {
					// initializing and clearing keystore
					java.security.KeyStore ks = java.security.KeyStore.getInstance("JKS", "SUN");
					ks.load(null, keypass.toCharArray());
					System.out.println("Using keystore-file : " + keystorename);
					ks.store(new FileOutputStream(keystorename), keypass.toCharArray());
					ks.load(new FileInputStream(keystorename), keypass.toCharArray());

					// loading Key
					InputStream fl = fullStream(keyfile);
					byte[] key = new byte[fl.available()];
					KeyFactory kf = KeyFactory.getInstance("RSA");
					fl.read(key, 0, fl.available());
					fl.close();
					PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec(key);
					PrivateKey ff = kf.generatePrivate(keysp);

					// loading CertificateChain
					CertificateFactory cf = CertificateFactory.getInstance("X.509");
					InputStream certstream = fullStream(certfile);

					Collection c = cf.generateCertificates(certstream);
					Certificate[] certs = new Certificate[c.toArray().length];

					if (c.size() == 1) {
						certstream = fullStream(certfile);
						System.out.println("One certificate, no chain.");
						Certificate cert = cf.generateCertificate(certstream);
						certs[0] = cert;
					} else {
						System.out.println("Certificate chain length: " + c.size());
						certs = (Certificate[]) c.toArray();
					}

					// storing keystore
					ks.setKeyEntry(defaultalias, ff, keypass.toCharArray(), certs);
					System.out.println("Key and certificate stored.");
					System.out.println("Alias:" + defaultalias + " Password:" + keypass);
					ks.store(new FileOutputStream(keystorename), keypass.toCharArray());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}

/*
	      KeyStore ks2 = KeyStore.getInstance("jks");
	      ks2.load(null,"".toCharArray());
	      FileOutputStream out = new FileOutputStream("C:\\mykeytore.keystore");
	      ks2.store(out, "".toCharArray());
*/

	}

	public static class ModuleLoader {
		private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			assert classLoader != null;
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = classLoader.getResources(path);
			List<File> dirs = new ArrayList<File>();
			while(resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			ArrayList<Class> classes = new ArrayList<Class>();
			for(File directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
			return classes.toArray(new Class[classes.size()]);
		}

		private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
			List<Class> classes = new ArrayList<Class>();
			if(!directory.exists()) {
				return classes;
			}
			File[] files = directory.listFiles();
			for(File file : files) {
				if(file.isDirectory()) {
					assert !file.getName().contains(".");
					classes.addAll(findClasses(file, packageName + "." + file.getName()));
				} else if(file.getName().endsWith(".class")) {
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				}
			}
			return classes;
		}
		
		public static Collection<Class> getClassesForPackage(String packageName) throws Exception {
			  String packagePath = packageName.replace(".", "/");
			  ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			  Set<URL> jarUrls = new HashSet<URL>();

			  while (classLoader != null) {
			    if (classLoader instanceof URLClassLoader)
			      for (URL url : ((URLClassLoader) classLoader).getURLs())
			        if (url.getFile().endsWith(".jar"))  // may want better way to detect jar files
			          jarUrls.add(url);

			    classLoader = classLoader.getParent();
			  }

			  Set<Class> classes = new HashSet<Class>();

			  for (URL url : jarUrls) {
			    JarInputStream stream = new JarInputStream(url.openStream()); // may want better way to open url connections
			    JarEntry entry = stream.getNextJarEntry();

			    while (entry != null) {
			      String name = entry.getName();
			      int i = name.lastIndexOf("/");

			      if (i > 0 && name.endsWith(".class") && name.substring(0, i).equals(packagePath)) 
			        classes.add(Class.forName(name.substring(0, name.length() - 6).replace("/", ".")));

			      entry = stream.getNextJarEntry();
			    }

			    stream.close();
			  }

			  return classes;
			}
	}

	public static void main(String[] args) {
		JHacker.ClassPath.addClassPath("e:/prj/runtime/win32/eclipse-4.4/plugins/;e:/tmp/distr/");

//		JHacker.SystemProperties.setDefault(); // will failed !!!
		JHacker.SystemProperties.display();

		JHacker.Invoker.main(new String[] { "fr.java.model.HelloWorldAWT" });
//		JHacker.SeparateProcessInvoker.main(new String[] { "fr.java.model.HelloWorldAWT" }, "e:/prj/runtime/win32/eclipse-4.4/plugins/;e:/tmp/distr/");
	}

}
