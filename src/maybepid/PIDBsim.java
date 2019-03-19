package maybepid;

import maybepid.PIDB;
import java.awt.*;

public class PIDBsim {

    static double position = 10;
	static double velocity = 0;
	static double acceleration = 0;
	
	static double efficiency = 0.1;
	static double statFric = 0.1;
	
	static double target = 260;
	
	static double maxPower = 10;
	static double mass = 5;
    
    static PIDB pidb;

    public static void main(String[] args) {

        pidb = new PIDB(0.07, 0.02, 0.0, 0.4, 1);
        Boolean done = false;

        while(!done) {

			if (MouseInfo.getPointerInfo().getLocation().getX() > 1000){
				target = 700 * (260.0/1439.0);
			}else{
				target = 250 * (260.0/1439.0);
			}
            target = MouseInfo.getPointerInfo().getLocation().getX() * (260.0/1919.0);

			pidb.loop(position, target);
			
			double power = pidb.getPower();
			
			if (Math.abs(power) > statFric) {
				if(power > maxPower) {
					power = maxPower;
				}else if(power < -maxPower) {
					power = -maxPower;
				}
			}else{
				power = 0.0;
			}
			acceleration = power * mass;
			
			velocity = (velocity + acceleration) * efficiency;
			position = position + velocity;

			display((int) Math.round(position));
			System.out.println("P: " + pidb.getP() + "      I: " + pidb.getI() + "     D: " + pidb.getD() + "     Err: " + pidb.getError() + "     POW: " + power);
			
			if(pidb.inTolerance) {
				//comment out the following line to make it keep running after it reaches the tolerances
				//done = true;
			}

			//20 millisecond delay or 50 times a second
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
    }

    

    public static void display(int position) {
		System.out.print("||");
		for(int i = 0; i < position; i++) {
			System.out.print(" ");
		}
		System.out.println("I");
	}


}