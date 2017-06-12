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
         host=args[0]; //  �Է¹��� ȣ��Ʈ�� ���
      }else{
         host="localhost"; // ���� ȣ��Ʈ�� ���
      }
      
      
      
      try{
         theSocket = new Socket(host, 6); // echo ������ �����Ѵ�.
         
         is = theSocket.getInputStream();
         
         
         reader = new BufferedReader(new InputStreamReader(is));
         userInput = new BufferedReader(new InputStreamReader(System.in));
         
         
         os = theSocket.getOutputStream();
         
         
         writer = new BufferedWriter(new OutputStreamWriter(os));
         
         System.out.println("������ ������ �Է��Ͻʽÿ�.");
         
         while(true){
           
        	theLine = userInput.readLine(); // �����͸� �Է��Ѵ�.
            if(theLine.equals("quit")) break; // ���α׷� ����
            writer.write(theLine+'\r'+'\n'); 
            writer.flush(); // ������ ������ ���� 
            System.out.println(reader.readLine()); //�ǵ��� �ް�, ȭ�鿡 ����Ѵ�.
         
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