package Voice_Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class VoiceThread extends Thread{

	// client socket
	Socket clientSocket = null;
	
	// reader socket
	Socket readerSocket = null;
	
	// Voice_Server object
	Voice_Server xServer = null;
	
	//whether reader
	boolean readerflag = false;
	
	// buffer stream
	BufferedInputStream in = null;
	BufferedOutputStream out = null;
	
	public VoiceThread(Socket sock, Voice_Server srv, boolean readerflag)
	{
		super();
		
		this.readerflag = readerflag;
		
		if(readerflag)
		{
			readerSocket = sock;
			xServer = srv;
		}
		else
		{
			clientSocket = sock;
			xServer = srv;
		}
	}
	
	public synchronized void sendVoice(byte[] inByte, int bytesRead)
	{
		try
		{
			out.write(inByte, 0, bytesRead);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public void run()
	{
		try
		{
			if(!readerflag)
			{
				out = new BufferedOutputStream(clientSocket.getOutputStream());
				in = new BufferedInputStream(clientSocket.getInputStream());
				
				xServer.addClient(this);
				
				int str;
				
				while((str = in.read()) != 0)
				{
					
				}
			}
			else
			{
				in = new BufferedInputStream(readerSocket.getInputStream());
				out = new BufferedOutputStream(readerSocket.getOutputStream());

				xServer.addClient(this);
				
				int bytesRead = 0;
				byte[] inBytes = new byte[1];
				
				while(bytesRead != -1)
				{
					/*
					 *  Send Voice
					 */
					
					bytesRead = in.read(inBytes, 0, inBytes.length);
					
					if(bytesRead >= 0)
					{
						xServer.broadcast(inBytes, bytesRead);
					}
					
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		finally
		{
			try
			{
				xServer.removeClient(this);
				
				if(in != null)
					in.close();
				if(out != null)
					out.close();
				if(clientSocket != null)
					clientSocket.close();
				if(readerSocket != null)
					readerSocket.close();
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
		}
	}
}
