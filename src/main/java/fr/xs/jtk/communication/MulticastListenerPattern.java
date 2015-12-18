package fr.xs.jtk.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import fr.xs.jtk.communication.interfaces.IMulticastListener;

public abstract class MulticastListenerPattern implements IMulticastListener {	
	private Thread			m_ListenerThread		= null;
	private MulticastSocket	m_Socket    			= null;
	private InetAddress	 	m_Addr      			= null;
	private int			 	m_Port      			= 0;
	private boolean			m_IsRunning				= false;
	
	MulticastListenerPattern(String _addr, int _port) {
		try {
			m_Addr = InetAddress.getByName(_addr);
			m_Port = _port;
		} catch (UnknownHostException e) { ; }
	}
	
	public void startListening() {
		m_IsRunning	= true;

		m_ListenerThread = new Thread( new Runnable() {

			public void run() {
				byte[] 			stream = null;
				DatagramPacket  packet = null;
				
				ByteArrayInputStream baiStream = null;
				ObjectInputStream    oiStream = null;

				try {
					m_Socket = new MulticastSocket(m_Port);
					m_Socket.joinGroup(m_Addr);

					while(m_IsRunning) {
						
						stream = new byte[m_Socket.getReceiveBufferSize()];
						packet = new DatagramPacket(stream, stream.length);

						m_Socket.receive(packet);

						baiStream = new ByteArrayInputStream(stream);
						oiStream  = new ObjectInputStream(baiStream);

						final Object o = oiStream.readObject();
						
						Thread traitment = new Thread() { public void run() { onReceivedData(o); } };
						traitment.start();
						
						Thread.sleep(0);
					}
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch(InterruptedException e0) {
					Thread.currentThread().interrupt();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					if(baiStream != null)
						onReceivedData(baiStream);
				}
			}
	
		});
		m_ListenerThread.start();

	}

	public void stopListening() {
		m_IsRunning	= false;

		m_ListenerThread.interrupt();
		
		if(m_Socket.isBound()) {
			try {
				m_Socket.wait();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			m_Socket.close();
		}
			
	}

}

