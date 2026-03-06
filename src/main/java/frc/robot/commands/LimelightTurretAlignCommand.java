package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.RotationSubsystem;
import frc.robot.LimelightHelpers;
import frc.robot.Constants;

public class LimelightTurretAlignCommand extends Command {

    private final Limelight limelight;
    private final RotationSubsystem rotationSubsystem;

    private int lockedTagId = -1;
    private int activePipeline = -1;

    private final Timer pipelineTimer = new Timer();
    private static final double PIPELINE_DELAY = 0.3; // 300ms

    public LimelightTurretAlignCommand(Limelight limelight, RotationSubsystem rotationSubsystem) {
        this.limelight = limelight;
        this.rotationSubsystem = rotationSubsystem;
        addRequirements(rotationSubsystem);
    }

    @Override
    public void initialize() {
        lockedTagId = -1;
        activePipeline = -1;
        pipelineTimer.stop();
        pipelineTimer.reset();
    }

    @Override
    public void execute() {

        if (!limelight.hasTarget()) {
            lockedTagId = -1;
            rotationSubsystem.rotateTurret(0.0);
            return;
        }

        int currentSeenTag = (int) limelight.getTagID();

        if (lockedTagId == -1 || lockedTagId != currentSeenTag) {
            lockedTagId = currentSeenTag;
        }

        int desiredPipeline = getPipelineForTag(lockedTagId);

        if (desiredPipeline == -1) {
            rotationSubsystem.rotateTurret(0.0);
            return;
        }

        // If pipeline changed, start delay timer
        if (desiredPipeline != activePipeline) {
            LimelightHelpers.setPipelineIndex("limelight-bb", desiredPipeline);
            activePipeline = desiredPipeline;

            pipelineTimer.reset();
            pipelineTimer.start();

            rotationSubsystem.rotateTurret(0.0);
            return; // skip aiming this cycle
        }

        // Wait for pipeline to stabilize
        if (pipelineTimer.isRunning() && pipelineTimer.get() < PIPELINE_DELAY) {
            rotationSubsystem.rotateTurret(0.0);
            return;
        }

        pipelineTimer.stop();

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