package fr.xs.jtk.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SecureSocket {
	private Socket				socket;
	
	// Gestion Donn�e brute
	private DataInputStream		in;
	private DataOutputStream	out;
	
	// Gestion Object
	private ObjectInputStream	oin;
	private ObjectOutputStream	oout;

	public SecureSocket(Socket _socket) {
		// Constructor used by the Server Application Part
		try {
			socket = _socket;
			out    = new DataOutputStream(socket.getOutputStream());
			in     = new DataInputStream(socket.getInputStream());
			oout   = new ObjectOutputStream(socket.getOutputStream());
        	oin    = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("IOException in SecureSocket");
		}
	}
	public SecureSocket(String _addr, int _port) {
		// Constructor used by the Client Application Part
		try {
			socket = new Socket(_addr, _port);
			in     = new DataInputStream(socket.getInputStream());
			out    = new DataOutputStream(socket.getOutputStream());
        	oin    = new ObjectInputStream(socket.getInputStream());
			oout   = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("Server not found");
		} catch (IOException e) {
			if(e.getLocalizedMessage().contains("Connection refused"))
				System.out.println("Connection has been refused");
		}
	}
	
	public boolean isConnected() {
		return socket != null;
	}

	public Socket asSocket() {
		return socket;
	}
	public void close() throws IOException {
		socket.close();
	}

	public void send(byte[] _stream) {
		try {
			out.write(_stream);
			out.flush();
		} catch (IOException e) {
			;
		} catch(NullPointerException e) {
			;
		}
	}
	public byte[] receive() {
		byte[] stream = null;
		try {
			stream = new byte[in.available()];
			in.readFully(stream);
		} catch(java.net.SocketTimeoutException e) {
			;
		} catch (IOException e) {
			;
		} catch(NullPointerException e) {
			;
		}

		return stream;
	}

	public void sendObject(Object _o) {
		try {
			oout.writeObject(_o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Object receiveObject() {
        Object o = null;
        
        try {
        	o = (Object) oin.readObject();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        return o;
	}

}
