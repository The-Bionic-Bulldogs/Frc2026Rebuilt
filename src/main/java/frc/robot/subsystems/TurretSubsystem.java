package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import frc.robot.Constants;

public class TurretSubsystem extends SubsystemBase {
  private final int turretOuttakeMotorPort = Constants.TurretConstants.kTurretOuttakeMotorPort;
  private final int turretRotationMotorPort = Constants.TurretConstants.kTurretRotationMotorPort;
  private final TalonFX turretOuttakeMotor = new TalonFX(turretOuttakeMotorPort);
  private final TalonFX turretRotationMotor = new TalonFX(turretRotationMotorPort);

  public TurretSubsystem() {
    turretOuttakeMotor.setNeutralMode(NeutralModeValue.Coast);
    turretRotationMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public Command startOuttake() {
    return runOnce(() -> turretOuttakeMotor.set(Constants.TurretConstants.kOuttakeSpeed));
  }
  public Command stopOuttake() {
    return runOnce(() -> turretOuttakeMotor.set(0.0));
  }
  public Command rotateTurret(double speed) {
    double limitedSpeed = MathUtil.clamp(speed, -Constants.TurretConstants.kRotateSpeed, Constants.TurretConstants.kRotateSpeed);
    return runOnce(() -> turretRotationMotor.set(limitedSpeed));
  }
}
