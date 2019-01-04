package maybepid;

import java.awt.MouseInfo;

public class Physics {

	//TUNE
	final static double kP = 0.06;
	final static double kI = 0.045;
	final static double kD = 0.5;
	final static double posTolerance = 2;
	final static double velTolerance = 0.01;
	//Less likely to overshoot, still goes fast in the beginning but slows down a lot. 
	
	
	static double position = 10;
	static double velocity = 0;
	static double acceleration = 0;
	
	static double efficiency = 0.95;
	
	static double target = 260;
	static double error = target;
	static double lasterror;
	
	static double P = 0.0;
	static double I = 0.0;
	static double D = 0.0;
	
	
	public static void main(String[] args) {
		
		double startTime = System.currentTimeMillis();
		Boolean done = false;

		while(!done) {
			
			target = MouseInfo.getPointerInfo().getLocation().getX() * (260.0/1919.0);
			System.out.println("target: " + target);
			
			lasterror = error;
			error = target - position;
			
			if (Math.abs(error) < 2 ) {
				I = 0;
			}
			
			P = kP * error;
			I = kI * (I + error);
			D = kD * (error - lasterror);
			
			acceleration = P + I + D;
			
			velocity = (velocity + acceleration) * efficiency;
			position = position + velocity;
			
			
			
			display((int) Math.round(position));
			System.out.println("P: " + (double) P + "      I: " + (double) I + "     D: " + (double) D);
			
			if(Math.abs(velocity) < velTolerance && Math.abs(error) < posTolerance) {
				//comment out the following line to make it keep running after it reaches the tolerances
				done = true;
			}

			//20 millisecond delay or 50 times a second
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
		System.out.println("target reached in  " + (System.currentTimeMillis() - startTime) + " milliseconds.");
		System.out.println("ending velocity:  " + velocity);
	}
	
	public static void display(int position) {
		System.out.print("||");
		for(int i = 0; i < position; i++) {
			System.out.print(" ");
		}
		System.out.println("I");
	}

}
