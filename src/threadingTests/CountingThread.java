package threadingTests;

public class CountingThread implements Runnable {

	private int counter;
	private boolean print, running, killProcess;
	
	public CountingThread(int num) {
		counter = num;
		print = true;
		running = true;
		killProcess = false;
	}
	
	public void run() {
		for(int i = 0; i < counter; i++) {
			if(killProcess) { break; }
			if(print) {	System.out.println("counter: " + i); }
			try { Thread.sleep(500); } catch (Exception e) { }
		}
		System.out.println("Exiting Counter.");
	}
	
	public void killIt() {
		killProcess = true;
	}
	
	public void displayPrints(boolean toggle) {
		if(toggle == true) {
			print = true;
		} else { print = false; }
	}
	
	public synchronized void makeWait() {
		System.out.println("Will try a wait...");
		
		running = false;
		while(running == false) {
			System.out.println("running = " + running);
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException");
			} catch (IllegalMonitorStateException e) {
				System.out.println("IllegalMonitorStateException");
			}
		}
		
		System.out.println("exiting wait.");
		notify();
	}
	
	public synchronized void makeNotify() {
		running = true;
		try {
			notify();
			System.out.println("Counter notified...");
		} catch (IllegalMonitorStateException e) {
			System.out.println("Counter IllegalMonitorStateException");
		}
	}
}
