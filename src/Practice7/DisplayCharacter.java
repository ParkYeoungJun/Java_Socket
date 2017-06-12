package Practice7;

import java.io.*;
public class DisplayCharacter
{
  // System.out은 PrintStream 클래스의 객체이다. 
   public static void main(String args[]) throws java.io.IOException {
      for(int i=32; i<127; i++){
         System.out.write(i); // I 값에서 하위 1바이트 만을 전송한다.
         if(i%8 == 7)
            System.out.write('\n'); // 8개의 문자를 출력하고 줄을 이동한다.
         else
            System.out.write('\t'); // 하나의 문자를 출력하고 탭을 출력한다. 
}
      System.out.write('\n');
   }
}
