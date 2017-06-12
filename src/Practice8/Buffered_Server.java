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
	    	  
	    	 // ���� ���� ���� 
	         theServer = new ServerSocket(6); // 7�� echo ���� ��Ʈ 
	         
	         //Ŭ���̾�Ʈ�� �����ϸ� ����� �� �ִ� ���� ��ȯ
	         theSocket = theServer.accept(); // �������� ������ �����Ѵ�.
	         
	         
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
