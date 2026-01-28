package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.TurretSubsystem;
import frc.robot.LimelightHelpers;
import frc.robot.Constants;

public class LimelightTurretAlignCommand extends Command {
    private final Limelight limelight;
    private final TurretSubsystem turretSubsystem;

    

    
    public LimelightTurretAlignCommand(Limelight limelight, TurretSubsystem turretSubsystem) {
        this.limelight = limelight;
        this.turretSubsystem = turretSubsystem;
        addRequirements(turretSubsystem);
    }
    
    @Override
    public void initialize() {
    }
    
    @Override
    public void execute() {
        if (limelight.hasTarget()) {

            if (limelight.getTagID() == 10 || limelight.getTagID() == 26) {

                LimelightHelpers.setPipelineIndex("limelight-bb", 0);
                 double tx = limelight.getTX();
                 double rotateSpeed = -tx * Constants.TurretConstants.kP;
                 turretSubsystem.rotateTurret(rotateSpeed);

            } else if (limelight.getTagID() == 8 || limelight.getTagID() == 24) {

                LimelightHelpers.setPipelineIndex("limelight-bb", 1);
                 double tx = limelight.getTX();
                 double rotateSpeed = -tx * Constants.TurretConstants.kP;
                 turretSubsystem.rotateTurret(rotateSpeed);

            } else if(limelight.getTagID() == 11 || limelight.getTagID() == 27) {

                LimelightHelpers.setPipelineIndex("limelight-bb", 2);
                 double tx = limelight.getTX();
                 double rotateSpeed = -tx * Constants.TurretConstants.kP;
                 turretSubsystem.rotateTurret(rotateSpeed);
                 
            } else {
                turretSubsystem.rotateTurret(0.0);
            }
        } else {
        turretSubsystem.rotateTurret(0.0);
        }
    }
    
    @Override
    public void end(boolean interrupted) {
        turretSubsystem.rotateTurret(0.0);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }
}
