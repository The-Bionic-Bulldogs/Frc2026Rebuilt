package frc.robot.subsystems;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.TurretConstants;

public class RotationSubsystem extends SubsystemBase{
private final TalonFX turretRotationMotor = new TalonFX(TurretConstants.kTurretRotationMotorPort);

public void init() {
    TalonFXConfiguration config = new TalonFXConfiguration();
    var softLimitConfig = new SoftwareLimitSwitchConfigs();
    softLimitConfig.ForwardSoftLimitEnable = true;
    softLimitConfig.ForwardSoftLimitThreshold = Constants.TurretConstants.kSoftlimit;
    softLimitConfig.ReverseSoftLimitEnable = true;
    softLimitConfig.ReverseSoftLimitThreshold = Constants.TurretConstants.kMinLimit;
    config.SoftwareLimitSwitch = softLimitConfig;
    turretRotationMotor.getConfigurator().apply(config);

    turretRotationMotor.setPosition(Constants.TurretConstants.kStartingPos);

    
    turretRotationMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public RotationSubsystem() {
    init();
  }

public void rotateTurret(double speed) {
    double limitedSpeed = MathUtil.clamp(speed, -Constants.TurretConstants.kRotateSpeed, Constants.TurretConstants.kRotateSpeed);
    turretRotationMotor.set(limitedSpeed);
  }
}
