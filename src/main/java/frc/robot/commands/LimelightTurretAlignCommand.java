package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.RotationSubsystem;
import frc.robot.LimelightHelpers;
import frc.robot.Constants;

public class LimelightTurretAlignCommand extends Command {

    private final Limelight limelight;
    private final RotationSubsystem rotationSubsystem;

    private int lockedTagId = -1;
    private int activePipeline = -1;

    public LimelightTurretAlignCommand(Limelight limelight, RotationSubsystem rotationSubsystem) {
        this.limelight = limelight;
        this.rotationSubsystem = rotationSubsystem;
        addRequirements(rotationSubsystem);
    }

    @Override
    public void initialize() {
        lockedTagId = -1;
        activePipeline = -1;
    }

    @Override
    public void execute() {

        if (!limelight.hasTarget()) {
            lockedTagId = -1; // unlock
            rotationSubsystem.rotateTurret(0.0);
            return;
        }

        int currentSeenTag = (int) limelight.getTagID();

        // If no tag locked or locked tag is different than current visible tag,
        // relock immediately
        if (lockedTagId == -1 || lockedTagId != currentSeenTag) {
            lockedTagId = currentSeenTag;
        }

        int desiredPipeline = getPipelineForTag(lockedTagId);

        if (desiredPipeline == -1) {
            rotationSubsystem.rotateTurret(0.0);
            return;
        }

        if (desiredPipeline != activePipeline) {
            LimelightHelpers.setPipelineIndex("limelight-bb", desiredPipeline);
            activePipeline = desiredPipeline;
        }

        double tx = limelight.getTX();
        double rotateSpeed = tx * Constants.TurretConstants.kP;

        rotationSubsystem.rotateTurret(rotateSpeed);
    }

    private int getPipelineForTag(int tagId) {
        if (tagId == 10 || tagId == 26) return 0;
        if (tagId == 8  || tagId == 24) return 1;
        if (tagId == 11 || tagId == 27) return 2;
        return -1;
    }

    @Override
    public void end(boolean interrupted) {
        rotationSubsystem.rotateTurret(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}