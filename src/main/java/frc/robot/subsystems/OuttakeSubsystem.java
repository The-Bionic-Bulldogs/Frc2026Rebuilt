package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.TurretConstants;
import frc.robot.LimelightHelpers;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;

public class OuttakeSubsystem extends SubsystemBase {
  
  //motors
  private final TalonFX turretOuttakeMotor = new TalonFX(TurretConstants.kTurretOuttakeMotorPort);
  private final TalonFX FeederMotorOne = new TalonFX(Constants.TurretConstants.kFeederFirstMotorPort);
  private final TalonFX FeederMotorTwo = new TalonFX(Constants.TurretConstants.kFeederSecondMotorPort);
  private final TalonFX RollerMotor = new TalonFX(Constants.TurretConstants.kRollerMotorPort);
private final InterpolatingDoubleTreeMap shooterTable = new InterpolatingDoubleTreeMap();
  // Initialize motors, set soft limits for turret rotation, and set neutral modes
  public void init() {
    
    

    turretOuttakeMotor.setNeutralMode(NeutralModeValue.Coast);

    FeederMotorOne.setNeutralMode(NeutralModeValue.Coast);
    FeederMotorTwo.setNeutralMode(NeutralModeValue.Coast);
    RollerMotor.setNeutralMode(NeutralModeValue.Coast);
  }
   //calls init
  public OuttakeSubsystem() {
    init();
/* 
    shooterTable.put(1.026, 0.395); // close
    shooterTable.put(0.529, 0.39); // mid
    shooterTable.put(0.17, 0.69); // far
*/
  }

public double getShooterSpeed() {
    double ta = LimelightHelpers.getTA("limelight-bb");
    return shooterTable.get(ta);
}

    // Command to start outtake sequence
  public Command startOuttake() {
  return Commands.sequence(

    // Start shooter motor
    runOnce(() -> {
     // double speed = getShooterSpeed();
      turretOuttakeMotor.set(Constants.TurretConstants.kOuttakeSpeed);
    }),

    // Wait half a second
    Commands.waitSeconds(0.5),

    // Run continuously until command is cancelled
    run(() -> {
   //   double speed = getShooterSpeed();

      turretOuttakeMotor.set(Constants.TurretConstants.kOuttakeSpeed);
      FeederMotorOne.set(Constants.TurretConstants.kFeederSpeed);
      FeederMotorTwo.set(Constants.TurretConstants.kFeederSpeed);
      RollerMotor.set(Constants.TurretConstants.kRollerSpeed);
    })

  );
}

  //stops all motors in the outtake sequence
  public Command stopOuttake() {
    return 
     runOnce(() -> {
      turretOuttakeMotor.set(0.0);
      FeederMotorOne.set(0.0);
      FeederMotorTwo.set(0.0);
      RollerMotor.set(0.0);
    });
    
  }
 
}
