package autonomous;

import constants.CatzConstants;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CatzAutonomousInit
{
	
	public static void runAutnomousInit()
	{
		choosePath();
		//CatzPIDTurn.setDebugModeEnabled( true );
		//CatzPIDTurn.PIDturn(90, 5);
		//CatzPIDTurn.PIDturn(-90, 5);
	}
	
	public static void choosePath() {
		
		String gameData;
		
		boolean check_box1 = SmartDashboard.getBoolean(CatzConstants.POSITION_SELECTORL, false);
		boolean check_box2 = SmartDashboard.getBoolean(CatzConstants.POSITION_SELECTORM, false);
		boolean check_box3 = SmartDashboard.getBoolean(CatzConstants.POSITION_SELECTORR, false); 

		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		System.out.println(gameData);
		if (gameData.charAt(0) == 'L' && check_box2 == true) {
			System.out.println("middlePathL");
			CatzAutonomousPaths.middlePathL();
			System.out.println("Init Done");
			
		} else if (gameData.charAt(0) == 'R' && check_box2 == true) {
			System.out.println("middlePathR");
			CatzAutonomousPaths.middlePathR();
			System.out.println("Init Done");
			
		} else if(gameData.charAt(1) == 'R' && check_box3 == true){
			System.out.println("rightPath");
			CatzAutonomousPaths.rightPath();	
			System.out.println("Init Done");
			
		} else if(gameData.charAt(1) == 'L' && check_box1 == true){
			System.out.println("LeftPath");
			CatzAutonomousPaths.leftPath();
			System.out.println("Init Done");
			
		} else {
			System.out.println("Init failed");
			CatzPIDDrive.encoderStraightDrive(0.5,10,3);
		}
			
		
	}

}
