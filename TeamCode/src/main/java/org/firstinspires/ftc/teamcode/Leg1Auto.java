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

@Autonomous(name="Leg1Auto", group="Autonomous")
//@Disabled

public class Leg1Auto extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        armMove(90);
        encoderDrive(.3, 14,14, 19);
        encoderDrive(.3, 16.7,-16.7,19);
        encoderDrive(.3, -14,-14, 19);
        encoderDrive(.3, -12,12, 19);
        encoderDrive(.3, -7.6,-7.6, 19);

        runCarousel();
    }
}
