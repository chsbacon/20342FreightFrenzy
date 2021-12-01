package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "EncoderTest", group = "Examples")
//@Disabled

public class EncoderTest extends LinearOpMode{
    GyroHMap             robot   = new GyroHMap();
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

        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0",  "Starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED,  20,  20, 30.0);  // S1: Forward 47 Inches with 10 Sec timeout
        telemetry.addData("Drive", "Complete");
        telemetry.update();
        
        sleep(5000);
        rotate(0.3, 90); 
        telemetry.addData("Rotate", "Complete");
        telemetry.update();
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
    */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        if (!opModeIsActive()) return;

        newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
        newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        robot.leftMotor.setTargetPosition(newLeftTarget);
        robot.rightMotor.setTargetPosition(newRightTarget);

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

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

        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
  
    /**
     * Rotate left or right the number of degrees. Does not support turning more than 350 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    public void rotate(double speed, int degrees) {
        double  leftPower, rightPower;
        int     targetAngle;

        // reset gyro to zero.
        robot.gyro.resetZAxisIntegrator();

        // Gyro returns 0->359 when rotating counter clockwise (left) and 359->0 when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = speed;
            rightPower = -speed;
            targetAngle = 360 + degrees;    // degrees is - for right turn.
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -speed;
            rightPower = speed;
            targetAngle = degrees;
        }
        else return;

        // set power to rotate.
        robot.leftMotor.setPower(leftPower);
        robot.rightMotor.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && robot.gyro.getHeading() == 0)
            {
                telemetry.addData("gyro heading", robot.gyro.getHeading());
                telemetry.update();
                idle();
            }

            while (opModeIsActive() && robot.gyro.getHeading() > targetAngle)
            {
                telemetry.addData("gyro heading", robot.gyro.getHeading());
                telemetry.update();
                idle();
            }
        }
        else {
            while (opModeIsActive() && robot.gyro.getHeading() < targetAngle) {
                telemetry.addData("gyro heading", robot.gyro.getHeading());
                telemetry.update();
                idle();
            }
        }
        // turn the motors off.
        robot.rightMotor.setPower(0);
        robot.leftMotor.setPower(0);

        // Reset gyro heading to zero on new direction we are now pointing.
        robot.gyro.resetZAxisIntegrator();
    }
  

}
