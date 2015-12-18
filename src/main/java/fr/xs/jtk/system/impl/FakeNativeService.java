package fr.xs.jtk.system.impl;

import java.io.File;

import fr.xs.jtk.system.NativeProcess;
import fr.xs.jtk.system.base.NativeServiceInterface;

public class FakeNativeService implements NativeServiceInterface {
	String serviceName;
	String binPath;
	
	protected Process serviceProcess;
	
	public FakeNativeService(String _serviceName, String _binPath) {
		serviceName = _serviceName;
		binPath = _binPath;
	}

	public String getBinaryPath() {
		return binPath;
	}
	public void setBinaryPath(String _binPath) {
		binPath = _binPath;
	}

	@Override
	public String getName() {
		return serviceName;
	}

	@Override
	public boolean isServiceExist() {
		return new File(binPath).exists();
	}

	@Override
	public boolean isServiceAlive() {
		return serviceProcess != null;
	}

	@Override
	public boolean start() {
		serviceProcess = NativeProcess.runtime().exec(binPath);
		return serviceProcess != null;
	}

	@Override
	public boolean stop() {
		if(serviceProcess == null)
			return false;

		serviceProcess.destroy();
		if(!serviceProcess.isAlive()) {
			serviceProcess = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean restart() {
		return false;
	}

}
