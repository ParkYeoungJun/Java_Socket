package Practice7;

import java.io.*;
public class DisplayCharacterBlock
{
   public static void main(String args[]) throws java.io.IOException{
      byte[] b = new byte[(255-127)*2]; // 바이트 기억공간을 할당한다. 문자 사이에 \t 및 \n 때문에 2배임
      int index=0;
      
      
      try
      {
      
    	  for(int i = 128 ; i < 255 ; i++){
    		  b[index++] = (byte) i; // 정수를 바이트(0에서 255)값으로 캐스트한다.
    		  if(i%8 ==7)
    			  b[index++] = (byte)'\n'; // 8개의 데이터 다음에는 newline을 삽입한다.
    		  else
    			  b[index++] = (byte) '\t';
    	  }
    	  b[index++] = (byte) '\n';
    	  System.out.write(b); // b 배열에 저장된 바이트를 출력한다.
   
      }
      catch(Exception e)
      {
    	  System.out.println(e);
      }
   }
}
