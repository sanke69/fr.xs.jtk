package fr.xs.jtk.system.base;

public interface NativeServiceInterface {

	public String getName();
	public boolean isServiceExist();
	public boolean isServiceAlive();
	
	public boolean start();
	public boolean stop();
	public boolean restart();

}
