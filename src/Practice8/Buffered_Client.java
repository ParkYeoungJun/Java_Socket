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
	         host=args[0]; //  입력받은 호스트를 사용
	      }else{
	         host="localhost"; // 로컬 호스트를 사용
	      }
	      
	      try{
	    	  
	         theSocket = new Socket(host, 6); // echo 서버에 접속한다.
	         
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
	         System.err.println(args[0]+" 호스트를 찾을 수 없습니다.");
	      }catch(IOException e){
	         System.err.println(e);
	      }finally{
	         try{
	            theSocket.close(); // 소켓을 닫는다.
	         }catch(IOException e){
	            System.out.println(e);
	         }
	      }
	   }
}