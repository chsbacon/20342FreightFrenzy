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

@Autonomous(name="Leg1AutoNoImage", group="Autonomous")
@Disabled

public class Leg1AutoNoImage extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        armMove(80);
        initWebcam();
        sleep(1000);
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        encoderDrive(DRIVE_SPEED, 11.6,11.6);
        encoderDrive(TURN_SPEED, 13.8, -13.8);
        encoderDrive(DRIVE_SPEED, -22,-22);
        encoderDrive(TURN_SPEED, -8.9, 8.9);
        encoderDrive(0.35, -7.5,-7.5);
        runCarousel();

        encoderDrive(DRIVE_SPEED, 7.8, 7.8);
        encoderDrive(TURN_SPEED, 8.9, -8.9);
        encoderDrive( DRIVE_SPEED, 40, 40);
        encoderDrive( TURN_SPEED, -13.8, 13.8);
        encoderDrive( DRIVE_SPEED, 13, 13);
        armSet(IMAGE_LEVEL);
        /*encoderDrive( TURN_SPEED, 15.2, -15.2);
        encoderDrive(.5, 53, 53);*/
    }
}
