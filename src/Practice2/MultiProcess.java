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
    	  
    	 // ���� ���� ���� 
         theServer = new ServerSocket(6); // 7�� echo ���� ��Ʈ 
         
         
         while(true){
        	 
        	 //Ŭ���̾�Ʈ�� �����ϸ� ����� �� �ִ� ���� ��ȯ
        	 theSocket = theServer.accept(); // �������� ������ �����Ѵ�.
         
        	 System.out.println("A Client Connected.....");
          
        	 os = theSocket.getOutputStream();
        	 writer = new BufferedWriter(new OutputStreamWriter(os));
         
        	 // ������ ����
        	 writer.write("Welcome to the TestServer....."); 
        	 writer.flush(); // Ŭ���̾�Ʈ�� �����͸� 
 
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
