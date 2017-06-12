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
         theServer = new ServerSocket(7); // 7�� echo ���� ��Ʈ 
         theSocket = theServer.accept(); // �������� ������ �����Ѵ�.
         is = theSocket.getInputStream();
         reader = new BufferedReader(new InputStreamReader(is));
         os = theSocket.getOutputStream();
         writer = new BufferedWriter(new OutputStreamWriter(os));
         while((theLine = reader.readLine()) != null ){ // Ŭ���̾�Ʈ�� �����͸� �޴´�.
            System.out.println(theLine); // ���� �����͸� ȭ�鿡 ����Ѵ�.
            writer.write(theLine+'\r'+'\n'); 
            writer.flush(); // Ŭ���̾�Ʈ�� �����͸� ������ 
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