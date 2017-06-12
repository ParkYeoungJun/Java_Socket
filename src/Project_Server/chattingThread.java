package Project_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class chattingThread extends Thread{
	
	Socket xSocket = null;
	Server xServer = null;
	
	// 입출력
	PrintWriter out = null;
	BufferedReader in = null;
	
	boolean readerflag = false;
	
	String UserName;
	
	public chattingThread(Socket sock, Server srv, boolean readerflag)
	{
		super();
		xSocket = sock;
		xServer = srv;
		this.readerflag = readerflag;
	}
	
	public synchronized void sendMessage(String msg)
	{
		if(out != null)
			out.println(msg);
	}
	
	
	public void run()
	{
		String line;
		
		try{
			out = new PrintWriter(xSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(xSocket.getInputStream()));
			
			xServer.addClient(this);
			
			while((line = in.readLine()) != null)
			{
				if(line.contains("/Im"))
				{
					String tempStr[] = line.split(" ");
					UserName = tempStr[1];
				}
				else if(line.contains("/silent"))
				{				
					if(readerflag)
						xServer.broadcast(line);
				}
				else if(line.contains("/startvote"))
				{
					if(readerflag)
					{
						xServer.broadcast(line);
					}
				}
				else if(line.contains("/endvote"))
				{
					if(readerflag)
					{
						xServer.broadcast(line);
					}
				}
				else
					xServer.broadcast(line);
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
		finally
		{
			try{
				xServer.broadcast("사용자 ["+UserName+"] 이 나가셧습니다.\n");
				
				xServer.removeClient(this);
				
				if(out != null)
					out.close();
				if(in != null)
					in.close();
				if(xSocket != null)
					xSocket.close();
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
		}
	}
}
