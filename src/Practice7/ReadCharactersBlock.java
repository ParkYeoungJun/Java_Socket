package Practice7;

import java.io.*;
public class ReadCharactersBlock
{
   public static void main(String args[]) throws java.io.IOException {
      byte[] buffer = new byte[80];
      int numberRead;
      int count = 0;
      
      while((numberRead = System.in.read(buffer)) >= 0) // 스트림의 내용을 블록단위로 읽어 buffer에 저장한다.
      {
    	  System.out.write(buffer, 0, numberRead);
      
    	  count = 0;
    	  
    	  while(buffer[count] != '\n')
    	  {
    		  ++count;
    	  }
    	  
    	  System.out.println(count-1);
      }
   }
}
