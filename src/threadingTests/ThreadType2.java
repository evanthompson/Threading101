package threadingTests;

import java.util.Scanner;

/*
 * Update : Successfully implemented Wait() and Notify() to pause and
 * resume the child Object.
 */
public class ThreadType2 extends Thread {
	
	private ThreadType2 nextThread = null;
	private int value = 0;
	private boolean running = true;
	private boolean pause = false;	
	private  boolean displayOn = true;	// displays every child print
	
	// Constructor
	ThreadType2(String name) {
		super(name);
		System.out.println("Creating instance " + name);
		
		if(name == "parent") {
			nextThread = new ThreadType2("child");
		}
		start();
	}
	
	// Run method
	public void run() {
		if(nextThread != null) {
			commune();
		} else { count(); }
		
		System.out.println("Exiting instance " + currentThread().getName());
	}
	
	public void count() {
		while(running) {
			value++;
			if(displayOn) {	/*System.out.println("value = " + value);*/	}
		
			synchronized (this) {
				try {
					while(pause) {
						System.out.println("pausing...");
						wait();
					}
				} catch (InterruptedException e) {
					System.out.println("InterruptedException");
				} catch (IllegalMonitorStateException e) {
					System.out.println("IllegalMonitorStateException");
				}
			}
			
			try { Thread.sleep(500); } catch (Exception e) { }
		}
	}
	
	public void commune() {
		int parsedNumber = 0;
		while(parsedNumber != 9) {
			try { Thread.sleep(200); } catch (Exception e) { }
			
			nextThread.toggleDisplay(false);
			parsedNumber = getParsed();
			nextThread.toggleDisplay(true);
			
			if(parsedNumber == 8) {
				System.out.println("Pausing counter...");
				nextThread.pausing(true);
			} else if(parsedNumber == 7) {
				System.out.println("Resuming counter...");
				nextThread.pausing(false);
				synchronized (nextThread) {
					try {
						nextThread.notify();
						System.out.println("Notifying...");
					} catch (IllegalMonitorStateException e) {
						System.out.println("IllegalMonitorStateException");
					}
				}				
			} else {
				System.out.println("Count is at: " + nextThread.getValue());
			}			
		}
		
		if(nextThread != null) {
			nextThread.killProc();
			try { Thread.sleep(600); } catch (Exception e) { }
		}
	}
	
	public int getParsed() {
		int number = 0;
		while(number == 0) {
			System.out.println("Enter a number (1-9):");
			Scanner input = new Scanner(System.in);
			String inputval = input.next();
			
			try {
				number = Integer.parseInt(inputval);
				if(number < 1 || number > 9) {
					System.out.println("That number is out of bounds");
					continue;
				}
			} catch(NumberFormatException e) {
				System.out.println("Incorrect User input...");
			}
		}
		return number;
	}
	
	public void killProc() {
		running = false;
		pause = false;
		
		synchronized (this) {
			try {				
				notify();
			} catch (IllegalMonitorStateException e) {
				System.out.println("IllegalMonitorStateException");
			}
		}
	}
	
	public void pausing(boolean state) {
		pause = state;
	}
	
	public void toggleDisplay(boolean b) {
		displayOn = b;
	}
	
	public int getValue() { return value; }
}
