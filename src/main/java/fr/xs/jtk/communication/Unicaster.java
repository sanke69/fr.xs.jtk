package fr.xs.jtk.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Unicaster {
	DatagramSocket	m_Socket = null;
	InetAddress     m_Addr   = null;
	int             m_Port   = 0;

	Unicaster(String _addr, int _port) {
		try {
			m_Addr  = InetAddress.getByName(_addr);
			m_Port = _port;

			m_Socket = new DatagramSocket();
		} catch (UnknownHostException e) { 
			m_Socket = null; 
		} catch(SocketException e) {
			m_Socket.close();
			m_Socket = null;
		}
	}

	public void setBroadcastAddress(String _addr) {
		;
	}
	public void setBroadcastPort(int _port) {
		;
	}

	public void send(Object _o) {
		if(m_Socket != null) {
			try {
				ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
				ObjectOutputStream    ooStream  = new ObjectOutputStream(baoStream);
				ooStream.writeObject(_o);
	
				m_Socket.send(new DatagramPacket(baoStream.toByteArray(),
												 baoStream.toByteArray().length, 
												 m_Addr, m_Port));

			} catch(SocketException e) {
				;
			}  catch(IOException e) {
				;
			}
		}
		
		m_Socket.close();
	}

}
