// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  public static class LimelightConstants {
    public static final String LimelightName = "limelight-bb";
  }
  public static class TurretConstants {
    public static final int kTurretOuttakeMotorPort = 13;
    public static final int kTurretRotationMotorPort = 14;
    public static final int kFeederFirstMotorPort = 15;
    public static final int kFeederSecondMotorPort = 16;
    public static final int kRollerMotorPort = 17;
    // PID values for turret rotation
    public static final double kP = 0.02;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double kRotateSpeed = 0.3;
    public static final double kOuttakeSpeed = 1.0;
    public static final double kFeederSpeed = 1.0;
    public static final double kRollerSpeed = 1.0;
  }

  public static class IntakeConstants {
    public static final int kIntakeMotorPort = 18;
    public static final int kIntakerMotorPort = 19;
    public static final double kIntakeSpeed = 1.0;
    public static final double kIntakerSpeed = 1.0;
  }
}
