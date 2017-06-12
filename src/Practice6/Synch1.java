package Practice6;

public class Synch1 {
	 public static void main(String[] args) {
	  Computer com = new Computer();
	  MyThread thread1 = new MyThread("Mike", com);
	  MyThread thread2 = new MyThread("Lala", com);
	  thread1.start();
	  thread2.start();
	 }
	}
	class Computer {
	 public synchronized void use(String user) {
	  System.out.println("Computer is being used by " + user);
	  try {
	   Thread.sleep(777);
	  } catch(InterruptedException ignored) {}
	  System.out.println("Computer is off");
	 }
	}
	class MyThread extends Thread {
	 protected String name;
	 protected Computer com;
	 public MyThread(String name, Computer com) {
	  super(name);
	  this.name = name;
	  this.com = com;
	 }
	 public void run() {
	  while(true) {
	   try {
	    Thread.sleep((int)(Math.random() * 3000));
	   } catch(InterruptedException ignored) {}
	   com.use(name);
	  }
	 }
	}