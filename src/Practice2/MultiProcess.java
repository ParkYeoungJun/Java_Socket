package Practice2;

import java.io.*;
import java.net.*;
public class MultiProcess
{
   public static void main(String args[]){
      ServerSocket theServer;
      Socket theSocket = null;
      InputStream is;
      BufferedReader reader;
      
      OutputStream os;
      BufferedWriter writer;
      String theLine;
     
      try{
    	  
    	 // 서버 소켓 생성 
         theServer = new ServerSocket(6); // 7은 echo 서버 포트 
         
         
         while(true){
        	 
        	 //클라이언트가 접속하면 통신할 수 있는 소켓 반환
        	 theSocket = theServer.accept(); // 서버측의 소켓을 생성한다.
         
        	 System.out.println("A Client Connected.....");
          
        	 os = theSocket.getOutputStream();
        	 writer = new BufferedWriter(new OutputStreamWriter(os));
         
        	 // 데이터 전송
        	 writer.write("Welcome to the TestServer....."); 
        	 writer.flush(); // 클라이언트에 데이터를 
 
        	 try{
        		 theSocket.close();
        	 }catch(IOException ignored){}
        
         
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
