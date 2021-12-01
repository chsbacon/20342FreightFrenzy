package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "EncoderTest", group = "Examples")
//@Disabled

public class EncoderTest extends LinearOpMode{
    EncoderHMap             robot   = new EncoderHMap();
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV  = 537.6;
    static final double     DRIVE_GEAR_REDUCTION  = 60.0/75.0;   // output (wheel) speed / input (motor) speed
    static final double     WHEEL_DIAMETER_INCHES = 5.0;
    static final double     WHEEL_CIRCUMFERENCE   = WHEEL_DIAMETER_INCHES * 3.1415; 
    static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                              WHEEL_CIRCUMFERENCE;
    static final double DRIVE_SPEED = 0.3;
    static final double TURN_SPEED = 0.2;

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  20,  20, 30.0);  // S1: Forward 47 Inches with 10 Sec timeout
        //encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        //encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
    */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (!opModeIsActive()) return;

        // Determine new target position, and pass to motor controller
        newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
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
  
  /**
     * Rotate left or right the number of degrees. Does not support turning more than 350 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;
        int     targetAngle;

        // reset gyro to zero.
        gyro.resetZAxisIntegrator();

        // Gyro returns 0->359 when rotating counter clockwise (left) and 359->0 when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
            targetAngle = 360 + degrees;    // degrees is - for right turn.
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
            targetAngle = degrees;
        }
        else return;

        // set power to rotate.
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && gyro.getHeading() == 0) 
            {
                telemetry.addData("gyro heading", gyro.getHeading());
                telemetry.update();
                idle();
            }

            while (opModeIsActive() && gyro.getHeading() > targetAngle) 
            {
                telemetry.addData("gyro heading", gyro.getHeading());
                telemetry.update();
                idle();
            }
        }
        else
            while (opModeIsActive() && gyro.getHeading() < targetAngle) 
            {
                telemetry.addData("gyro heading", gyro.getHeading());
                telemetry.update();
                idle();
            }

        // turn the motors off.
        rightMotor.setPower(0);
        leftMotor.setPower(0);

        // Reset gyro heading to zero on new direction we are now pointing.
        gyro.resetZAxisIntegrator();
    }
  

}
