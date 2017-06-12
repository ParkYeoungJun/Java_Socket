package Practice4;

public class Priority2 {
	 public static void main(String[] args) {
	  MyThread1 t1 = new MyThread1();
	  MyThread1 t2 = new MyThread1();
	  MyThread1 t3 = new MyThread1();
	  
	  Thread.currentThread().setPriority(Thread.MAX_PRIORITY);	  
	  
	  System.out.println("Start");
	  t1.start();
	  t2.start();
	  t3.start();

	  try{
		  Thread.sleep(1000);
	  }catch (InterruptedException e){}
	  
	  for(int i=0 ; i<20 ; i++) {
	   System.out.println("main thread : " + i);
	  }
	 }
	}

	class MyThread1 extends Thread {
	 public void run() {
	  for(int i=0 ; i<20 ; i++) {
	   System.out.println("MyThread thread : " + i);
	  }
	 }
	}
	