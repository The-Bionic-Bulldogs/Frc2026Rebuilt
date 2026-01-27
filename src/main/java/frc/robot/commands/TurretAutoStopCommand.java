package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

public class TurretAutoStopCommand extends Command {
    private final TurretSubsystem turretSubsystem;
    private final Limelight limelight;

    public TurretAutoStopCommand(TurretSubsystem turretSubsystem, Limelight limelight) {
        this.turretSubsystem = turretSubsystem;
        this.limelight = limelight;
        addRequirements(turretSubsystem);
    }

    @Override
    public void initialize() {
        // Stop the turret when the command is initialized
        turretSubsystem.rotateTurret(0.0);
        LimelightHelpers.setPipelineIndex("limelight-bb", 5);
    }

    @Override
    public void execute() {
        // Stop the turret if there's a target
        
        if (!limelight.hasTarget()) {
            turretSubsystem.rotateTurret(0.1);
        } else {
               double tx = limelight.getTX();
                 double rotateSpeed = -tx * Constants.TurretConstants.kP;
                 turretSubsystem.rotateTurret(rotateSpeed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        // Ensure the turret stops when the command ends
        turretSubsystem.rotateTurret(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}