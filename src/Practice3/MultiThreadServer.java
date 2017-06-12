package Practice3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiThreadServer {
	   public static void main(String args[]){
		      ServerSocket theServer;
		      Socket theSocket = null;
		 
		     
		      try{
		    	  
		    	 // ���� ���� ���� 
		         theServer = new ServerSocket(6); // 7�� echo ���� ��Ʈ 
		         
		         while(true){
		        	 
		        	 //Ŭ���̾�Ʈ�� �����ϸ� ����� �� �ִ� ���� ��ȯ
		        	 theSocket = theServer.accept(); // �������� ������ �����Ѵ�.
		         
		        	 System.out.println("A Client Connected.....");
		          
		        	 Handler handler = new Handler(theSocket);
		        	 handler.start();
		        	 
		         }
		         
		      }catch(UnknownHostException e){
		         System.err.println(e);
		      }catch(IOException e){
		         System.err.println(e);
		      }
		         finally{
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

class Handler extends Thread{
	   protected Socket socket;
	   
	   public Handler(Socket socket){
		   this.socket = socket;
	   }
	   
	   public void run(){
		   try{
			   
			   BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		         
	        	 // ������ ����
			   writer.write("Welcome to the TestServer....."); 
			   writer.flush(); // Ŭ���̾�Ʈ�� �����͸� 
			   
	 		   }catch(IOException ignored){}
			 finally{
			   try{
				   socket.close();
			   }catch(IOException ignored){}
		   }
	   }
}
