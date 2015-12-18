package fr.xs.jtk.communication;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SecureUnicaster {
	Socket 			m_Socket = null;
	InetAddress     m_Addr   = null;
	int             m_Port   = 0;
	
	SecureUnicaster(String _addr, int _port) {
		try {
			m_Addr = InetAddress.getByName(_addr);
			m_Port   = _port;
		} catch (UnknownHostException e) { ; }
	}

	public void send(byte[] _o) {
		try {
			m_Socket = new Socket(m_Addr, m_Port);

			OutputStream stream = new PrintStream(m_Socket.getOutputStream());
			stream.write(_o);

			m_Socket.close();
		} catch(IOException e) { ; }
	}
}
