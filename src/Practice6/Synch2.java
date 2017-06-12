package Practice6;

import java.util.*;
public class Synch2 {
 public static void main(String[] args) {
  Store s = new Store();
  Producer p = new Producer(s);
  Consumer c = new Consumer(s);
  p.start();
  c.start();
 }
}
class Store {
 protected Vector stocks = new Vector();
 public synchronized void add(String product) {
  stocks.add(product);
  System.out.println(product + " was produced");
  this.notify();
 }
 public synchronized String buy() {
  while(true) {
   if(stocks.isEmpty()) {
    try {
     this.wait();
    } catch(InterruptedException ignored) {}
   } else {
    break;
   }
  }
  String product = (String)stocks.remove(0);
  System.out.println("                         " + 
product + " was consumed");
  return product;
 }
}

class Producer extends Thread {
 protected Store store;
 public Producer(Store store) {
  this.store = store;
 }
 public void run() {
  int i = 0;
  while(true) {
   try {
    Thread.sleep((int)(Math.random() * 3000));
   } catch(InterruptedException ignored) {}
   String s = new String("Product #" + i++);
   store.add(s);
  }
 }
}

class Consumer extends Thread {
 protected Store store;
 public Consumer(Store store) {
  this.store = store;
 }
 public void run() {
  while(true) {
   try {
    Thread.sleep((int)(Math.random() * 3000));
   } catch(InterruptedException ignored) {}
   store.buy();
  }
 }
}