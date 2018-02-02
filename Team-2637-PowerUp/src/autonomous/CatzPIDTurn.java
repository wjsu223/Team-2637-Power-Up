package autonomous;
import org.usfirst.frc.team2637.robot.CatzRobotMap;

import constants.CatzConstants;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/*
 *  Author : Derek Duenas
 *  Last Revised : 1-28-2018 DD
 *  Translated from C++ and added integral term
 *  Methods : PIDturn
 *  Functionality : Accurately turn autonomously
 */
public class CatzPIDTurn
{
	static CatzRobotMap instance;
	static void PIDturn(double turnDegrees, int timeoutSeconds)
	{
		Timer functionTimer = new Timer();
		Timer pdTimer = new Timer();
		
		instance = CatzRobotMap.getInstance();
		instance.navx.reset();
		functionTimer.delay(CatzConstants.WAIT_0_1_SECONDS);
		boolean done = false;
		int PDTurnLoopcount = CatzConstants.ZERO_INT;
		double turnToDegrees = turnDegrees + instance.navx.getAngle();
		double turnThreshold = CatzConstants.TURN_THRESHOLD_0_1;
		double currentError;
		double previousError = CatzConstants.ZERO_DOUBLE;
		
		double deltaError;
		double derivative; 
		double deltaT;
		double power;
		
		double totalError = CatzConstants.ZERO_DOUBLE;
		
		functionTimer.reset();
		functionTimer.start();
		
		pdTimer.reset();
		
		while(Math.abs(instance.navx.getAngle()) < Math.abs(turnToDegrees)-turnThreshold ||
				Math.abs(instance.navx.getAngle()) > Math.abs(turnToDegrees)+turnThreshold && done!= true)
		{
			// make data array for deltaT,currentError; loop of about 100 + counter for how many loops
			pdTimer.stop(); // empty function
			
			deltaT = pdTimer.get();
			pdTimer.reset();
			pdTimer.start();

			currentError = turnToDegrees-instance.navx.getAngle();
			deltaError = currentError-previousError;
			totalError += currentError * deltaT;           
			derivative = deltaError/deltaT;

			power = .6*((CatzConstants.TURN_KP*currentError)+(CatzConstants.TURN_KD*derivative)+(CatzConstants.TURN_KI * (totalError)));
			
			instance.drive.tankDrive(power,-power);

			previousError = currentError;

			if (functionTimer.get() > timeoutSeconds)
				done = true;
			
			if(totalError >= CatzConstants.POS_MAX)    // saturation
				totalError = CatzConstants.POS_MAX;
			
			if(totalError <= CatzConstants.NEG_MAX)
				totalError = CatzConstants.NEG_MAX;
			
			SmartDashboard.putNumber("PDTurn:NavxReading",instance.navx.getAngle());
			SmartDashboard.putNumber("PDTurn:TimerReading",functionTimer.get());
			SmartDashboard.putNumber("PDTurn:LoopCount",PDTurnLoopcount); 
		}

		instance.drive.tankDrive(0,0);
		functionTimer.stop();
		functionTimer.reset();
		
		pdTimer.stop();
		pdTimer.reset();
	}
	
}
