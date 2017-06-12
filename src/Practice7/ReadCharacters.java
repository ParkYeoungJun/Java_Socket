package Practice7;

import java.io.*;
import java.util.*;

public class ReadCharacters
{
   public static void main(String args[]) throws java.io.IOException {
      int data;
      byte[] byte_data;
      
      Vector<Integer> buffer = new Vector <Integer>();
      
      while((data = System.in.read()) >= 0){ // 스트림에서 한 문자씩 읽는다. 
    	 
    	  if(data == '`')
    	 {
    		 
    		 for(int i = 0 ; i < buffer.size() ; ++i)
    		 {
    			 System.out.write(buffer.get(i));
    		 }

    		 System.out.println("\nGood Bye");
    		 break;
    	 }
      
      	

      	buffer.add(data);
      }
      
   }
}
