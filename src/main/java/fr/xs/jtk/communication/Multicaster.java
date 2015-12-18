package fr.xs.jtk.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Multicaster {
	InetAddress     m_Addr   = null;
	int             m_Port   = 0;
	
	MulticastSocket	m_Socket = null;
	
	Multicaster(String _target, int _port) {
		try {
			m_Addr   = InetAddress.getByName(_target);
			m_Port   = _port;
			
			m_Socket = new MulticastSocket(m_Port);
		} catch (UnknownHostException e) { 
			; 
		} catch(IOException e) {
			;
		}
	}
	
	public void setMulticastAddress(String _addr) {
		;
	}
	public void setMulticastPort(int _port) {
		;
	}

	public void send(Object _o) {
		try {
			ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
			ObjectOutputStream    ooStream  = new ObjectOutputStream(baoStream);
			ooStream.writeObject(_o);

			m_Socket.send(new DatagramPacket(baoStream.toByteArray(),
											 baoStream.toByteArray().length, 
											 m_Addr, m_Port));

		} catch(SocketException e) {
			;
		} catch(IOException e) {
			;
		}

		m_Socket.close();
	}

}
