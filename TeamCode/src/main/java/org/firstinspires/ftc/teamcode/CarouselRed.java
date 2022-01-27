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

@Autonomous(name="CarouselRed", group="Autonomous")
//@Disabled

public class CarouselRed extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        initWebcam();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        armMove(80);
        runtime.reset();

        encoderDrive(.4,-2,-2);
        runCarousel();
        encoderDrive(.4,-7,7);
        encoderDrive(.4,-17,-17);
        encoderDrive(.4,-3,3);
        encoderDrive(.4,-9,-9);
    }
}
