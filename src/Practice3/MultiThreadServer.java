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
		    	  
		    	 // 서버 소켓 생성 
		         theServer = new ServerSocket(6); // 7은 echo 서버 포트 
		         
		         while(true){
		        	 
		        	 //클라이언트가 접속하면 통신할 수 있는 소켓 반환
		        	 theSocket = theServer.accept(); // 서버측의 소켓을 생성한다.
		         
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
		         
	        	 // 데이터 전송
			   writer.write("Welcome to the TestServer....."); 
			   writer.flush(); // 클라이언트에 데이터를 
			   
	 		   }catch(IOException ignored){}
			 finally{
			   try{
				   socket.close();
			   }catch(IOException ignored){}
		   }
	   }
}
