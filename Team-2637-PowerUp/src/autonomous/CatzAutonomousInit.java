/*******************************************************
 *  Author : Jean Kwon
 *   Last Revised : 2-19-2018 JK
 *  Last revision summary: rename the check box
 *  Methods: runAutonomousInit, setMechanisms, liftToSwitchHeight, liftToScaleHeight, choosePath
 *  Functionality: choose the path for autonomous
*******************************************************/

package autonomous;

import constants.CatzConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.CatzRobotMap;

public class CatzAutonomousInit
{
	
	public static void runAutonomousInit()
	{
		//retractMechanisms();
		CatzRobotMap.grabber.retractGrabber();
		//choosePath();
		//CatzPIDTurn.setDebugModeEnabled( true );
		//CatzPIDTurn.PIDturn(-45, 5);
		//CatzPIDTurn.PIDturn(-90, 10);
		//CatzPIDDrive.PIDDrive(0.7, 100, 3);
		//CatzAutonomousPaths.middlePathL();
	}

	public static void retractMechanisms() 
	{
		CatzRobotMap.grabber.closeForearm();
		CatzRobotMap.grabber.retractBicep();
	}
	public static void deployMechanisms()
	{
		CatzRobotMap.grabber.deployBicep();
		CatzRobotMap.grabber.openForearm();
	}
	public static void outtakeCubeToScale()
	{
		CatzRobotMap.lift.liftToScaleHeight();
		deployMechanisms();
		CatzRobotMap.grabber.setIntakeSpeed(-CatzConstants.INTAKE_SPEED);
		Timer.delay(CatzConstants.CUBE_OUTTAKE_WAIT_TIME);
		retractMechanisms();
	}
	public static void outtakeCubeToSwitch()
	{
		CatzRobotMap.lift.liftToSwitchHeight();
		deployMechanisms();
		CatzRobotMap.grabber.setIntakeSpeed(-CatzConstants.INTAKE_SPEED);
		Timer.delay(CatzConstants.CUBE_OUTTAKE_WAIT_TIME);
		retractMechanisms();
	}
	public static void intakeCube()
	{
		CatzRobotMap.lift.dropToGroundHeight();
		deployMechanisms();
		CatzRobotMap.grabber.setIntakeSpeed(CatzConstants.INTAKE_SPEED);
		Timer.delay(CatzConstants.CUBE_INTAKE_WAIT_TIME);
		retractMechanisms();
	}

	
	public static void choosePath() {	
		String gameData;
		
		boolean check_boxL = SmartDashboard.getBoolean(CatzConstants.POSITION_SELECTORL, false);
		boolean check_boxM = SmartDashboard.getBoolean(CatzConstants.POSITION_SELECTORM, false);
		boolean check_boxR = SmartDashboard.getBoolean(CatzConstants.POSITION_SELECTORR, false); 

		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		System.out.println(gameData);
		if (gameData.charAt(0) == 'L' && check_boxM == true) {
			printOutDebugData("middlePathL");
			CatzAutonomousPaths.middleSingle_LXX();
			printOutDebugData("Init Done");
			
		} else if (gameData.charAt(0) == 'R' && check_boxM == true) {
			printOutDebugData("middlePathR");
			CatzAutonomousPaths.middleSingle_RXX();
			printOutDebugData("Init Done");
			
		} else if(gameData.charAt(1) == 'R' && check_boxR == true){
			printOutDebugData("rightPath");
			CatzAutonomousPaths.right_XRX();	
			printOutDebugData("Init Done");
			
		} else if(gameData.charAt(1) == 'L' && check_boxL == true){
			printOutDebugData("LeftPath");
			CatzAutonomousPaths.left_XLX();
			printOutDebugData("Init Done");
			
		} else {
			printOutDebugData("Init failed");
			CatzPIDDrive.PIDDrive(CatzConstants.AUTO_STARTPOS_DEF_SPEED,
		              			  CatzConstants.AUTO_STARTPOS_DEF_DISTANCE,
		                          CatzConstants.AUTO_STARTPOS_DEF_TIMEOUT); //if all else fails, just drive forward
		}
	}
	
	private static void printOutDebugData(String info) {
		if(CatzRobotMap.debugMode == true) {
			double currentTime = CatzRobotMap.globalTimer.get();
			System.out.println(currentTime + "  -" + info);
		}
	}

}
