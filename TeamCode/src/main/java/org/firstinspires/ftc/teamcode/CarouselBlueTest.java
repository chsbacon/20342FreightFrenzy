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

@Autonomous(name="CarouselBlueTest", group="Autonomous")
//@Disabled

public class CarouselBlueTest extends AutoBasicOpMode2 {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        initWebcam();
        armMove(70);
        runtime.reset();

        encoderDrive(DRIVE_SPEED, 11.6, 11.6);
        encoderDrive(TURN_SPEED, 7, -7);
        encoderDrive(DRIVE_SPEED, 15,15);
        armSet(IMAGE_LEVEL);
        encoderDrive(TURN_SPEED, 3, -3);
        encoderDrive(DRIVE_SPEED, -28, -28);
        encoderDrive(TURN_SPEED, -2.5, 2.5);
        encoderDrive(DRIVE_SPEED, -4, -4);
        runCarousel();

        requestOpModeStop();
    }
}