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

    @Override
    public void runOpMode(){}

    public void runCarousel() {
        robot.carouselMotor.setPower(0.2);
        sleep(2000);
        robot.carouselMotor.setPower(0);

        telemetry.addData("Carousel", "Complete");
    }

     public void runIntakeMotor(double time, boolean deposit) {
        if(deposit) {
            robot.intakeMotor.setPower(-1);
            sleep(time);
        } else {
            robot.intakeMotor.setPower(1);
            sleep(time);
        }
    }
    
    public void armMove(int degrees) {
        double ticks = (degrees/360.0)*288.0*(45.0/125.0);
        robot.armMotor.setTargetPosition(ticks);
        robot.armMotor.setPower(1);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(robot.armMotor.isBusy()) {
        }
        robot.armMotor.setPower(0);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    
    public void armSet(int Setting) {
        robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        switch(Setting) {
            case 1:
                armMove(120); //1st level
                //runIntakeMotor(2.5, true);
                break;
            case 2:
                armMove(135); //2nd level
                //runIntakeMotor(2.5, true);
                break;
            case 3:
                armMove(10); //3rd level
                //runIntakeMotor(2.5, true);
                break;
            case 4:
                armMove(-10); //Collecting
                //runIntakeMotor(2.5, false);
                break;
            case 5: //Neutral
                break;
        }
        //armMove(0); //Resting position
    }
}
