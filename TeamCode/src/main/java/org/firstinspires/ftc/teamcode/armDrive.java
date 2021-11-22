package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file is a basic auto op template
 * The code is structured as a LinearOpMode
 **/

@Autonomous(name="armDrive", group="Examples")
@Disabled

public class armDrive extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    EncoderHMap robot = new EncoderHMap();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        robot.rightMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){

        }
    }
    public void armMove(int degrees){
        double ticks = (degrees/360.0)*537.6;
        robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int targetPos = robot.armMotor.getTargetPosition()+(int)ticks;
        robot.armMotor.setTargetPosition(targetPos);
    }
}