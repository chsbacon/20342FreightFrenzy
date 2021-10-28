//simplified version of FTC7080 ExampleTeleOp
//imports
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


@TeleOp(name = "ExampleFileOnPhone", group = "Opmode")
//@Disabled



// Extends LinearOpMode means the code will execute chronologically
public class ExampleTeleOp extends LinearOpMode {

    // creates an instance of your hardware map, called "robot" 
    // The hardware map is where all the phone connection and device setup stuff goes
    MyHWMap robot = new MyHWMap();
    private ElapsedTime runtime = new ElapsedTime(); 
    
    //when the init button is pressed, run the stuff in runOpMode
    @Override
    public void runOpMode() {

        robot.init(hardwareMap);  //initializes the hardware map
        double x, y, r;           //example variables
        
        //send data to driver station that OpMode is initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        
        // when the game is live -- when the robot is free to be driven -- when the driver has pressed play
        // This is where like 90% of your code will go
        while (opModeIsActive()) {
         
            // Most robots need the motor on one side to be reversed to drive forward
            // Reverse the motor that runs backwards when connected directly to the battery
            robot.frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
            robot.frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
            
            // Driving with POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive        = -gamepad1.left_stick_y;
            double turn         =  gamepad1.right_stick_x;
            double leftPower    =  Range.clip(drive + turn, -1.0, 1.0) ;
            double rightPower   =  Range.clip(drive - turn, -1.0, 1.0) ;
            
            
            //example if statement: send calculated power to wheels
            if (gamepad1.a) {
                sleep(250);
                robot.frontLeftMotor.setPower(leftPower);
                robot.frontRightMotor.setPower(rightPower);
            }
            
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }

    } //where the runOpMode ends



    // Example Functions : you put your functions outside of the runOpMode
    //kills power ot all wheels
    void stopDriving(){
        robot.frontLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.backRightMotor.setPower(0);
    }


}
