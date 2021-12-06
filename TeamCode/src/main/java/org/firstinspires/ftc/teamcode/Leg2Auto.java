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

@Autonomous(name="Leg2Auto", group="Autonomous")
//@Disabled

public class Leg2Auto extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        encoderDrive(.3, 7, 7, 19);
        encoderDrive(.3, 12, -12, 19);
        encoderDrive( .3, 40, 40, 19);
        encoderDrive( .3, -16.7, 16.7, 19);
        armMove(120);
        runIntakeMotor(2000, true);
        encoderDrive( .3, 16.7, -16.7, 19);
        encoderDrive(.5, 65, 65, 30);


    }
}