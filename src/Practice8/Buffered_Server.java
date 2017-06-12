package Practice8;

import java.io.*;
import java.net.*;

public class Buffered_Server {

	public static void main(String args[]){
		 
	      ServerSocket theServer;
	      Socket theSocket = null;
	      InputStream is;
	      OutputStream os;

	      BufferedOutputStream bout;
	      BufferedInputStream sin;
	      BufferedOutputStream sout;
	      
	      FileOutputStream out;	      
	      
	      try{
	    	  
	    	 // 서버 소켓 생성 
	         theServer = new ServerSocket(6); // 7은 echo 서버 포트 
	         
	         //클라이언트가 접속하면 통신할 수 있는 소켓 반환
	         theSocket = theServer.accept(); // 서버측의 소켓을 생성한다.
	         
	         
	         is = theSocket.getInputStream();
	         os = theSocket.getOutputStream();
	         
	         bout = new BufferedOutputStream(System.out);
	         sin = new BufferedInputStream(is);
	         sout = new BufferedOutputStream(os);

	         out = new FileOutputStream("Data.txt");
	         
	         System.out.println("User Connected...\n");
	         
	         System.out.println("Message From Client...\n");
	         
	            
	         int data = 0;
	         
	         while(true)
	         {
	        	 data = sin.read();
	        	 
	        	 if(data == ' ')
	        	 {
		        	 sout.write(data);
	        		 
		        	 sout.flush();
		        	 
	        		 continue;
	        	 }
	        	 
	        	 bout.write(data);

	        	 out.write(data);

	        	 sout.write(data);
	        	 	        	 	        	
	        	 bout.flush();
	        		        	 	        	 
	        	 sout.flush();
	         } 
	         
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
