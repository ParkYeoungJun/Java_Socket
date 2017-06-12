package Practice1;

import java.io.*;
import java.net.*;
public class EchoServer
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
         
         //클라이언트가 접속하면 통신할 수 있는 소켓 반환
         theSocket = theServer.accept(); // 서버측의 소켓을 생성한다.
         
         
         is = theSocket.getInputStream();
         
         reader = new BufferedReader(new InputStreamReader(is));
         os = theSocket.getOutputStream();
         writer = new BufferedWriter(new OutputStreamWriter(os));
         
         
         while((theLine = reader.readLine()) != null ){ // 클라이언트의 데이터를 받는다.
            
        	System.out.println(theLine); // 받은 데이터를 화면에 출력한다.
            
        	// 데이터 전송
        	writer.write(theLine+'r'+'\n'); 
            writer.flush(); // 클라이언트에 데이터를 재전송 
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