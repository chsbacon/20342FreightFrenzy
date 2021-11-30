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

    static final double MOTOR_POWER = 0.8;

    @Override
    public void runOpMode(){}

    public void runCarousel() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 5)) {

            if (gamepad1.a) {
                robot.carouselMotor.setPower(MOTOR_POWER);
                sleep(2500);
            }

            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        robot.carouselMotor.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);

    }

    public void runIntakeMotor(double time, boolean deposit) {
        runtime.reset();
        if(deposit) {
            while(runtime.seconds() < time) {
                robot.intakeMotor.setPower(-1);
            }
        }
        else {
            while(runtime.seconds() < time) {
                robot.intakeMotor.setPower(1);
            }
        }
    }
    public void armMove(int degrees) {
        double ticks = (degrees/360.0)*288;
        robot.armMotor.setTargetPosition((int)ticks);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armMotor.setPower(1);
        while(robot.armMotor.isBusy()) {
            //Nothing needed here
        }
        robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armMotor.setPower(0);
    }
    public void armSet(int Setting) {
        switch(Setting) {
            case 1:
                armMove(120); //1st level
                runIntakeMotor(2.5, true);
                break;
            case 2:
                armMove(135); //2nd level
                runIntakeMotor(2.5, true);
                break;
            case 3:
                armMove(150); //3rd level
                runIntakeMotor(2.5, true);
                break;
            case 4:
                armMove(-10); //Collecting
                runIntakeMotor(2.5, false);
                break;
            case 5: //Neutral
                break;
        }
        armMove(0); //Resting position
    }
}