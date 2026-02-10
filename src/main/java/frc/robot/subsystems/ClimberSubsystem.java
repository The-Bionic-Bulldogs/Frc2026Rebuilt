package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ClimberConstants;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ClimberSubsystem extends SubsystemBase {

  private final TalonFX climberMotor = new TalonFX(ClimberConstants.kLeftClimberMotorPort); //id 20
  private final TalonFX climberMotorTwo = new TalonFX(ClimberConstants.kRightClimberMotorPort); //id 21

  private final MotionMagicVoltage motionMagic = new MotionMagicVoltage(0);

   public void init() {
         TalonFXConfiguration config = new TalonFXConfiguration();
    var motionConfig = new MotionMagicConfigs();
    motionConfig.MotionMagicCruiseVelocity = Constants.ClimberConstants.MaxVelocity; //38.5
    motionConfig.MotionMagicAcceleration = Constants.ClimberConstants.MaxAcceleration; //507
    config.MotionMagic = motionConfig;
    climberMotor.getConfigurator().apply(config);
    climberMotorTwo.getConfigurator().apply(config);
    climberMotor.setNeutralMode(NeutralModeValue.Brake);
    climberMotorTwo.setNeutralMode(NeutralModeValue.Brake);

   }
   //for testing speeds
   public void runClimber(double speed) {
    climberMotor.set(speed);
    climberMotorTwo.set(speed);
   }

   public void stopClimber() {
    climberMotor.set(0.0);
    climberMotorTwo.set(0.0);
   }

   public void moveClimberToPosition(double position) {
    climberMotor.setControl(motionMagic.withPosition(position));
    climberMotorTwo.setControl(motionMagic.withPosition(position));
   }
  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    init();
  }

}
