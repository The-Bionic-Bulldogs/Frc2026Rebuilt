package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class Limelight extends SubsystemBase {
  /** Creates a new Limelight. */
  public Limelight() {}
  private final String LimelightName = "limelight-bb";

  public double getTX() {
    return LimelightHelpers.getTX(LimelightName);
  }

  public double getTY() {
    return LimelightHelpers.getTY(LimelightName);
  }

  public double getTA() {
    return LimelightHelpers.getTA(LimelightName);
  }
  public boolean hasTarget() {
    return LimelightHelpers.getTV(LimelightName);
  }
  public double getTagID() {
    return LimelightHelpers.getFiducialID(LimelightName);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

}
