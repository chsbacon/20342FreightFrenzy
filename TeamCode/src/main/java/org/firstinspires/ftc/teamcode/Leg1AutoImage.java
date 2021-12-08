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

@Autonomous(name="Leg1AutoImage", group="Autonomous")
//@Disabled

public class Leg1AutoImage extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        tensorInit();

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        runtime.reset();

        armMove(80);
        encoderDrive(.3, 14,14);
        if(isDuck()) { telemetry.addData("LEVEL", "LOWEST"); telemetry.update(); }
        encoderDrive(.3, -4.5,4.5);
        if(isDuck()) { telemetry.addData("LEVEL", "MIDDLE"); telemetry.update(); }
        sleep(1000);
        encoderDrive(.3, -3,3);
        if(isDuck()) { telemetry.addData("LEVEL", "HIGHEST"); telemetry.update(); }
        sleep(1000);
        encoderDrive(.3, 20.5,-20.5);
        encoderDrive(.3, -16,-16);
        encoderDrive(.3, -8,8);
        encoderDrive(.3, -6.8,-6.8);


        runCarousel();
    }
}
