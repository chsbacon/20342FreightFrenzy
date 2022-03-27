package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
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

@TeleOp(name = "LedTests", group = "Examples")
//@Disabled

public class LedTests extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    MecanumHMap robot = new MecanumHMap();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            if(gamepad1.a) {
                robot.pattern = RevBlinkinLedDriver.BlinkinPattern.HEARTBEAT_RED;
                robot.blinkinLedDriver.setPattern(robot.pattern);
            }
        }

    }
}
