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


@TeleOp(name = "TeleOpArmTest", group = "Opmode")
//@Disabled



// Extends LinearOpMode means the code will execute chronologically
public class TeleOpArmTest extends AutoBasicOpMode {

    // creates an instance of your hardware map, called "robot"
    // The hardware map is where all the phone connection and device setup stuff goes
    private ElapsedTime runtime = new ElapsedTime();

    //when the init button is pressed, run the stuff in runOpMode
    @Override
    public void runOpMode() {

        robot.init(hardwareMap);  //initializes the hardware map
        double x, y, r;           //example variables

        //send data to driver station that OpMode is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // when the game is live -- when the robot is free to be driven -- when the driver has pressed play
        // This is where like 90% of your code will go
        while (opModeIsActive()) {

            // Driving with POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive        = -gamepad1.left_stick_y;
            double turn         =  gamepad1.right_stick_x;
            double leftPower    =  Range.clip(drive + turn, -1.0, 1.0) ;
            double rightPower   =  Range.clip(drive - turn, -1.0, 1.0) ;


            //example if statement: send calculated power to wheels
            if (gamepad1.x) {
                armSet(1);
            } else  if (gamepad1.y) {
                armSet(2);
            } else  if (gamepad1.b) {
                armSet(3);
            } else  if (gamepad1.a) {
                armSet(4);
            } else if (gamepad1.dpad_up) {
                armSet(5);
            }
            //you should have a bunch of if/else if statements for each button on controller

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }

    } //where the runOpMode ends



    // Example Functions : you put your functions outside of the runOpMode
    //kills power of all wheels
    void stopDriving(){
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
    }


}