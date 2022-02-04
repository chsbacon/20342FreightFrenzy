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

public class WarehouseBlue extends AutoBasicOpMode {
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

        encoderDrive(DRIVE_SPEED, 13,13); //
        encoderDrive(TURN_SPEED, -7.75,7.75);
        encoderDrive(DRIVE_SPEED, 12.4,12.4);
        armSet(IMAGE_LEVEL);
        encoderDrive(0.7, 16.6, -16.6);
        if (IMAGE_LEVEL == 3) armMove(50);
        encoderDrive(.8, 39,39);
        armMove(0);

        requestOpModeStop();
    }
}