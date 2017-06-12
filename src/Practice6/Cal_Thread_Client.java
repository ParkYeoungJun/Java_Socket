package Practice6;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cal_Thread_Client{
	 public static void main(String args[]){
		 
		   
		  Socket theSocket = null;
	      String host;
	      
	      
	      InputStream is;
	      BufferedReader reader;
	      OutputStream os;
	      BufferedWriter writer;
	      String theLine;
	      Scanner scan;
	      int count;
	      int operand[];
	      String operator;
	      String temp;
	      
	      
	      
	      if(args.length>0){
	         host=args[0]; //  �Է¹��� ȣ��Ʈ�� ���
	      }else{
	         host="localhost"; // ���� ȣ��Ʈ�� ���
	      }
	      
	      
	      
	      try{
	         theSocket = new Socket(host, 6); // echo ������ �����Ѵ�.
	         
	         is = theSocket.getInputStream();
	         
	         scan = new Scanner(System.in);
	         
	         
	         reader = new BufferedReader(new InputStreamReader(is));
	         
	         
	         os = theSocket.getOutputStream();
	         
	         
	         writer = new BufferedWriter(new OutputStreamWriter(os));
	         
	         System.out.println("Connected");
	         
	         while(true){
	         
	        	 System.out.println("If you wand quit insert q");
	        	 
	        	 System.out.println("Operand Count : ");
	        	 
	        	 temp = scan.next();
	        	 
	       
	        	 
	        	 if(temp.charAt(0) == 'q')
	        	 {
	        		 System.out.println("System Terminated");
	        		 
	        		 writer.write("q");
	        		 writer.flush();
	        		 
	        		 theSocket.close();
	        		 return;
	        	 }
	        	 count = Integer.parseInt(temp);
	     
	        	 operand = new int[count];
	        	 
	        	 for(int i = 0 ; i < count ; ++i)
	        	 {
	        		 
	        		 System.out.println("Operand " + (i+1) + ": ");
	        		 
	        		 operand[i] = scan.nextInt();
	        	 }
	        	 
	        	 System.out.println("Operator : ");
	        	 
	        	 operator = scan.next();
	        	 
	        	 theLine = "" + count + " ";
	        	 
	        	 for(int i = 0 ; i < count ; ++i)
	        	 {
	        		 theLine += ""+operand[i]+" ";
	        	 }
	        	 
	        	 theLine += ""+operator+" ";
	        	 
	        	 writer.write(theLine + '\r' + '\n'); 
	        	 writer.flush(); // ������ ������ ���� 
	            
	            System.out.println("Result : "+reader.readLine()+"\n"); //�ǵ��� �ް�, ȭ�鿡 ����Ѵ�.
	         
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
