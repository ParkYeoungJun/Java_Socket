package Practice7;

import java.io.*;
public class DisplayCharacterBlock
{
   public static void main(String args[]) throws java.io.IOException{
      byte[] b = new byte[(255-127)*2]; // ����Ʈ �������� �Ҵ��Ѵ�. ���� ���̿� \t �� \n ������ 2����
      int index=0;
      
      
      try
      {
      
    	  for(int i = 128 ; i < 255 ; i++){
    		  b[index++] = (byte) i; // ������ ����Ʈ(0���� 255)������ ĳ��Ʈ�Ѵ�.
    		  if(i%8 ==7)
    			  b[index++] = (byte)'\n'; // 8���� ������ �������� newline�� �����Ѵ�.
    		  else
    			  b[index++] = (byte) '\t';
    	  }
    	  b[index++] = (byte) '\n';
    	  System.out.write(b); // b �迭�� ����� ����Ʈ�� ����Ѵ�.
   
      }
      catch(Exception e)
      {
    	  System.out.println(e);
      }
   }
}
