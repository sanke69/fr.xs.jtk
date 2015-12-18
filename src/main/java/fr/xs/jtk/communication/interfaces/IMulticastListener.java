package fr.xs.jtk.communication.interfaces;

import java.io.ObjectInputStream;

public interface IMulticastListener {
	
	public void setMulticastGroup(String _addr);
	public void setMulticastPort(int _port);

	public void startListening();
	public void stopListening();

	public void onReceivedData(Object _o);
	public void onReceivedData(ObjectInputStream _o);

}

