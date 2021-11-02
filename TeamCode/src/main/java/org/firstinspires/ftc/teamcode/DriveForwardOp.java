package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file illustrates the concept of driving a path based on time.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *   - Stop and close the claw.
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 */

@Autonomous(name="DriveForward", group="Examples")
//@Disabled

public class DriveForwardOp extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap2022 robot = new HardwareMap2022();

    static final double  FORWARD_SPEED = 0.6;
    static final double  TURN_SPEED    = 0.5;

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        double frontLeft, frontRight, backLeft, backRight;
        robot.frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 3 seconds
        robot.frontLeftMotor.setPower(FORWARD_SPEED);
        robot.backLeftMotor.setPower(FORWARD_SPEED);
        robot.frontRightMotor.setPower(FORWARD_SPEED);
        robot.backRightMotor.setPower(FORWARD_SPEED);
        runtime.reset();
        while(opModeIsActive() && (runtime.seconds() < 3.0)){
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 2:  Spin right for 1.3 seconds
        robot.frontLeftMotor.setPower(TURN_SPEED);
        robot.backLeftMotor.setPower(TURN_SPEED);
        robot.frontRightMotor.setPower(-TURN_SPEED);
        robot.backRightMotor.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3: Stop motors
        robot.frontLeftMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backRightMotor.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
