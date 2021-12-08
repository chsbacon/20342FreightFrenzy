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

@Autonomous(name="Leg2Auto2", group="Autonomous")
//@Disabled

public class Leg3Auto extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        encoderDrive(.3, 7, 7);
        encoderDrive(.3, 12, -12);
        encoderDrive( .3, 40, 40);
        encoderDrive( .3, -16.5, 16.5);
        encoderDrive( .3, 14, 14);
        armMove(10);
        runIntakeMotor(2000, true);
        encoderDrive( .3, 16, -16);
        encoderDrive(.5, 75, 75);


    }
}