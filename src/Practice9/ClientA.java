package Practice9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class ClientA {
	
	Socket socket;
	
	int primnum1, primnum2;
	int n;
	int a;
	int result1,result2,result_final;
	
	PrintWriter out;
	BufferedReader in;
	
	public void job()
	{
		try
		{
			
			socket = new Socket("127.0.0.1",6666);
		
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			Scanner scan = new Scanner(System.in);
			
			System.out.println("큰 소수 2개를 입력하세요");
			
			primnum1 = scan.nextInt();
			primnum2 = scan.nextInt();
			
			n = primnum1*primnum2;
			
			out.println(n);
			
			a = Integer.parseInt(in.readLine());

			china();
						
			System.out.println("2개의 정수중 한개를 선택하세요");
			
			Vector vec = new Vector();
			
			for(int i = 0 ; i < n ; ++i)
			{
				if(a == (i*i % n))
				{
					vec.add(i);
				}
			}
			
			int temp = n - (int)vec.elementAt(0);

			if(temp == (int)vec.elementAt(1))
				System.out.println((int)vec.elementAt(0)+" "+(int)vec.elementAt(2));
			else if(temp == (int)vec.elementAt(2))
				System.out.println((int)vec.elementAt(0)+" "+(int)vec.elementAt(1));
			else
				System.out.println((int)vec.elementAt(0)+" "+(int)vec.elementAt(1));
			
			int selected;
						
			selected = scan.nextInt();
									
			for(int i = 0 ; i < vec.size() ; ++i)
			{
				if(selected == (int)vec.elementAt(i))
				{					
					out.println(selected);
				}
			}
			
			String result = in.readLine();
			
			System.out.print(result);
			
//			while(true){}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	private void china()
	{
		
	}
	
	public static void main(String[] args)
	{
		ClientA c = new ClientA();
		c.job();
	}

}
