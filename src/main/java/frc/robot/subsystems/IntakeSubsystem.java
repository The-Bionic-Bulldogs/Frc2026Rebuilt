package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.IntakeConstants;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor = new TalonFX(IntakeConstants.kIntakeMotorPort); //id 18
    private final TalonFX intakerMotor = new TalonFX(IntakeConstants.kIntakerMotorPort); //id 19

    private final MotionMagicVoltage motionMagic = new MotionMagicVoltage(0);

    public void init()
    {
        TalonFXConfiguration config = new TalonFXConfiguration();
    var motionConfig = new MotionMagicConfigs();
    motionConfig.MotionMagicCruiseVelocity = Constants.IntakeConstants.MaxVelocity; //38.5
    motionConfig.MotionMagicAcceleration = Constants.IntakeConstants.MaxAcceleration; //507
    config.MotionMagic = motionConfig;
    intakerMotor.getConfigurator().apply(config);

    intakeMotor.setNeutralMode(NeutralModeValue.Coast);
    intakerMotor.setNeutralMode(NeutralModeValue.Brake);
    }
     

    public IntakeSubsystem() {
        init();
    }

    public Command runIntake() {
        return runOnce(() -> intakeMotor.set(IntakeConstants.kIntakeSpeed));
    }

    public Command extendIntaker() {
         return runOnce(() -> intakerMotor.set(IntakeConstants.kIntakerSpeed));
    }

    public Command retractIntaker() {
        return runOnce(() -> intakerMotor.set(-IntakeConstants.kIntakerSpeed));
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
