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

@Autonomous(name="WarehouseBlue", group="Autonomous")
//@Disabled

public class AutoTestBlue extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();
        initWebcam();

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        armMove(80);
        runtime.reset();

        encoderDrive(DRIVE_SPEED, 11.6,11.6);
        encoderDrive(TURN_SPEED, -5.7,5.7);
        encoderDrive(DRIVE_SPEED, 12,12);
        armSet(IMAGE_LEVEL);
        encoderDrive(0.7, 19.8, -19.8);
        encoderDrive(DRIVE_SPEED, -69, -69);
        encoderDrive(TURN_SPEED, 8, -8);
    }
}