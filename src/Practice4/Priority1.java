package Practice4;

public class Priority1 {
	 public static void main(String[] args) {
	  MyThread t = new MyThread();
	  t.start();
	  for(int i=0 ; i<20 ; i++) {
	   System.out.println("main thread : " + i);
	  }
	 }
	}

class MyThread extends Thread {
	 public void run() {
	  for(int i=0 ; i<20 ; i++) {
	   System.out.println("MyThread thread : " + i);
	  }
	 }
	}
