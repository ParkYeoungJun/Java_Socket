package Practice7;

import java.io.*;
public class ReadFromFile
{
   public static void main(String args[]){
      int bytesRead;
      byte[] buffer = new byte[256];
      FileInputStream fin = null;
      FileOutputStream out = null;
      if(args.length != 2){
         System.err.println("Filename Please");
      }
      try{
         fin = new FileInputStream(args[0]);
         out = new FileOutputStream(args[1]);
         
         while((bytesRead = fin.read(buffer)) >= 0){ // 파일의 끝은 –1을 반환한다.
        	 out.write(buffer, 0 , bytesRead);
         }
      }catch(IOException e){
         System.err.println("Do not read from stream");
      }finally{
         try{
            if(fin!=null) fin.close();
         }catch(IOException e){}
      }
   }
}
