package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robocol.Heartbeat;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.concurrent.TimeUnit;
import java.util.Locale;

public class BasicOpMode extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    EncoderHMap robot = new EncoderHMap();

    static final double     COUNTS_PER_MOTOR_REV = 537.6;
    static final double     DRIVE_GEAR_REDUCTION = 60.0/72.0;   // output (wheel) speed / input (motor) speed
    static final double     WHEEL_DIAMETER_INCHES = 5.0;
    static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.3;
    static final double CORRECTION = 1;

    @Override
    public void runOpMode(){}

    public void runCarousel() {
        robot.carouselMotor.setPower(0.2);
        sleep(2000);
        robot.carouselMotor.setPower(0);

        telemetry.addData("Carousel", "Complete");
    }

    public void runIntakeMotor(double time, boolean deposit) {
        int timeMS = (int) (time*1000);
        if(deposit) robot.intakeMotor.setPower(-0.6);
        else robot.intakeMotor.setPower(1); //collect

        sleep(timeMS);
        robot.intakeMotor.setPower(0);
    }

    public void armMove(int degrees) {
        double ticks = ((degrees-10)/360.0)*288.0*(45.0/125.0);
        robot.armMotor.setTargetPosition((int)ticks);
        robot.armMotor2.setTargetPosition((int)ticks);
        robot.armMotor.setPower(1);
        robot.armMotor2.setPower(1);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(robot.armMotor.isBusy()) {
        }
        robot.armMotor.setPower(0);
        robot.armMotor2.setPower(0);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void armSet(int Setting) {
        robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        switch(Setting) {
            case 1:
                armMove(-90); //1st level
                //runIntakeMotor(2000, true);
                break;
            case 2:
                armMove(90); //2nd level
                //runIntakeMotor(2000, true);
                break;
            case 3:
                armMove(10); //3rd level
                //runIntakeMotor(2000, true);
                break;
            case 4:
                armMove(-10); //Collecting
                //runIntakeMotor(0500, false);
                break;
            case 5: //Neutral
                break;
        }
        //armMove(0); //Resting position
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        leftInches -= 2.4;
        rightInches -= 2.4;

        // Ensure that the opmode is still active
        if (!opModeIsActive()) return;

        // Determine new target position, and pass to motor controller
        int newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH * CORRECTION) ;
        int newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH * CORRECTION);
        robot.leftMotor.setTargetPosition(newLeftTarget);
        robot.rightMotor.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        runtime.reset();
        robot.leftMotor.setPower(Math.abs(speed));
        robot.rightMotor.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {
            telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d",
                    robot.leftMotor.getCurrentPosition(),
                    robot.rightMotor.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(250);   // optional pause after each move
    }
}
