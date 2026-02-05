package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX intakeMotor = new TalonFX(IntakeConstants.kIntakeMotorPort);
    private final TalonFX intakerMotor = new TalonFX(IntakeConstants.kIntakerMotorPort);

    public IntakeSubsystem() {
        intakeMotor.setNeutralMode(NeutralModeValue.Coast);
        intakerMotor.setNeutralMode(NeutralModeValue.Brake);
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

}
