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

@Autonomous(name="Leg1AutoTurnImage", group="Autonomous")
//@Disabled

public class Leg1AutoTurnImage extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        armMove(80);
        encoderDrive(.3, 14,14);

        encoderDrive(.3, -4.5,4.5);
        sleep(2000);
        encoderDrive(.3, -3,3);
        sleep(2000);
        encoderDrive(.3, 20.5,-20.5);
        encoderDrive(.3, -16.3,-16.3);
        encoderDrive(.3, -9,9);
        encoderDrive(.3, -6.5,-6.5);


        runCarousel();
    }
}
