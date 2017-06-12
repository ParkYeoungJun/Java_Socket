package Practice9;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientB {
	
	ServerSocket srvsocket;
	Socket clientsocket;
	
	PrintWriter out;
	BufferedReader in;
	
	Scanner scan;
	
	int n;
	
	int result;
	
	int x;

	public void job()
	{
		try
		{
			srvsocket = new ServerSocket(6666);
			clientsocket = srvsocket.accept();
		
			out = new PrintWriter(clientsocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			scan = new Scanner(System.in);
						
			n = Integer.parseInt(in.readLine());
						
			System.out.println("���� �ϳ��� �Է��ϼ���");
			
			result = scan.nextInt();
			
			x = result*result;
			
			x = x % n;
						
			out.println(x);
			
			x = Integer.parseInt(in.readLine());
			
			if(x == result)
				out.println("�����Դϴ�!!");
			else
				out.println("��!");
			
//			while(true){}
			
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	public static void main(String[] args)
	{
		ClientB c = new ClientB();
		c.job();
	}
	
}
