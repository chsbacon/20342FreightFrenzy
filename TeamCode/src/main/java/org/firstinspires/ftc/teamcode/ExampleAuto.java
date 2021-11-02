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

@Autonomous(name="ExampleAuto", group="Examples")
//@Disabled

public class ExampleAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    HardwareMap2022 robot = new HardwareMap2022();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        robot.frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
      
        while(opModeisActive()){
        
        }
    }
}
