package fr.xs.jtk.system.impl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import fr.xs.jtk.system.base.RootProcessInterface;
import fr.xs.jtk.tools.Debugger;

public class FakeRootProcess implements RootProcessInterface {

	@Override
	public boolean isUserExist(String _username) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean createUser(int _id, String _name, String _group, String _home) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean createGroup(int _id, String _name) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean changeUserGroup(String _user, String _group) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean addUserToSecondariesGroup(String _user, String... _groups) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean changeOwner(String _path, String _user, String _group, boolean _recursive) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean changeAccess(String _path, int _OGA, boolean _recursive) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean createFile(String _path, String _content) {
		Debugger.log(500, "Pure Java implementation - Not Root Certified!");
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
	public boolean createDirectory(String _path) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean setInstallProperties(String _package, String _section, String _key, String _value) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean installPackage(String _package) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

	@Override
	public boolean installPackages(String... _packages) {
		Debugger.log(500, "Fake implementation");
		return false;
	}

}
