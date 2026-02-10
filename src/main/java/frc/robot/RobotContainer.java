// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.Constants;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.*;
import frc.robot.commands.*;

public class RobotContainer {

    // Subsystems
    private Limelight m_limelight = new Limelight();
    private TurretSubsystem m_turretSubsystem = new TurretSubsystem();
    private IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();

    public final CommandSwerveDrivetrain drivetrain =
            TunerConstants.createDrivetrain();

    // Constants
    private final double MaxSpeed =
            TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);

    private final double MaxAngularRate =
            RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    // Swerve requests
    private final SwerveRequest.FieldCentric drive =
            new SwerveRequest.FieldCentric()
                    .withDeadband(MaxSpeed * 0.1)
                    .withRotationalDeadband(MaxAngularRate * 0.1)
                    .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.RobotCentric autoDrive =
            new SwerveRequest.RobotCentric()
                    .withDriveRequestType(DriveRequestType.Velocity);

    private final Telemetry logger = new Telemetry(MaxSpeed);

    // Controllers
    private final CommandXboxController dj = new CommandXboxController(Constants.OperatorConstants.kDriverControllerPort);
    private final CommandXboxController oj = new CommandXboxController(Constants.OperatorConstants.kOperatorControllerPort);

    // Auto chooser
    private SendableChooser<Command> autoChooser = new SendableChooser<>();

    public RobotContainer() {

    m_turretSubsystem.setDefaultCommand(
        new LimelightTurretAlignCommand(m_limelight, m_turretSubsystem)
    );

    

    autoChooser = AutoBuilder.buildAutoChooser("placeHolder");
    SmartDashboard.putData("Auto Mode", autoChooser);

    configureBindings();
}


     

    private void configureBindings() {

        double rotationSensitivity = 0.5;

        drivetrain.setDefaultCommand(
                drivetrain.applyRequest(() ->
                        drive.withVelocityX(
                                        MathUtil.applyDeadband(dj.getLeftY(), 0.1) * MaxSpeed)
                                .withVelocityY(
                                        MathUtil.applyDeadband(dj.getLeftX(), 0.1) * MaxSpeed)
                                .withRotationalRate(
                                        MathUtil.applyDeadband(-dj.getRightX(), 0.1)
                                                * MaxAngularRate * rotationSensitivity)
                )
        );

        // Turret controls
        oj.rightTrigger().whileTrue(m_turretSubsystem.startOuttake()).onFalse(m_turretSubsystem.stopOuttake());
        oj.leftBumper().whileTrue(m_intakeSubsystem.runIntake()).onFalse(m_intakeSubsystem.stopIntake());
        oj.y().onTrue(m_intakeSubsystem.moveIntakerToPosition(Constants.IntakeConstants.ExtendedPosition));
        oj.a().onTrue(m_intakeSubsystem.moveIntakerToPosition(Constants.IntakeConstants.RetractedPosition));

        oj.leftBumper().onTrue(
                new InstantCommand(() ->
                        m_turretSubsystem.setDefaultCommand(
                                new TurretAutoStopCommand(
                                        m_turretSubsystem, m_limelight)))
        );

        // Reset heading
        dj.back().onTrue(
                drivetrain.runOnce(() -> drivetrain.seedFieldCentric())
        );

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
