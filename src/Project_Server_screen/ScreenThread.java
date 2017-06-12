package Project_Server_screen;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ScreenThread extends Thread{
	
	// clinet 저장할 소켓
	Socket clientSocket = null;
	
	// reader 저장할 소켓, reader에게만 받음
	Socket readerSocket = null;
	
	// 관리할 Screen_Server 객체
	Screen_Server xServer = null;
	
	// reader 인지
	boolean readerflag = false;
	
	// 입출력
	BufferedInputStream in = null;
	BufferedWriter out = null;
	BufferedOutputStream bout = null;

	public ScreenThread(Socket sock, Screen_Server srv, boolean readerflag)
	{
		super();
		
		this.readerflag = readerflag;

		if(readerflag == false)
		{
			clientSocket = sock;
			xServer = srv;
		}
		else
		{
			readerSocket = sock;
			xServer = srv;
		}
		
	}
	
	public synchronized void sendScreen(BufferedImage buf)
	{
		try
		{
			if(bout != null)
				ImageIO.write(buf , "BMP", bout);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	public void run()
	{
		try
		{
			if(!readerflag)
			{
				in = new BufferedInputStream(clientSocket.getInputStream());
				bout = new BufferedOutputStream(clientSocket.getOutputStream());

				
				xServer.addClient(this);
				
				int str;
				
				while((str = in.read())!=0)
				{
					
				}
			}
			else
			{
				in = new BufferedInputStream(readerSocket.getInputStream());
				out = new BufferedWriter(new OutputStreamWriter(readerSocket.getOutputStream()));
				bout = new BufferedOutputStream(readerSocket.getOutputStream());
				
				
				xServer.addClient(this);
				
				BufferedImage buf = null;
						
				while(readerSocket != null)
				{
					buf = ImageIO.read(ImageIO.createImageInputStream(in));	
				
					// 예외 처리용
					if(buf == null)
					{ 
						break;
					}

					xServer.broadcast(buf);
						
					Thread.sleep(100);
				}
				
				
				/*
				 *  Screen 읽음
				 */
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
				if(bout != null)
					bout.close();
				if(readerSocket != null)
					readerSocket.close();
				if(clientSocket != null)
					clientSocket.close();
				
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
		}
		
	}
}
