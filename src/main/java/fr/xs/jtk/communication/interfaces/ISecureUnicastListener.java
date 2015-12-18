package fr.xs.jtk.communication.interfaces;

import java.io.ObjectInputStream;
import java.net.Socket;

public interface ISecureUnicastListener {

	public void setSecureUnicastPort(int _port);

	public void startListening();
	public void stopListening();

	public void onReceivedData(Socket _from, Object _o);
	public void onReceivedData(Socket _from, ObjectInputStream _o);

}
