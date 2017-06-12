package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame /*extends JFrame*/{
	
	// Main Frame
	
	private JFrame mainf = null;
	
	// ClinetServer
	private Socket xSocket = null;
	private int port = 5555;
	private static String user;
	
	// Client input, output stream
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	// ������ ���� ����� ó���� ������
	Thread threadinput = null;	
	
	// MainFrame_look_chat_panel
	private JTextArea Lchat;
	private JScrollPane Lchatscroll;
	
	
	// MainFrame_chat_panel
	private JTextArea chatting;
	private JScrollPane chatscroll;
	private JButton passtextBtn;
	
	// �������� �˻�
	private boolean readerflag = false;
	
	// Screen Socket
	private Socket scrSocket = null;
	private int scrport = 5556;
	
	
	// Screen Screen
	private JPanel Screen = null;
	private boolean fullscreen_flag = false;
	private BufferedInputStream scrin = null;
	private BufferedOutputStream bout = null;

	
	// full screen
	private Dimension fulldim = Toolkit.getDefaultToolkit().getScreenSize();
	
	
	// voice socket
	private Socket vocSocket = null;
	private int vocport = 5557;

	// voice stream
	private DataInputStream vocin = null;
	private DataOutputStream voice = null;

	// client size
	private JLabel clientsize;
	
	
	// vote
	private boolean voteflag = false;
	private int vote_yes = 0;
	private int vote_no = 0;
	private boolean voted = false;
	
	
	public static void main(String args[])
	{
		/*
		 *  Login
		 */
		JFrame login = new JFrame();
		login.setTitle("LogIn");
		login.setBounds(800, 400, 300, 180);
		login.setLayout(null);
		
		JLabel nick = new JLabel("�г����� �Է��� �ּ���.");
		nick.setBounds(70, 30, 150, 30);
		login.add(nick);
		
		JButton loginBtn = new JButton("Ȯ��");
		JTextField id = new JTextField();
		id.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent et)
			{
				if(et.getKeyChar() == KeyEvent.VK_ENTER)
				{
					login.dispose();
					
					/*
					 *  �������� ���̵� ��й�ȣ �˻� ���� �ؾ���.
					 */
					if(id.getText().length() != 0)
						user = id.getText();
					else
						user = "�մ�";
					
					// ���� ȭ��
					MainFrame main = new MainFrame();
					
					main.connect(args[0]);
				}
				else
				{
					if(id.getText().length() > 6)
						et.consume();
				}
			}
		});;
		
		id.setBounds(30, 70, 120, 30);
		
		login.add(id);
		
		loginBtn.setBounds(180, 60, 60, 40);
		loginBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				login.dispose();
				
				/*
				 *  �������� ���̵� ��й�ȣ �˻� ���� �ؾ���.
				 */
				if(id.getText().length() != 0)
					user = id.getText();
				else
					user = "�մ�";
				
				// ���� ȭ��
				MainFrame main = new MainFrame();
				
				main.connect(args[0]);
			}
		
		});
		login.add(loginBtn);
		
		login.setVisible(true);
		
		/*
		 *  Login
		 */
		
		
	}
	
	public MainFrame()
	{
		
		// Color
		Color Cmain = new Color(5, 255, 153);
		Color Ctextframe = new Color(189,189,189);
		
		mainf = new JFrame("Presentation");
		mainf.setBackground(Cmain);
		mainf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainf.setLayout(null);
		mainf.setBackground(Cmain);
		mainf.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				JFrame close = new JFrame("Close");
				close.setLayout(null);
				close.setBounds(fulldim.width/2-100,fulldim.height/2-100,350,200);
				close.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JLabel la = new JLabel("�����Ͻðڽ��ϱ�?");;
				la.setBounds(110,30,120,40);
				close.add(la);
				
				JButton yes = new JButton("��");
				JButton no = new JButton("�ƴϿ�");
				yes.setBounds(50, 100, 80, 30);
				no.setBounds(200, 100, 80, 30);
				close.add(yes);
				close.add(no);

				yes.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
						if(readerflag)
						{
							sendMessage("/reader out");
						}
						
						System.exit(0);
					}
					
				});
				no.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						close.dispose();
					}
					
				});
				
				close.setVisible(true);
			}
		});
		
		/*
		 *  ä�� ���� ��
		 */
		
		Lchat = new JTextArea();
		Lchat.setEditable(false);
		Lchat.setBackground(Ctextframe);
		Lchat.setLineWrap(true);
		Lchatscroll = new JScrollPane(Lchat);
		Lchatscroll.setBounds(fulldim.width - 320, 30, fulldim.width - 1620, fulldim.height - 350);		
		mainf.add(Lchatscroll);
		
		
		/*
		 *  ä�� �Է� ĭ
		 */
		
		chatting = new JTextArea();
		chatting.setBackground(Ctextframe);
		chatting.setLineWrap(true);
		chatting.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent et)
			{
				if(et.getKeyChar() == KeyEvent.VK_ENTER)
				{
					sendMessage("["+user+"] "+chatting.getText()+"\n");
					chatting.setText("");
					chatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
					Lchatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
				}
			}
		});
		
		chatscroll = new JScrollPane(chatting);
		chatscroll.setBounds(fulldim.width - 320, fulldim.height - 300, fulldim.width-1700, fulldim.height-900 );
		passtextBtn = new JButton("������");
		passtextBtn.setBounds(fulldim.width - 100, fulldim.height - 200, 80, 50);
		passtextBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(out != null && chatting.getText().length() != 0)
				{
					sendMessage("["+user+"] "+chatting.getText()+"\n");
					chatting.setText("");
					chatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
					Lchatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
				}
			}
			
		});
		mainf.add(chatscroll);
		mainf.add(passtextBtn);
		
		Screen = new JPanel();
		Screen.setLayout(null);
		Screen.setBounds(30, 40, fulldim.width-380 , fulldim.height - 200);
		Screen.setBackground(Color.black);
		
		// double click Full Screen
		Screen.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2)
				{
					if(!fullscreen_flag)
					{
						Screen.setBounds(0, 0, fulldim.width, fulldim.height);
					
						chatscroll.setVisible(false);
						Lchatscroll.setVisible(false);
						passtextBtn.setVisible(false);
						
						fullscreen_flag = true;
					}
					else
					{
						Screen.setBounds(30, 40, fulldim.width-380 , fulldim.height - 200);
						
						chatscroll.setVisible(true);
						Lchatscroll.setVisible(true);
						passtextBtn.setVisible(true);
						
						fullscreen_flag = false;
					}
				}
			}
		});
		mainf.add(Screen);
		
		clientsize = new JLabel();
		clientsize.setText("������ �� : 0");
		clientsize.setBounds(fulldim.width-320, 10, 100, 20);
		mainf.add(clientsize);
		
		mainf.setVisible(true);
		
	}
	
	private void connect(String server)
	{
		try
		{
			scrSocket = new Socket(server, scrport);
			xSocket = new Socket(server, port);
			vocSocket = new Socket(server, vocport);

			
			out = new PrintWriter(xSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(xSocket.getInputStream()));
			
			sendMessage("���ο� ����� ["+user+"]�� �����Ͽ����ϴ�.\n");
			
			sendMessage("/Im "+user);
			
			chatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
			Lchatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
			
			readmsg readThread = new readmsg();
			readThread.start();
			
			takeScreen readscrThread = new takeScreen();
			readscrThread.start();
			
			if(!readerflag)
			{
				readVoice readvocThread = new readVoice();
				readvocThread.start();
			}
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		finally
		{

		}
		
	}
	
	private synchronized void sendMessage(String msg)
	{
		if(msg.contains("/vote"))
		{
			if(voteflag)
			{
				if(!voted)
				{
					voted = true;
					
					out.println(msg);
				}
				else
					JOptionPane.showMessageDialog(null, "�̹� ��ǥ�߽��ϴ�.");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "��ǥ���� �ƴմϴ�.");
			}
		}
		else if(msg.contains("/endvote"))
		{
			if(readerflag)
				out.println(msg);
		}
		else
			out.println(msg);
	}
	
	private synchronized void addMessage(String msg)
	{
		if(msg.contains("/clientsize"))
		{			
			String tempstr[] = msg.split(" ");
			
			clientsize.setText("������ �� : " + tempstr[1]);
		}
		else if(msg.equals("/you are reader"))
		{
			readerflag = true;
			
			sendscreen scrThread = new sendscreen();
			scrThread.start();
			
			sendVoice vocThread = new sendVoice();
			vocThread.start();
						
			JOptionPane.showMessageDialog(null, "you're team reader");
		}
		else if(msg.equals("/reader out"))
		{
			JOptionPane.showMessageDialog(null, "����� ����Ǿ����ϴ�.");
			
			System.exit(0);
		}
		else if(msg.contains("/silent"))
		{
						
			String tempstr[] = msg.split(" ");
			
			if(user.equals(tempstr[2]))
			{
				chatting.setEditable(false);
				chatting.setText("��� ó�� �Ǿ����ϴ�.");
								
				sendMessage("["+user+"]�� ��� ó�� �Ǿ����ϴ�.\n");
		
				JOptionPane.showMessageDialog(null, "��� ó�� �Ǿ����ϴ�.");
				
				TimerTask time = new Time();
				
				Timer t = new Timer(false);
				
				t.schedule(time, 30000);
			}
		}
		else if(msg.contains("/startvote"))
		{
			if(voteflag)
			{
				if(readerflag)
				{
					JOptionPane.showMessageDialog(null, "�̹� ��ǥ�� �������Դϴ�.");
				}
			}
			else
			{
				String tempstr[] = msg.split(" ");
			
				String str = "";
			
				for(int i = 2 ; i < tempstr.length ; ++i)
				{
					str += tempstr[i];
					str += " ";
				}

				Lchat.append("<<<<<< ��ǥ�� �����մϴ� >>>>>>\n\n"+str+"\n\n�����ϽŴٸ� /vote yes\n"+"�ݴ��ϽŴٸ� /vote no\n�� �Է��ϼ���\n\n");
		
				Lchat.append("��ǥ����� ������ /endvote�� �Է��Ͽ��� �� ����˴ϴ�.");
			
				voteflag = true;
			}
		}
		else if(msg.contains("/vote"))
		{			
			if(readerflag)
			{
				String tempstr[] = msg.split(" ");

				if(tempstr[2].equals("yes"))
				{
					++vote_yes;
				}
				else if(tempstr[2].equals("no"))
				{
					++vote_no;
				}
			}	
		}
		else if(msg.contains("/endvote"))
		{
			if(readerflag)
			{
				if(voteflag)
				{
					voteflag = false;
					voted = false;
				
					String str = "";
					str += "��ǥ���\n\n";
					str += "���� : "+ vote_yes;
					str += "\n�ݴ� : "+ vote_no;
					
					if(vote_yes > vote_no)
						str += "\n\n"+(vote_yes-vote_no)+"ǥ ���̷� �������� �����Ǿ����ϴ�.\n";
					else if(vote_no > vote_yes)
						 str += "\n\n"+(vote_no-vote_yes)+"ǥ ���̷� �ݴ�� �����Ǿ����ϴ�.\n";
					else
						 str += "\n\n"+(vote_no-vote_yes)+"ǥ ���̷� ��ȿó�� �Ǿ����ϴ�.\n";
					
					sendMessage(str);
				
					vote_yes = 0;
					vote_no = 0;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "�������� ��ǥ�� �����ϴ�.");
				}
				
			}
		}
		else
		{
			Lchat.append(msg+"\n");
		}
			
		chatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
		Lchatscroll.getVerticalScrollBar().setValue(Lchatscroll.getVerticalScrollBar().getMaximum());
	}
	
	//timer
	private class Time extends TimerTask{
		public void run()
		{
			chatting.setText("");
			chatting.setEditable(true);
		}
	}
	
	/*
	 *  capture�� �ʿ��Ѱ�
	 */
	
	private class sendscreen extends Thread
	{
		public void run()
		{			
			try
			{
				
				BufferedImage image;
				Robot r = new Robot();
				bout = new BufferedOutputStream(scrSocket.getOutputStream());
				
				while(true)
				{					
					image = r.createScreenCapture(new Rectangle(0,0,fulldim.width,fulldim.height));
						
					ImageIO.write(image, "BMP", bout);
										
					bout.flush();
					
					Thread.sleep(100);
					
					if(bout == null) 
						break;
				}
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
			finally
			{
				try
				{
					if(bout != null)
						bout.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
		}
	}
	
	private class takeScreen extends Thread
	{
		public void run()
		{
			try{
				scrin = new BufferedInputStream(scrSocket.getInputStream());
								
				BufferedImage buf = null;
				
				while(true)
				{	
					
					buf = ImageIO.read(ImageIO.createImageInputStream(scrin));
					
					if(buf == null)
					{
						break;
					}
					
					Screen.getGraphics().drawImage(buf,0,0,Screen.getWidth(),Screen.getHeight(),Screen);
					
					Thread.sleep(100);
					
				}
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
			finally
			{
				try
				{
					if(scrin != null)
						scrin.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
		}
	}
	
	// Voice
	public class sendVoice extends Thread
	{
		public void run()
		{
			AudioFormat af = new AudioFormat(8000.0f,8,1,true,false);
			
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
			
			
			try
			{
				voice = new DataOutputStream(vocSocket.getOutputStream());
				
				TargetDataLine microphone = (TargetDataLine)AudioSystem.getLine(info);
				
				microphone.open(af);
				
				microphone.start();
				
				int bytesRead = 0;
				byte[] soundData = new byte[1];
				
				while(bytesRead != -1)
				{
					bytesRead = microphone.read(soundData, 0, soundData.length);
					
					if(bytesRead >= 0)
					{
						voice.write(soundData, 0, bytesRead);
					}
				}
				
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
			finally
			{
				try
				{
					if(voice != null)
						voice.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
		}
	}
	
	public class readVoice extends Thread
	{
		public void run()
		{			
			try
			{
				vocin = new DataInputStream(vocSocket.getInputStream());
				
				AudioFormat af = new AudioFormat(8000.0f,8,1,true,false);
				
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
				
				SourceDataLine speaker = null;
				
				speaker = (SourceDataLine) AudioSystem.getLine(info);
				
				speaker.open(af);
				
				int bytesRead = 0;
				byte[] sound = new byte[1];
				speaker.start();
				
				while(bytesRead != -1)
				{
					bytesRead = vocin.read(sound, 0, sound.length);
					
					if(bytesRead >= 0)
					{
						speaker.write(sound, 0, bytesRead);
					}
				}
			}
			catch(Exception e)
			{
				System.err.println(e);
			}
			finally
			{
				try
				{
					if(vocin != null)
						vocin.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
		}
	}
	
	
	private class readmsg extends Thread
	{
		public void run()
		{
			try{
				String str;
				while((str = in.readLine()) != null)
				{
					addMessage(str);
				}
			}
			catch(IOException e)
			{
				System.err.println(e);
			}
			finally
			{
				try
				{
					if(in != null)
						in.close();
					if(out != null)
						out.close();
					if(xSocket != null)
						xSocket.close();
					if(vocSocket != null)
						vocSocket.close();
					if(scrSocket != null)
						scrSocket.close();
					if(bout != null)
						bout.close();
					if(scrin != null)
						scrin.close();
					if(vocin != null)
						vocin.close();
					if(voice != null)
						voice.close();
				}
				catch(Exception e)
				{
					System.err.println(e);
				}
			}
		}
	}
}
