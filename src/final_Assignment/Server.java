package final_Assignment;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	ServerSocket server;
	Socket client;

	public void job()
	{
		try
		{
			server = new ServerSocket(7777);
			
			while(true)
			{
				client = server.accept();
			
				do_server the = new do_server(client);
				
				the.start();			
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	public static void main(String args[])
	{
		Server ser = new Server();
		ser.job();
	}
	
}
