package Project_Server_screen;

import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Screen_Server {
	
	// server socket
	ServerSocket srvsocket = null;
	
	// accepted socket
	Socket consocket = null;
	Socket readersocket = null;
	
	// Screen�� port ��ȣ
//	static String ip = "182.227.201.125";
	static String ip = "127.0.0.1";

	static int screen_port = 5556;
	
	// �� ���� vector
	Vector client = new Vector();
	
	// thread�� �����ڷ� �־��־� ���� ����, reader������ ȭ�� ĸ�ĺ� �ޱ�.
	boolean readerflag = false;
	
	public void Screen()
	{
		try
		{
//			srvsocket = new ServerSocket();
//			srvsocket.bind(new InetSocketAddress(ip, screen_port));
			
			srvsocket = new ServerSocket(screen_port);
			
//			System.out.println("ȣ��Ʈ address : "+srvsocket.getInetAddress()+
//					" port : "+screen_port+" �� ���ε� �Ǿ����ϴ�.");
		
			
			while(true)
			{
				ScreenThread service;
				
				consocket = srvsocket.accept();
							
				if(!(client.size() == 0 && readerflag == false))
				{					
					service = new ScreenThread(consocket, this, false);
				}
				else
				{
					readersocket = consocket;
									
					readerflag = true;
									
					service = new ScreenThread(readersocket, this, true);
				}
				
				service.start();
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		finally
		{
			/*
			 *  �ʱ�ȭ �������.
			 */
			
			try
			{
				if(consocket != null)
					consocket.close();
				if(readersocket != null)
					readersocket.close();
				if(srvsocket != null)
					srvsocket.close();
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
		}
	}
	
	public synchronized void addClient(ScreenThread s)
	{
		client.add(s);
//		System.out.println("���ο� Ŭ���̾�Ʈ�� �����߽��ϴ�. ���� " + client.size() + "�� ������ �Դϴ�.");
	}
	
	public synchronized void broadcast(BufferedImage s)
	{
		for(int i = 0 ; i < client.size(); ++i)
		{
			ScreenThread scr = (ScreenThread) client.elementAt(i);
			scr.sendScreen(s);
		}
	}
	
	public synchronized void removeClient(ScreenThread s)
	{
		client.remove(s);
//		System.out.println("Ŭ���̾�Ʈ�� �������ϴ�. ���� " + client.size() + "�� ������ �Դϴ�.");

		if(client.size() == 0)
			readerflag = false;
	}
	
	public synchronized int getClientSize()
	{
		return client.size();
	}
	
	public static void main(String[] args)
	{
		Screen_Server server = new Screen_Server();
		
		server.Screen();
	}

}
