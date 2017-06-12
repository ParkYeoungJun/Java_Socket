package Practice6;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Cal_Thread_Server {
	

	public static void main(String args[]){
		 
		
		ServerSocket theServer;
		Socket theSocket = null;
		InputStream is;
		OutputStream os;
		BufferedWriter writer;
		BufferedReader reader;

	      
		try{
	    	  
	    	 // 서버 소켓 생성 
	         theServer = new ServerSocket(6); // 7은 echo 서버 포트 
	         
	         //클라이언트가 접속하면 통신할 수 있는 소켓 반환
	         theSocket = theServer.accept(); // 서버측의 소켓을 생성한다.
	         
	         
	         is = theSocket.getInputStream();
	         
	         reader = new BufferedReader(new InputStreamReader(is));
	         os = theSocket.getOutputStream();
	         writer = new BufferedWriter(new OutputStreamWriter(os));
	        
	         System.out.println("User Connected...\n");
	         
	         Database store = new Database(writer, reader);
	         
	         read read = new read(store);
	         calculation cal = new calculation(store);
	         
	         read.start();	         
	         cal.start();
	         
	         while(true)
	         {}
	         
	      }catch(UnknownHostException e){
	         System.err.println(e);
	      }catch(IOException e){
	         System.err.println(e);
	       }finally{
	         if(theSocket != null){
	            try{	            	
	               theSocket.close();
	            }catch(IOException e){
	               System.out.println(e);
	            }
	         }
	      }
	   }
}


class Database
{
	
	public int count = 0;
	public int operand[];
	public char operator;
	public int result;
	public BufferedWriter writer;
	public BufferedReader reader; 
	

	public Database(BufferedWriter writer, BufferedReader reader)
	{
		this.writer = writer;
		this.reader = reader;
	}
	
	public synchronized void add()
	{
		this.notify();
	}
	
	public synchronized void cal()
	{
		while(true)
		{			
			if(count == 0)
			{
				try
				{
					this.wait();
				}
				catch(InterruptedException ignored)
				{}
			}
			else
			{				
				break;
			}
		}
	}
}


class read extends Thread
{
	public Database store;
	public String str;
	
	public read(Database store)
	{
		this.store = store;
	}
	
	public void run()
	{
		try{
			while(true){
				if((str = store.reader.readLine()) != null)
				{	
					if(str.equals("q"))
					{
						System.out.println("Client is Gone...");
						
						System.exit(0);
					}
					
					StringTokenizer token = new StringTokenizer(str);
					
					store.count = Integer.parseInt(token.nextToken());

					store.operand = new int[store.count+1];
			
					for(int i = 0 ; i < store.count ; ++i)
					{
						store.operand[i] = Integer.parseInt(token.nextToken());
					}
			
					store.operator = token.nextToken().charAt(0);	        	 
       	 				
					str = null;
				
					store.add();
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Client is Gone..");
			System.exit(0);
		}
	}
}


class calculation extends Thread
{
	protected Database store;
	
	public calculation(Database store)
	{
		this.store = store;
	}
	
	public void run()
	{			
		
		store.cal();
		
		while(true)
		{
			for(int i = 0 ; i < store.count ; ++i)
   	 		{
				if(store.operator == '+')
				{
					store.result += store.operand[i];
				}
				else if(store.operator == '-')
				{
					if(i != 0)
					{
						store.result -= store.operand[i];
					}
					else
					{
						store.result += store.operand[i];
					}
				}
				else if(store.operator == '*')
				{
					if(i != 0)
					{
						store.result *= store.operand[i];
					}
					else
					{
						store.result += store.operand[i];
					}
				}
   	 		}
		
			try
			{
				store.writer.write(Integer.toString(store.result) + '\r' + '\n');
				store.writer.flush();
			
				store.count = 0;
				store.result = 0;
			
				store.cal();
			}
			catch(IOException e)
			{
				System.out.println(e);
			}
		}
	}
}


