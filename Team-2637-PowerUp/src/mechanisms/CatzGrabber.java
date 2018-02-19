/************************************************
 * Timothy Vu
 * 
 * Last Revised: 2/19/18
 * 
 * added printout debug data code
 * 
 * Methods: openFlapToggle(), intakeCube(), launchCube(), 
 * deployIntake(), retractIntake()
 * 
 * Functionality:
 ***********************************************/

package mechanisms;

import org.usfirst.frc.team2637.robot.CatzRobotMap;
import constants.CatzConstants;

public class CatzGrabber 
{
	public CatzGrabber() {
		printOutDebugData("CatzGrabber successfully initialized");
	}
	
	public void setIntakeSpeed(double relativeSpeed) {
		CatzRobotMap.intakeLeft.set(relativeSpeed);
		CatzRobotMap.intakeRight.set(-relativeSpeed);
	}
	
	public void forearmOpen() {
		CatzConstants.forearmOpen = true;
		CatzRobotMap.intakeForearm.set(CatzConstants.forearmOpen);
		printOutDebugData("Grabber forearm set to Open");
	}

	public void forearmClose() {
		CatzConstants.forearmOpen = false;
		CatzRobotMap.intakeForearm.set(CatzConstants.forearmOpen);
		printOutDebugData("Grabber forearm set to Closed");
	}
	
	public void toggleForearm() {
		if(CatzConstants.forearmOpen == true) 
		{
			this.forearmClose();
		}
		else
		{
			this.forearmOpen();
		}
	}

	public void retractBicep() { 
		CatzConstants.bicepDeployed = false;
		CatzRobotMap.intakeBicep.set(CatzConstants.bicepDeployed);
		printOutDebugData("Grabber Bicep set to Retract");
	}

	public void deployBicep() {
		CatzConstants.bicepDeployed = true;
		CatzRobotMap.intakeBicep.set(CatzConstants.bicepDeployed);
		printOutDebugData("Grabber forearm set to Deploy");
	}
	
	private static void printOutDebugData(String data){
		if(CatzRobotMap.debugMode==true) {
			System.out.println(data);
		}
	}
}
