package Practice8;

import java.io.*;
import java.net.*;

public class Buffered_Client {
	 public static void main(String args[]){
		 
		   
		  Socket theSocket = null;
	      String host;
	      
	      BufferedInputStream bin;
	      BufferedOutputStream bout;
	      BufferedInputStream sin;
	      BufferedOutputStream sout;
  
	      InputStream is;
	      OutputStream os;
	          
	      if(args.length>0){
	         host=args[0]; //  �Է¹��� ȣ��Ʈ�� ���
	      }else{
	         host="localhost"; // ���� ȣ��Ʈ�� ���
	      }
	      
	      try{
	    	  
	         theSocket = new Socket(host, 6); // echo ������ �����Ѵ�.
	         
	         is = theSocket.getInputStream();
	         
	         os = theSocket.getOutputStream();
	         
	         bin = new BufferedInputStream(System.in);
	         bout = new BufferedOutputStream(System.out);
	         sin = new BufferedInputStream(is);
	         sout = new BufferedOutputStream(os);

	         
	         
	         System.out.println("Connected\n");
	         System.out.println("If you want Transport Message Insert ' '\n");
	         
	         int data = 0;
	         
	         while(true){

	        	 while(true){
	        		 data = bin.read();
	        		 sout.write(data);
	        		 if(data == ' ') break;
	        	 }
				
	        	 sout.flush();
	        	 		        
	        	 while(true){
	        		 data = sin.read();
	        		 if(data == ' ') break;
	        		 bout.write(data);
	        	 }
	 	        	 
	        	 bout.flush();
	         }       
	         
	      }catch(UnknownHostException e){
	         System.err.println(args[0]+" ȣ��Ʈ�� ã�� �� �����ϴ�.");
	      }catch(IOException e){
	         System.err.println(e);
	      }finally{
	         try{
	            theSocket.close(); // ������ �ݴ´�.
	         }catch(IOException e){
	            System.out.println(e);
	         }
	      }
	   }
}