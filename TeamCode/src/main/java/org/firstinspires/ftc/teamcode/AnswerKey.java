package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file is an example POV mode TeleOp
 * This code is structured as a Linear OpMode
 **/

@TeleOp(name = "AnswerKey", group = "Examples")
//@Disabled

public class AnswerKey extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    MecanumHMap robot = new MecanumHMap(hardwareMap);

    @Override
    public void runOpMode(){
        robot.init();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            //POV Mode driving (left stick translate, right stick turn)
            double m = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
            double tAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double turn  =  gamepad1.right_stick_x * 0.7;

            setMecanumDrive(tAngle, m, turn);
        }

    }

    public void setMecanumDrive(double tAngle, double m, double turn)
    {
        // calculate motor power
        double LFpwr = m * Math.cos(tAngle) + turn;
        double LBpwr = m * Math.sin(tAngle) + turn;
        double RFpwr = m * Math.sin(tAngle) - turn;
        double RBpwr = m * Math.cos(tAngle) - turn;

        double turnScale = Math.max(Math.max(Math.abs(LFpwr), Math.abs(LBpwr)),
                                    Math.max(Math.abs(RFpwr), Math.abs(RBpwr)));
        if(Math.abs(turnScale) < 1.0) turnScale = 1.0;

        // set the motors
        robot.LFMotor.setPower(LFpwr/turnScale);
        robot.LBMotor.setPower(LBpwr/turnScale);
        robot.RFMotor.setPower(RFpwr/turnScale);
        robot.RBMotor.setPower(RBpwr/turnScale);
    }
}
