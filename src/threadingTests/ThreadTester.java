package threadingTests;

import java.util.Scanner;

public class ThreadTester {
	
	
	public static void waiting (int n){
		long t0, t1;
		t0 = System.currentTimeMillis();
		do {
			t1 = System.currentTimeMillis();
		} while ((t1 - t0) < (n * 1000));
	}
	
	public static void main(String[] args) {
		
		CountingThread counter = new CountingThread(100);
		new Thread(counter).start();
		
		int parsedNumber = 0;
		while(parsedNumber != 9) {
			waiting(2);
			counter.displayPrints(false);
			while(true) {
				System.out.println("Enter a number (0-9):");
				Scanner input = new Scanner(System.in);
				String inputval = input.next();
				
				try {
					parsedNumber = Integer.parseInt(inputval);
					System.out.println("The number is: " + parsedNumber);
					if(parsedNumber < 1 || parsedNumber > 9) {
						System.out.println("That number is out of bounds");
						continue;
					}
					break;
				} catch(NumberFormatException e) {
					System.out.println("Incorrect User input...");
				}
			}
			counter.displayPrints(true);
			
			if(parsedNumber == 8) {
				counter.makeWait();
				
			} else if(parsedNumber == 7) {
				counter.makeNotify();
				System.out.println("Counter notified...");
				
			} else {
				System.out.println("Regular output");
			}			
		}
		
		System.out.println("Main thread exiting.");
		counter.killIt();
	}
}
