package Practice1;

import java.io.*;
import java.net.*;
public class EchoClient
{
   public static void main(String args[]){
 
	   
	  Socket theSocket = null;
      String host;
      
      
      InputStream is;
      BufferedReader reader, userInput;
      OutputStream os;
      BufferedWriter writer;
      String theLine;
      
      
      
      if(args.length>0){
         host=args[0]; //  입력받은 호스트를 사용
      }else{
         host="localhost"; // 로컬 호스트를 사용
      }
      
      
      
      try{
         theSocket = new Socket(host, 6); // echo 서버에 접속한다.
         
         is = theSocket.getInputStream();
         
         
         reader = new BufferedReader(new InputStreamReader(is));
         userInput = new BufferedReader(new InputStreamReader(System.in));
         
         
         os = theSocket.getOutputStream();
         
         
         writer = new BufferedWriter(new OutputStreamWriter(os));
         
         System.out.println("전송할 문장을 입력하십시오.");
         
         while(true){
           
        	theLine = userInput.readLine(); // 데이터를 입력한다.
            if(theLine.equals("quit")) break; // 프로그램 종료
            writer.write(theLine+'\r'+'\n'); 
            writer.flush(); // 서버에 데이터 전송 
            System.out.println(reader.readLine()); //되돌려 받고, 화면에 출력한다.
         
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