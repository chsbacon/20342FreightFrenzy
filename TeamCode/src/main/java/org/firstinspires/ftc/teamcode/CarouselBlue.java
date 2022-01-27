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

@Autonomous(name="CarouselBlue", group="Autonomous")
//@Disabled

public class CarouselBlue extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        initWebcam();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        armMove(70);
        runtime.reset();

        encoderDrive(.4, -2,2);
        runCarousel();
        encoderDrive(.4,27,27);
        encoderDrive(.4, -16,16);
        encoderDrive(.4,22,22);
    }
}