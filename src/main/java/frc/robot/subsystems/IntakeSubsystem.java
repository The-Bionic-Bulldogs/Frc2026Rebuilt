package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor = new TalonFX(IntakeConstants.kIntakeMotorPort); //id 18
    private final TalonFX intakerMotor = new TalonFX(IntakeConstants.kIntakerMotorPort); //id 19

    private final MotionMagicVoltage motionMagic = new MotionMagicVoltage(0);

    public void init()
    {
       var config = new TalonFXConfiguration();

       var softLimitConfig = new SoftwareLimitSwitchConfigs();
    softLimitConfig.ForwardSoftLimitEnable = true;
    softLimitConfig.ForwardSoftLimitThreshold = Constants.IntakeConstants.kSoftlimit;
    softLimitConfig.ReverseSoftLimitEnable = true;
    softLimitConfig.ReverseSoftLimitThreshold = Constants.IntakeConstants.kMinLimit;
     config.SoftwareLimitSwitch = softLimitConfig;

config.MotionMagic.MotionMagicCruiseVelocity = 0.8 * Constants.IntakeConstants.MaxVelocity;
config.MotionMagic.MotionMagicAcceleration = 0.6 * Constants.IntakeConstants.MaxAcceleration;

config.Slot0.kP = 1.1;
config.Slot0.kI = 0.0;
config.Slot0.kD = 0.0;

config.Slot0.kG = 0.35; // tune this
config.Slot0.GravityType = GravityTypeValue.Arm_Cosine;

config.CurrentLimits.SupplyCurrentLimit = 40;
config.CurrentLimits.SupplyCurrentLimitEnable = true;

intakerMotor.getConfigurator().apply(config);

intakerMotor.setNeutralMode(NeutralModeValue.Brake);
intakerMotor.setPosition(0);
    }
     

    public IntakeSubsystem() {
        init();
    }

    public Command runIntake() {
        return runOnce(() -> intakeMotor.set(-IntakeConstants.kIntakeSpeed));
    }

    public Command extendIntaker() {
         return runOnce(() -> intakerMotor.set(-IntakeConstants.kIntakerSpeed));
    }

    public Command retractIntaker() {
        return runOnce(() -> intakerMotor.set(IntakeConstants.kIntakerSpeed));
    }

    public Command stopIntaker() {
        return runOnce(() -> intakerMotor.set(0));
    }

    public Command stopIntake() {
        return runOnce(() -> intakeMotor.set(0));
    }

    public Command moveIntakerToPosition(double position) {
       return runOnce(() -> intakerMotor.setControl(motionMagic.withPosition(position)));
    }
  

}
