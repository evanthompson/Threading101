package threadingTests;

import java.util.Scanner;

public class ThreadType2 extends Thread {
	
	private ThreadType2 nextThread = null;
	private int value, increment, instance;
	private boolean running = true;
	private boolean pause = false;
	private boolean displayOn = true;
	
	// Constructor
	ThreadType2(int num, int inst) {
		super("Type2 Thread");
		instance = inst;
		System.out.println("instance: " + instance + " created.");
		
		value = num;
		increment = 1;
		if(instance == 1) {
			nextThread = new ThreadType2(value, instance + 1);
		}
		start();
	}
	
	// Run method
	public void run() {
		if(nextThread != null) {
			commune();
		} else { count(); }
		
		System.out.println("Exiting instance " + instance);
	}
	
	public void count() {
		while(running) {
			if(!pause) {
				value+= increment;
				if(displayOn) {	System.out.println("value = " + value);	}
			}
			try { Thread.sleep(500); } catch (Exception e) { }
		}
	}
	
	public void killProc() {
		running = false;
	}
	public void pausing(boolean state) {
		pause = state;
	}
	
	public void commune() {
		int parsedNumber = 0;
		while(parsedNumber != 9) {
			try { Thread.sleep(1000); } catch (Exception e) { }
			
			nextThread.toggleDisplay(false);
			parsedNumber = getParsed();
			nextThread.toggleDisplay(true);
			
			if(parsedNumber == 8) {
				System.out.println("Pausing counter...");
				nextThread.pausing(true);
			} else if(parsedNumber == 7) {
				System.out.println("Resuming counter...");
				nextThread.pausing(false);
			} else {
				System.out.println("Regular output");
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
	
	public void toggleDisplay(boolean b) {
		displayOn = b;
	}
	
	// Getters and Setters
	public void setValue(int x) { value = x; }
	public void setInc(int x) { increment = x; }
	public int getValue() { return value; }
	public int getInc() { return increment; }
}
