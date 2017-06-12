package final_Assignment;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Client {

	Socket socket;
	static String ip;
	
	BufferedReader in;
	BufferedWriter out;
	
	JFrame frame;
	
	JLabel chat;
	JLabel chatting;
	JLabel pass;
	
	JButton to_password;
	JButton choose_file;
	JButton push_statement;
	JButton transport;
	
	JTextArea insert_statement;
	JTextArea view_statement;
	JTextArea view_password;
	
	JScrollPane state_scroll;
	JScrollPane view_scroll;
	JScrollPane view_password_scroll;
	
	JFileChooser f;
	File file = new File("");
	boolean fileflag = false;
	
	long ee, n;
	
	public void connect(JFrame fr)
	{
		try
		{
			socket = new Socket(ip, 7777);
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	
			String[] public_key = in.readLine().split(" ");
			
			ee = Long.parseLong(public_key[0]);
			n = Long.parseLong(public_key[1]);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Do not exist Server");
		
			fr.setVisible(false);
		}
	}
	
	public static long enc(long m, long e, long n)
	{
		long z = 1;
		
		while(e != 0)
		{
			while(e % 2 == 0)
			{
				e = e / 2;
				m = (m*m)%n;
			}
			
			e--;
			z = (z * m) % n;
		}
		
		return z;
	}
	
	
	public void job()
	{	
		frame = new JFrame();
		frame.setLayout(null);
		frame.setBounds(100,100,600,600);
		frame.setTitle("Network");
//		frame.setDefaultCloseOperation(operation);
		
		chat = new JLabel("Chatting");
		chat.setBounds(10,5,100,30);
		chatting = new JLabel("View Statement");
		chatting.setBounds(10,200,100,30);
		pass = new JLabel("View Password");
		pass.setBounds(10, 340, 100, 30);
		
		frame.add(chat);
		frame.add(chatting);
		frame.add(pass);
		
		insert_statement = new JTextArea();
		insert_statement.setLineWrap(true);
		state_scroll = new JScrollPane(insert_statement);
		state_scroll.setBounds(10, 30, 550, 100);
		frame.add(state_scroll);
		
		choose_file = new JButton("File");
		choose_file.setBounds(487, 140, 70, 30);
		choose_file.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
					f = new JFileChooser();
					f.setFileSelectionMode(JFileChooser.FILES_ONLY);
					f.setFileFilter(new FileNameExtensionFilter("ObjectFile(.txt)","txt"));

					if(f.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
					{
						file = f.getSelectedFile();
					
						String[] sp = file.getName().split("\\.");
					
						if(!sp[1].equals("txt"))
							JOptionPane.showMessageDialog(null, "text파일을 선택하세요");
						else
						{
							try
							{
								insert_statement.setText("");
								view_statement.setText("");
								
								Scanner fscan = new Scanner(file);
								
								String str = "";
								
								while(fscan.hasNext())
								{
									view_statement.append(fscan.nextLine());
									view_statement.append("\n");
								}
								
								fileflag = true;
								
							}
							catch(Exception e)
							{
								System.err.println(e);
							}
						}
						
					}
				
			}
			
		});
		frame.add(choose_file);
		
		view_statement = new JTextArea();
		view_statement.setEditable(false);
		view_statement.setLineWrap(true);
		view_scroll = new JScrollPane(view_statement);
		view_scroll.setBounds(10, 225, 550, 100);
		frame.add(view_scroll);
		
		view_password = new JTextArea();
		view_password.setEditable(false);
		view_password.setLineWrap(true);
		view_password_scroll = new JScrollPane(view_password);
		view_password_scroll.setBounds(10, 365, 550, 100);
		frame.add(view_password_scroll);
		
		to_password = new JButton("Encryption");
		to_password.setBounds(300, 480, 150, 30);
		to_password.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				if(view_statement.getText().length() != 0)
				{
					view_password.setText("");
				
					char charArray[] = view_statement.getText().toCharArray();
				
					for(int i = 0 ; i < charArray.length ; ++i)
					{
						long temp = (long)charArray[i];
										
						long pass = enc(temp, ee, n);
										
						view_password.append(pass+" ");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "입력된 문장이 없습니다.");
				}
			}
			
		});
		frame.add(to_password);
		
		push_statement = new JButton("Push");
		push_statement.setBounds(400, 140, 80, 30);
		push_statement.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(insert_statement.getText().length() != 0)
				{
				
					String str = insert_statement.getText();
				
					insert_statement.setText("");
				
					view_statement.setText(str);
					
					fileflag = false;
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "입력된 문장이 없습니다.");
				}
			}
			
		});
		frame.add(push_statement);
		
		transport = new JButton("Transport");
		transport.setBounds(460, 480, 100, 30);
		transport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				if(view_password.getText().length() != 0)
				{
					try
					{
						out.write(view_password.getText()+'\r'+'\n');
						out.flush();
						
						if(fileflag)
						{
							out.write("file" + '\r' + '\n');
							out.flush();
						}
						else
						{
							out.write("nofile" + '\r' + '\n');
							out.flush();
						}

						frame.setVisible(false);
						System.exit(0);
						
					}
					catch(Exception e)
					{
						System.err.println(e);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "입력된 암호가 없습니다.");
				}
				
			}
			
		});
		frame.add(transport);
		
		frame.setVisible(true);
		
		connect(frame);
	}
	
	public static void main(String args[])
	{
		if(args.length > 0)
		{
			ip = args[0];
		}
		else
		{
			ip = "localhost";
		}
	
		Client cli = new Client();
		cli.job();
	}
	
}
