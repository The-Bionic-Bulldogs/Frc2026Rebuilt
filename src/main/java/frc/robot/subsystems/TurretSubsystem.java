package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import frc.robot.Constants;

public class TurretSubsystem extends SubsystemBase {
  private final int turretOuttakeMotorPort = Constants.TurretConstants.kTurretOuttakeMotorPort;
  private final int turretRotationMotorPort = Constants.TurretConstants.kTurretRotationMotorPort;
  private final TalonFX turretOuttakeMotor = new TalonFX(turretOuttakeMotorPort);
  private final TalonFX turretRotationMotor = new TalonFX(turretRotationMotorPort);
  private final TalonFX FeederMotorOne = new TalonFX(Constants.TurretConstants.kFeederFirstMotorPort);
  private final TalonFX FeederMotorTwo = new TalonFX(Constants.TurretConstants.kFeederSecondMotorPort);
  private final TalonFX RollerMotor = new TalonFX(Constants.TurretConstants.kRollerMotorPort);

  public TurretSubsystem() {
    turretOuttakeMotor.setNeutralMode(NeutralModeValue.Coast);
    turretRotationMotor.setNeutralMode(NeutralModeValue.Brake);
    FeederMotorOne.setNeutralMode(NeutralModeValue.Coast);
    FeederMotorTwo.setNeutralMode(NeutralModeValue.Coast);
    RollerMotor.setNeutralMode(NeutralModeValue.Coast);
  }

  public Command startOuttake() {
    return runOnce(() -> {
      turretOuttakeMotor.set(Constants.TurretConstants.kOuttakeSpeed);
      FeederMotorOne.set(-Constants.TurretConstants.kFeederSpeed);
      FeederMotorTwo.set(Constants.TurretConstants.kFeederSpeed);
      RollerMotor.set(Constants.TurretConstants.kRollerSpeed);
    });
  }
  public Command stopOuttake() {
    return runOnce(() -> {
      turretOuttakeMotor.set(0.0);
      FeederMotorOne.set(0.0);
      FeederMotorTwo.set(0.0);
      RollerMotor.set(0.0);
    });
    
  }
  public Command rotateTurret(double speed) {
    double limitedSpeed = MathUtil.clamp(speed, -Constants.TurretConstants.kRotateSpeed, Constants.TurretConstants.kRotateSpeed);
    return runOnce(() -> turretRotationMotor.set(limitedSpeed));
  }
}
