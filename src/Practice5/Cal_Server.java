package Practice5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Cal_Server {

	public static void main(String args[]){
		 
	      ServerSocket theServer;
	      Socket theSocket = null;
	      InputStream is;
	      BufferedReader reader;
	      OutputStream os;
	      BufferedWriter writer;
	      String theLine;
	      int count;
	      int operand[];
	      char operator;
	      int result;
	      
	      try{
	    	  
	    	 // ���� ���� ���� 
	         theServer = new ServerSocket(6); // 7�� echo ���� ��Ʈ 
	         
	         //Ŭ���̾�Ʈ�� �����ϸ� ����� �� �ִ� ���� ��ȯ
	         theSocket = theServer.accept(); // �������� ������ �����Ѵ�.
	         
	         
	         is = theSocket.getInputStream();
	         
	         reader = new BufferedReader(new InputStreamReader(is));
	         os = theSocket.getOutputStream();
	         writer = new BufferedWriter(new OutputStreamWriter(os));
	        
	         System.out.println("User Connected...\n");
	         
	         while((theLine = reader.readLine()) != null ){ // Ŭ���̾�Ʈ�� �����͸� �޴´�.
	            
	        	 StringTokenizer token = new StringTokenizer(theLine, " ");
	        	 
	        	 result = 0;
	        	 
	        	 count = Integer.parseInt(token.nextToken());
	        	 
	        	 operand = new int[count+1];
	        	 
	        	 for(int i = 0 ; i < count ; ++i)
	        	 {
	        		 operand[i] = Integer.parseInt(token.nextToken());
	        	 }
	        	 
	        	 operator = token.nextToken().charAt(0);	        	 
	        	 
	        	 for(int i = 0 ; i < count ; ++i)
	        	 {
	        		 if(operator == '+')
	        		 {
	        			 result += operand[i];
	        		 	        		 }
	        		 else if(operator == '-')
	        		 {
	        			 if(i != 0)
	        			 {
	        				 result -= operand[i];
	        			 }
	        			 else
	        			 {
	        				 result += operand[i];
	        			 }
	        		 }
	        		 else if(operator == '*')
	        		 {
	        			 if(i != 0)
	        			 {
	        				 result *= operand[i];
	        			 }
	        			 else
	        			 {
	        				 result += operand[i];
	        			 }
	        		 }
	        	 }
	        	// ������ ����
	        	writer.write(Integer.toString(result)+'\r'+'\n'); 
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
