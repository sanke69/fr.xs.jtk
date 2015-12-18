package fr.xs.jtk.communication.interfaces;

import java.io.ObjectInputStream;

public interface IUnicastListener {

	public void setBroadcastPort(int _port);

	public void startListening();
	public void stopListening();

	public void onReceivedData(Object _o);
	public void onReceivedData(ObjectInputStream _o);

}
