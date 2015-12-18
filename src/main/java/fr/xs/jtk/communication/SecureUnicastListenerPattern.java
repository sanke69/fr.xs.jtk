package fr.xs.jtk.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;

import fr.xs.jtk.communication.interfaces.ISecureUnicastListener;

public abstract class SecureUnicastListenerPattern implements ISecureUnicastListener {

	private Thread			m_ListenerThread		= null;
	private ServerSocket 	m_Socket 				= null;
	private int				m_Port      			= 0;
	private boolean			m_IsRunning				= false;
	
	public SecureUnicastListenerPattern(int _port) {
		m_Port = _port;
	}

	public void startListening() {
		try {
			m_Socket = new ServerSocket(m_Port);
			
			while(m_IsRunning) {
				final Socket socket = m_Socket.accept();
				
				Thread processing = new Thread( new Runnable() {

					public void run() {
						final Socket incoming = socket;
						
						ByteArrayInputStream baiStream = null;
						ObjectInputStream    oiStream  = null;

						try {
							final byte[] stream = new byte[incoming.getReceiveBufferSize()];
							
							incoming.getInputStream().read(stream);

							baiStream = new ByteArrayInputStream(stream);
							oiStream  = new ObjectInputStream(baiStream);
							
							oiStream.readFully(stream);
							
							final Object o = oiStream.readObject();

							Thread traitment = new Thread() { public void run() { onReceivedData(incoming, o); } };
							traitment.start();
							
							Thread.sleep(0);


						} catch(SocketException e1) {
							e1.printStackTrace();
						} catch(IOException e) {
							e.printStackTrace();
						} catch(InterruptedException e) {
							Thread.currentThread().interrupt();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							if(baiStream != null)
								onReceivedData(incoming, baiStream);
						}

					}
					
				});
				processing.start();

			}

		} catch(SocketException _e) {
			;
		} catch(IOException _e1) {
			;
		}
	}
	
	public void stopListening() {
		m_IsRunning	= false;

		m_ListenerThread.interrupt();
		
		if(m_Socket.isBound()) {
			try {
				m_Socket.wait();
				m_Socket.close();
			} catch(InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
			
	}

}
