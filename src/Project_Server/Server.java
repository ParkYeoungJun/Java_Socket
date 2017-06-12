package Project_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Server {
	
	// initial
	ServerSocket srvsocket = null;
	
	Socket consocket = null;
	Socket readersocket = null;
//	static String ip = "192.168.0.4";
//	static String ip = "182.227.201.125";
	static String ip = "127.0.0.1";
//	static String ip = "192.168.25.103";
	static int chat_port = 5555;
//	static int screen_port = 5556;
	
	// client��� ������ ����
	Vector client = new Vector();
	Vector client_name = new Vector();
	
	
	// reader���� ������ ����
	PrintWriter out = null;
	BufferedReader in = null;
	
	boolean readerflag = false;
	
	public void chat(){
		
		try{
			
//			srvsocket = new ServerSocket();
//			srvsocket.bind(new InetSocketAddress(ip, chat_port));
			
			srvsocket = new ServerSocket(chat_port);
		
//			System.out.println("ȣ��Ʈ address : "+srvsocket.getInetAddress()+
//					" port : "+chat_port+" �� ���ε� �Ǿ����ϴ�.");
			
			ClientSize cs = new ClientSize();
			
			cs.start();
			
			while(true)
			{
				// Create Thread
				chattingThread service;

				consocket = srvsocket.accept();
				
				// clinet, reader ����
				if(!(client.size() == 0 && readerflag == false))
				{
					service = new chattingThread(consocket, this, false);
				}
				else
				{
					readersocket = consocket;
					out = new PrintWriter(readersocket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(readersocket.getInputStream()));
					
					out.println("/you are reader");
										
					service = new chattingThread(readersocket, this, true);
					
					readerflag = true;
				}
								
				service.start();
			}
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		finally
		{
			try{
				if(srvsocket != null)
					srvsocket.close();
				if(consocket != null)
					consocket.close();
				if(out != null)
					out.close();
				if(in != null)
					in.close();
				if(readersocket != null)
					readersocket.close();
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}
	
	/*
	 *  �޼��� ����
	 */
	public synchronized void broadcast(String msg)
	{
		for(int i = 0 ; i < client.size() ; ++i)
		{
			chattingThread cclient = ((chattingThread) client.elementAt(i));
			cclient.sendMessage(msg);
		}
	}
	
	/*
	 *  Ŭ���̾�Ʈ�� ���� ����Ʈ�� ����
	 */
	public synchronized void addClient(chattingThread c)
	{
		client.add(c);
//		System.out.println("���ο� Ŭ���̾�Ʈ�� �����߽��ϴ�. ���� " + client.size() + "�� ������ �Դϴ�.");
	}

	
	/*
	 *  Ŭ���̾�Ʈ ����
	 */
	public synchronized void removeClient(chattingThread c)
	{
		client.remove(c);
//		System.out.println("Ŭ���̾�Ʈ�� �������ϴ�. ���� " + client.size() + "�� ������ �Դϴ�.");
				
		if(client.size() == 0)
		{
			readerflag = false;
		}
	}
	
	class ClientSize extends Thread
	{
		public void run()
		{
			int startsize = client.size();
			
			while(true)
			{
				try
				{					
					Thread.sleep(1000);
				
					if(startsize != client.size())
					{
						startsize = client.size();
						
						broadcast("/clientsize "+startsize);
					}	
					
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		Server server = new Server();
		
		server.chat();
	}
	
	
}
