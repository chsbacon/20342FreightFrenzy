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


@TeleOp(name = "ArmTest", group = "Opmode")
@Disabled


public class ArmTest extends LinearOpMode {

    EncoderHMap robot = new EncoderHMap();
    private ElapsedTime runtime = new ElapsedTime(); 
    int ticks = 0;
  
    @Override
    public void runOpMode() {

        robot.init(hardwareMap); 
        robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        waitForStart();
        runtime.reset();
        
        
        while (opModeIsActive()) {
            
            // Driving with POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive        = -gamepad1.left_stick_y;
            double turn         =  gamepad1.right_stick_x;
            double leftPower    =  Range.clip(drive + turn, -1.0, 1.0) ;
            double rightPower   =  Range.clip(drive - turn, -1.0, 1.0) ;
            
            
            //example if statement: send calculated power to wheels
            if (gamepad1.a) {
                sleep(250);
                ticks += 10;
                armMove(ticks);
            } 
          
            else if(gamepad1.y){
                sleep(250);
                ticks -= 10;
                armMove(ticks);
            }
            robot.leftMotor.setPower(leftPower);
            robot.rightMotor.setPower(rightPower);


        }

    } //where the runOpMode ends

    public void armMove(int myTicks){
        double degrees = (40.0*360.0*myTicks)/(288.0*125.0);
        telemetry.addData("ArmMotor", "ticks: ", myTicks);
        telemetry.addData("ArmMotor", "degrees: ", degrees);
        telemetry.update();

        robot.armMotor.setTargetPosition(myTicks);
        robot.armMotor2.setTargetPosition(myTicks);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armMotor.setPower(0.75);
        robot.armMotor2.setPower(0.75);

        while(robot.armMotor.isBusy()){
        }
        robot.armMotor.setPower(0);
        robot.armMotor2.setPower(0);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
