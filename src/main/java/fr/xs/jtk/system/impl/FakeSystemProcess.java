package fr.xs.jtk.system.impl;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.xs.jtk.system.base.SystemProcessInterface;
import fr.xs.jtk.tools.Debugger;

public class FakeSystemProcess implements SystemProcessInterface {

	@Override
	public boolean isExist(String _path) {
		Debugger.log(500, "Pure Java implementation");
		return new File(_path).exists();
	}

	@Override
	public boolean isFile(String _path) {
		Debugger.log(500, "Pure Java implementation");
		return new File(_path).isFile();
	}

	@Override
	public boolean isDirectory(String _path) {
		Debugger.log(500, "Pure Java implementation");
		return new File(_path).isDirectory();
	}

	@Override
	public boolean touchFile(String _path) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean copyFile(String _from, String _to) {
		Debugger.log(500, "Java NIO2 implementation");
		try {
			Files.copy(Paths.get(_from), Paths.get(_to), REPLACE_EXISTING);
		} catch (IOException e) { return false; }
		return true;
	}

	@Override
	public boolean moveFile(String _from, String _to) {
		Debugger.log(500, "Java NIO2 implementation");
		try {
			Files.move(Paths.get(_from), Paths.get(_to), REPLACE_EXISTING);
		} catch (IOException e) { return false; }
		return false;
	}

	@Override
	public boolean createFile(String _path, String _content) {
		Debugger.log(500, "Pure Java implementation");
		try {
			PrintWriter out = new PrintWriter(_path);
			out.println(_content);
			out.close();
			return true;
		} catch(FileNotFoundException e) {
			return false;
		}
	}
	@Override
	public boolean createDirectory(String _path, boolean _recursive) {
		Debugger.log(500, "Java NIO2 implementation");
		try {
			Files.createDirectory(Paths.get(_path));
		} catch (IOException e) { return false; }
		return false;
	}

	@Override
	public boolean writeFile(String _path, String _content) {
		Debugger.log(500, "Pure Java implementation");
		try {
			PrintWriter out = new PrintWriter(_path);
			out.println(_content);
			out.close();
			return true;
		} catch(FileNotFoundException e) {
			return false;
		}
	}
	@Override
	public boolean replaceInFile(String _path, String _find, String _repl, boolean _all) {
		Debugger.log(500, "Pure Java implementation");
		Path path = Paths.get(_path);
		Charset charset = StandardCharsets.UTF_8;

		String content;
		try {
			content = new String(Files.readAllBytes(path), charset);
			content = _all ? content.replaceAll(_find, _repl) : content.replace(_find, _repl); // TODO:: replace & replaceAll same issue here!
			Files.write(path, content.getBytes(charset));
		} catch(IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public String getHostname() {
		Debugger.log(500, "Fake implementation");
		return null;
	}

	@Override
	public boolean download(String _url, String _localPath) {
		Debugger.log(500, "Java implementation");
		try {
			URL url = new URL(_url);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(new File(_localPath));

			int read = 04;
			byte[] bytes = new byte[65635];

			Debugger.append(500, "java dl");
			while ((read = is.read(bytes)) != -1) {
				os.write(bytes, 0, read);
				Debugger.append(500, ".");
			}
			Debugger.append(500, "\n");
			
			os.close();
			is.close();
		} catch(IOException e) { return false; }
		return false;
	}

	@Override
	public boolean untar(String _archivePath, String _extractPath, boolean _cut_root_dir) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean addCrontabTask(int _minute, int _hour, int _dayOfMonth, int _month, int _dayOfWeek, String _task) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

}
