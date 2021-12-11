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
 *   - Drive forward for 2 seconds
 *   - Spin left for 1.3 seconds
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 **/

@Autonomous(name="FullExampleAuto", group="Examples")
@Disabled

public class FullExampleAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    EncoderHMap robot = new EncoderHMap();

    static final double  FORWARD_SPEED = 0.2;
    static final double  TURN_SPEED    = 0.3;

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 2 seconds
        robot.leftMotor.setPower(FORWARD_SPEED);
        robot.rightMotor.setPower(FORWARD_SPEED);
        runtime.reset();
        while(opModeIsActive() && (runtime.seconds() < 2.0)){
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 2:  Spin left for 1.3 seconds
        robot.leftMotor.setPower(TURN_SPEED);
        robot.rightMotor.setPower(-TURN_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 3: Stop motors
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
