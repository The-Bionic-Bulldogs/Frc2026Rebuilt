package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Constants.TurretConstants;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;

public class TurretSubsystem extends SubsystemBase {
  
  //motors
  private final TalonFX turretOuttakeMotor = new TalonFX(TurretConstants.kTurretOuttakeMotorPort);
  private final TalonFX turretRotationMotor = new TalonFX(TurretConstants.kTurretRotationMotorPort);
  private final TalonFX FeederMotorOne = new TalonFX(Constants.TurretConstants.kFeederFirstMotorPort);
  private final TalonFX FeederMotorTwo = new TalonFX(Constants.TurretConstants.kFeederSecondMotorPort);
  private final TalonFX RollerMotor = new TalonFX(Constants.TurretConstants.kRollerMotorPort);

  // Initialize motors, set soft limits for turret rotation, and set neutral modes
  public void init() {
    TalonFXConfiguration config = new TalonFXConfiguration();
    var softLimitConfig = new SoftwareLimitSwitchConfigs();
    softLimitConfig.ForwardSoftLimitEnable = true;
    softLimitConfig.ForwardSoftLimitThreshold = Constants.TurretConstants.kSoftlimit;
    softLimitConfig.ReverseSoftLimitEnable = true;
    softLimitConfig.ReverseSoftLimitThreshold = Constants.TurretConstants.kMinLimit;
    config.SoftwareLimitSwitch = softLimitConfig;
    turretRotationMotor.getConfigurator().apply(config);

    turretRotationMotor.setPosition(0);

    turretOuttakeMotor.setNeutralMode(NeutralModeValue.Coast);
    turretRotationMotor.setNeutralMode(NeutralModeValue.Brake);
    FeederMotorOne.setNeutralMode(NeutralModeValue.Coast);
    FeederMotorTwo.setNeutralMode(NeutralModeValue.Coast);
    RollerMotor.setNeutralMode(NeutralModeValue.Coast);
  }
   //calls init
  public TurretSubsystem() {
    init();
  }
    // Command to start outtake sequence
  public Command startOuttake() {
    return  Commands.sequence(

    // Start outtake motor immediately
    runOnce(() -> 
      turretOuttakeMotor.set(Constants.TurretConstants.kOuttakeSpeed)
    ),

    // Wait 0.5 seconds
    Commands.waitSeconds(0.5),

    // Then run the rest continuously until canceled
    run(() -> {
      turretOuttakeMotor.set(Constants.TurretConstants.kOuttakeSpeed);
      FeederMotorOne.set(-Constants.TurretConstants.kFeederSpeed);
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
  // Command to rotate turret at a given speed, with limits and Limelight vison
  public void rotateTurret(double speed) {
    double limitedSpeed = MathUtil.clamp(speed, -Constants.TurretConstants.kRotateSpeed, Constants.TurretConstants.kRotateSpeed);
    turretRotationMotor.set(limitedSpeed);
  }
}
