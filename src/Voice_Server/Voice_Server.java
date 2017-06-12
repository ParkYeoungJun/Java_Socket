package Voice_Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Voice_Server {

	// Voice server socket
	ServerSocket srvsocket = null;
	
	// accept client
	Socket consocket = null;
	Socket readersocket = null;
	
	// Screen의 address
//	static String ip = "182.227.201.125";
	static String ip = "127.0.0.1";

	static int voice_port = 5557;
	
	// client array
	Vector client = new Vector();
	
	boolean readerflag = false;
	
	public void Voice()
	{
		try
		{
//			srvsocket = new ServerSocket();
//			srvsocket.bind(new InetSocketAddress(ip, voice_port));
			
			srvsocket = new ServerSocket(voice_port);
			
//			System.out.println("호스트 address : "+srvsocket.getInetAddress()+
//					" port : "+voice_port+" 에 바인딩 되었습니다.");		
		
			while(true)
			{
				VoiceThread service;
				
				consocket = srvsocket.accept();
				
				if(!(client.size() == 0 && readerflag == false))
				{
					service = new VoiceThread(consocket, this, false);
				}
				else
				{
					readersocket = consocket;
					
					readerflag = true;
					
					service = new VoiceThread(consocket, this, true);
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
				System.out.println(e);
			}
		}
	}
	
	public synchronized void addClient(VoiceThread v)
	{
		client.add(v);
//		System.out.println("새로운 클라이언트가 접속했습니다. 현재 " + client.size() + "명 접속중 입니다.");
	}
	
	public synchronized void broadcast(byte[] inBytes, int bytesRead)
	{
		for(int i = 1 ; i < client.size() ; ++i)
		{
			VoiceThread voc = (VoiceThread) client.elementAt(i);
			voc.sendVoice(inBytes, bytesRead);
		}
	}
	
	public synchronized void removeClient(VoiceThread v)
	{
		client.remove(v);
//		System.out.println("클라이언트가 나갔습니다. 현재 " + client.size() + "명 접속중 입니다.");

		if(client.size() == 0)
			readerflag = false;
	}
	
	
	public static void main(String[] args)
	{
		Voice_Server voice = new Voice_Server();
		voice.Voice();
	}
}
