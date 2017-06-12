package final_Assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class do_server extends Thread{

	Socket client;
	
	BufferedReader in;
	BufferedWriter out;
	
	JTextArea text;
	JScrollPane textscroll;
	JLabel input;
	JButton de;
	
	JLabel Delabel;
	JTextArea deen;
	JScrollPane descroll;
	
	boolean fileflag = false;
	
	long p = 173, q = 53;
	long n = p*q;
	long pi_n = (p-1)*(q-1);
	long e;
	long d;
	
	JFrame frame;
	
	public do_server(Socket soc)
	{
		client = soc;
		
		try
		{
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	public static long publickeyMaker(long temp)
	{
		long ee = 0;
		
		while(true)
		{
			ee = (long)(Math.random()*10000);
			long gcd = gcd(ee, temp);
			if(gcd == 1)
			{
				break;
			}
		}
		
		return ee;
	}
	
	public static long gcd(long a, long b)
	{
		long s = a;
		long t = b;
		long r = 0;
		
		while(t > 0)
		{
			r = s % t;
			s = t;
			t = r;
		}
		
		return s;
	}
	
	public static long uclide(long first, long second)
	{
		long b, p, x, s, q, e, w, a;
		
		b = second;
		a = first;
		x = 0;
		s = 1;
		
		while(a != 1)
		{
			q = b/a;
			e = b - a*q;
			w = x - s*q;
			b = a;
			a = e;
			x = s;
			s = w;
		}
		
		if(s < 0)
			s = s + second;

		return s;
	}
	
	public static long deenc(long c, long d, long n)
	{
		long z = 1;
		
		while(d != 0)
		{
			while(d % 2 == 0)
			{
				d = d / 2;
				c = (c * c) % n;
			}
			
			d--;
			
			z = (z * c) % n;
		}
		
		return z;
	}
	
	public void run()
	{
		try
		{
			frame = new JFrame("Server");
			frame.setLayout(null);
			frame.setBounds(100,100,500,380);
			
			input = new JLabel("Input String");
			input.setBounds(30, 10, 100, 30);
			frame.add(input);
			
			Delabel = new JLabel("Decryption String");
			Delabel.setBounds(30, 130, 200, 30);
			frame.add(Delabel);
			
		
			e = publickeyMaker(pi_n);
		
			out.write(e+" "+n+'\r'+'\n');
			out.flush();
			
			d = uclide(e, pi_n);

			text = new JTextArea();
			text.setEditable(false);
			text.setLineWrap(true);
			textscroll = new JScrollPane(text);
			textscroll.setBounds(30, 30, 400, 100);
			frame.add(textscroll);
			
			String instr = in.readLine();
			
			text.setText(instr);
			
			String str[] = instr.split(" ");

			long temp[] = new long[str.length];
			
			for(int i = 0 ; i < str.length ; ++i)
			{				
				temp[i] = deenc(Long.parseLong(str[i]), d, n);
			}
			
			de = new JButton("Decryption");
			de.setBounds(100, 270, 250, 30);
			de.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try{
						
						if(deen.getText().length() == 0)
						{
							char t[] = new char[str.length];
						
							for(int i = 0 ; i < str.length ; ++i)
							{
								t[i] = (char)temp[i];
							}
						
							for(int i = 0 ; i < t.length ; ++i)
							{
								deen.append(""+t[i]);
							}
												
							String strr = in.readLine();
												
							if(strr.equals("file"))
							{
								FileWriter write = new FileWriter(new File("text.txt"), true);
								BufferedWriter writer = new BufferedWriter(write);
							
								String tempstr[] = deen.getText().split("\n");
								
								for(int i = 0 ; i < tempstr.length ; ++i)
								{
									writer.write(tempstr[i]);
									writer.newLine();
								}
								
								writer.write("////////////////////");
								writer.newLine();
								
								writer.close();
								write.close();
								
								JOptionPane.showMessageDialog(null, "파일을 저장했습니다.");
							}
						}
					}
					catch(Exception e1)
					{
						System.err.println(e1);
					}
				}
				
			});
			frame.add(de);
			
			deen = new JTextArea();
			deen.setEditable(false);
			deen.setLineWrap(true);
			descroll = new JScrollPane(deen);
			descroll.setBounds(30, 150, 400, 100);
			frame.add(descroll);
			
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		finally
		{
//			try
//			{
//				if(in != null)
//					in.close();
//				if(out != null)
//					out.close();
//			}
//			catch(Exception e)
//			{
//				System.err.println(e);
//			}
		}
	}
}
