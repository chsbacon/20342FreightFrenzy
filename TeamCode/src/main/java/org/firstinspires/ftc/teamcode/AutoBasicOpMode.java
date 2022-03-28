package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.acmerobotics.dashboard.FtcDashboard;
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
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Locale;

public class AutoBasicOpMode extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    MecanumHMap robot = new MecanumHMap();

    @Override
    public void runOpMode(){}

    public void waitForB_Button(){
        while(!gamepad1.b && opModeIsActive()) {
            telemetry.addData("Waiting for B Button ", runtime.seconds());
            telemetry.update();
        }
    }

    public void wait25(double wait){
        double start_time = runtime.seconds();
        while(runtime.seconds() < start_time+wait && opModeIsActive()){
            telemetry.addData("Waiting in between ", runtime.seconds());
            telemetry.update();
        }
    }

    public void runForward() {
        runtime.reset();
        while(runtime.seconds()<=0.3 && opModeIsActive()) {
            robot.RHMotor.setPower(0.5);
            robot.LHMotor.setPower(0.5);
            telemetry.addData("Forward ", runtime.seconds());
            telemetry.update();
        }
        robot.RHMotor.setPower(0);
        robot.LHMotor.setPower(0);
        wait25(1.5);
    }
    public void runBack() {
        runtime.reset();
        while(runtime.seconds()<=0.3 && opModeIsActive()) {
            robot.RHMotor.setPower(-0.5);
            robot.LHMotor.setPower(-0.5);
            telemetry.addData("Back ", runtime.seconds());
            telemetry.update();
        }
        robot.RHMotor.setPower(0);
        robot.LHMotor.setPower(0);
        wait25(1.5);
    }
    public void runLeft() {
        runtime.reset();
        while(runtime.seconds()<=0.36 && opModeIsActive()) {
            robot.FVMotor.setPower(0.5);
            robot.BVMotor.setPower(0.5);
            telemetry.addData("Left ", runtime.seconds());
            telemetry.update();
        }
        robot.FVMotor.setPower(0);
        robot.BVMotor.setPower(0);
        wait25(1.5);
    }
    public void runRight() {
        runtime.reset();
        while(runtime.seconds()<=0.36 && opModeIsActive()) {
            robot.FVMotor.setPower(-0.5);
            robot.BVMotor.setPower(-0.5);
            telemetry.addData("Right ", runtime.seconds());
            telemetry.update();
        }
        robot.FVMotor.setPower(0);
        robot.BVMotor.setPower(0);
        wait25(1.5);
    }
    public void lightOn() {
        runtime.reset();
        while(runtime.seconds()<=1 && opModeIsActive()) {
            robot.pattern = RevBlinkinLedDriver.BlinkinPattern.BLUE;
            robot.blinkinLedDriver.setPattern(robot.pattern);
        }
        robot.pattern = RevBlinkinLedDriver.BlinkinPattern.GRAY;
        robot.blinkinLedDriver.setPattern(robot.pattern);
        wait25(1.5);
    }
    public void setLight() {
        robot.pattern = RevBlinkinLedDriver.BlinkinPattern.RAINBOW_PARTY_PALETTE;
        robot.blinkinLedDriver.setPattern(robot.pattern);
    }

}
