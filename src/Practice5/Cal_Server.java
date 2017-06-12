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
	    	  
	    	 // 서버 소켓 생성 
	         theServer = new ServerSocket(6); // 7은 echo 서버 포트 
	         
	         //클라이언트가 접속하면 통신할 수 있는 소켓 반환
	         theSocket = theServer.accept(); // 서버측의 소켓을 생성한다.
	         
	         
	         is = theSocket.getInputStream();
	         
	         reader = new BufferedReader(new InputStreamReader(is));
	         os = theSocket.getOutputStream();
	         writer = new BufferedWriter(new OutputStreamWriter(os));
	        
	         System.out.println("User Connected...\n");
	         
	         while((theLine = reader.readLine()) != null ){ // 클라이언트의 데이터를 받는다.
	            
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
	        	// 데이터 전송
	        	writer.write(Integer.toString(result)+'\r'+'\n'); 
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
