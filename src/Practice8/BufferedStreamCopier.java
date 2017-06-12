package Practice8;

import java.io.*; 

public class BufferedStreamCopier
{
	public static void main(String args[])
	{
		try{
			copy(System.in, System.out);
		}catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	public static void copy(InputStream in, OutputStream out)
	throws IOException{
		synchronized(in){
			synchronized(out)
			{
				BufferedInputStream bin = new BufferedInputStream(in);
				BufferedOutputStream bout = new BufferedOutputStream(out);
				
				while(true){
					int data = bin.read();
					if(data == -1) break;
					bout.write(data);
				}
				
				bout.flush();
			}
		}
	}
}