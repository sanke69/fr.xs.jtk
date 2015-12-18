package fr.xs.jtk.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.xs.jtk.bytes.ByteStreams;

/**
 * Provide methods to facilitate media/resources management 
 * in different JAVA context.
 * 
 * @author Steve PECHBERTI
 *
 */
public class MediaHelper {

	public static Collection<String> getDirectories(String _path) {
		Collection<String> directories = new ArrayList<String>();

		File[] listOfFiles = new File(_path).listFiles();

		if(listOfFiles != null)
			for(File f : listOfFiles)
				if(f.isDirectory())
					directories.add(f.getName());
		
		return directories;
	}
	public static Collection<String> getFiles(String _path) {
		Collection<String> files = new ArrayList<String>();

		File[] listOfFiles = new File(_path).listFiles();

		if(listOfFiles != null)
			for(File f : listOfFiles)
				if(f.isFile())
					files.add(f.getName());

		return files;
	}
	public static Collection<String> getFiles(String _path, String... _ext) {
		Collection<String> files = new ArrayList<String>();

		File[] listOfFiles = new File(_path).listFiles();

		if(listOfFiles != null)
			for(File f : listOfFiles)
				if(f.isFile()) {
					String path = f.getAbsolutePath(); 
					String ext  = path.substring(path.lastIndexOf(".") + 1);
					for(String extInc : _ext)
						if(ext.equalsIgnoreCase(extInc))
							files.add(f.getName());
				}

		return files;
	}

	public static int getNumberOfLine(String _content) throws IOException {
	    InputStream is = new ByteArrayInputStream(_content.getBytes());
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean endsWithoutNewLine = false;
	        while ((readChars = is.read(c)) != -1) {
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n')
	                    ++count;
	            }
	            endsWithoutNewLine = (c[readChars - 1] != '\n');
	        }
	        if(endsWithoutNewLine) {
	            ++count;
	        } 
	        return count;
	    } finally {
	        is.close();
	    }
	}

	public static String convertToString(InputStream _stream) throws IOException {
		byte[] bytes = new byte[_stream.available()];
		_stream.read(bytes);
		return new String(bytes);
	}

	private static List<String> searchPaths;
	static {
		searchPaths = new ArrayList<String>();

		final String dataPath;
		switch(OSHelper.get()) {
		case WINDOWS : dataPath = "H:/home/media/DATA"; break;
		case LINUX   : dataPath = "/media/sanke/home_usb/home/media/DATA"; break;
		case ANDROID : 
		case OSX     : 
		case SOLARIS : 
		case UNKNOWN : 
		default      : dataPath = null; break;
		}

		MediaHelper.addToSearchPath(dataPath);
		MediaHelper.addToSearchPath(dataPath + "/3D");
		MediaHelper.addToSearchPath(dataPath + "/3D/_3D_Universe");
	}

	public static void addToSearchPath(String _path) {
		String path = _path.codePointAt(_path.length() - 1) != '/' ? _path + "/" : _path;
		if(!searchPaths.contains(path))
			searchPaths.add(path);
	}
	public static void removeFromSearchPath(String _path) {
		String path = _path.codePointAt(_path.length() - 1) != '/' ? _path + "/" : _path;
		if(searchPaths.contains(path))
			searchPaths.remove(path);
	}

	public static URL getURLForMedia(String _partialPath) {
		URL url = null;
		if((url = getURLForFile(_partialPath)) != null)
			return url;
		if((url = getURLForResource(_partialPath)) != null)
			return url;

		return null;
	}
	public static URL getURLForFile(String _filename) {
		try {
			if(new File(_filename).exists())
				return new URL("file://" + _filename);

			String filename = _filename.codePointAt(0) == '/' ? _filename.substring(1) : _filename;

			for(String path : searchPaths) {
				File f = new File(path + filename);
				if(f.exists())
					return new URL("file://" + path + filename);
			}
		} catch(MalformedURLException e) { e.printStackTrace(); }

		return null;
	}
	public static URL getURLForResource(String _partialPath) {
		URL url = MediaHelper.class.getResource(_partialPath);

		if(url == null)
			url = MediaHelper.class.getResource("." + _partialPath);
		if(url == null)
			url = MediaHelper.class.getResource("/" + _partialPath);
		if(url == null)
			url = MediaHelper.class.getResource("./" + _partialPath);
		if(url == null)
			url = MediaHelper.class.getResource("../" + _partialPath);
		if(url == null)
			url = MediaHelper.class.getClassLoader().getResource(_partialPath);
		if(url == null)
			url = MediaHelper.class.getClassLoader().getResource("." + _partialPath);
		if(url == null)
			url = MediaHelper.class.getClassLoader().getResource("/" + _partialPath);
		if(url == null)
			url = MediaHelper.class.getClassLoader().getResource("./" + _partialPath);
		if(url == null)
			url = MediaHelper.class.getClassLoader().getResource("../" + _partialPath);

		return url;
	}
	public static URL getURLForResource(Class<?> _class, String _partialPath) {
		URL url = _class.getResource(_partialPath);

		if(url == null)
			url = _class.getResource("." + _partialPath);
		if(url == null)
			url = _class.getResource("/" + _partialPath);
		if(url == null)
			url = _class.getResource("./" + _partialPath);
		if(url == null)
			url = _class.getResource("../" + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource(_partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("." + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("/" + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("./" + _partialPath);
		if(url == null)
			url = _class.getClassLoader().getResource("../" + _partialPath);

		return url;
	}

    public static InputStream getContent(String _path) {
		try {
			return new FileInputStream(new File(_path));
		} catch(Exception e) {
			URL url = getURLForFile(_path);
			String path = url.getPath();
			try {
				return new FileInputStream(new File(path));
			} catch(Exception e2) {
				return null;
			}
		}
    }
    public static byte[]      getContentAsByteArrays(String _path) {
    	byte[] bytes;
		try {
			FileInputStream fis = new FileInputStream(_path);
	        DataInputStream dis = new DataInputStream(fis);
	        bytes = new byte[dis.available()];
	        dis.readFully(bytes);
	        dis.close();
		} catch(IOException e) { return null; }

        return bytes;
	}
	public static String      getContentAsString(String _path) {
		byte[] data = getContentAsByteArrays(_path);
		if(data == null) return null;
		return new String(data);
	}

    public static InputStream getContent(Class<?> _class, String _path) {
		return getContent( getURLForResource(_class, _path) );
    }
    public static byte[]      getContentAsByteArrays(Class<?> _class, String _path) {
		return getContentAsByteArrays( getURLForResource(_class, _path) );
	}
	public static String      getContentAsString(Class<?> _class, String _path) {
		return getContentAsString( getURLForResource(_class, _path) );
	}

	public static InputStream getContent(URL _url) {
		InputStream is = null;
		try { is = _url.openStream(); } catch(Exception e) { is = null; }
		return is;
	}
	public static byte[]      getContentAsByteArrays(URL _url) {
		InputStream is = getContent(_url);
		if(is == null) return null;
		return ByteStreams.toByteArray(is);
	}
	public static String      getContentAsString(URL _url) {
		byte[] data = getContentAsByteArrays(_url);
		if(data == null) return null;
		return new String(data);
	}

	public static boolean isExist(String _partialPath) {
		return getURLForMedia(_partialPath) != null;
	}
	public static boolean isExist(URL _partialPath) {
		return new File(_partialPath.getPath()).exists();
	}
	
	public static void download(URL _url, URL _dest) {
		try {
			ReadableByteChannel rbc = Channels.newChannel(_url.openStream());
			FileOutputStream fos = new FileOutputStream(_dest.getPath());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static InputStream download(URL _url) {
	    ByteBuffer            tmp = ByteBuffer.wrap(new byte[65635 * 1024]);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			ReadableByteChannel rbc = Channels.newChannel(_url.openStream());

			int tmpSize = 0;
			while((tmpSize = rbc.read(tmp)) > -1)
				out.write(tmp.array(), 0, tmpSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public static boolean createAsciiFile(String _path, String _content) {
		BufferedWriter writer = null;

		try {
		    writer = new BufferedWriter(new FileWriter(_path));
		    writer.write(_content);
		} catch ( IOException e) { return false;
		} finally {
		    try {
		        if(writer != null)
		        	writer.close( );
		    } catch ( IOException e) { return false; }
		}
		return true;
	}

}
