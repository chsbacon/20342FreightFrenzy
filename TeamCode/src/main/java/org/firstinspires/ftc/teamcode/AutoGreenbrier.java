package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file is a basic auto op template
 * The code is structured as a LinearOpMode
 **/

@Autonomous(name="AutoGreenbrier", group="Autonomous")
//@Disabled

public class AutoGreenbrier extends AutoBasicOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        setLight();
        waitForStart();
        runForward(0.75);
        lightOn();
        runBack(0.75);
        lightOn();
        runRight(0.9);
        lightOn();
        runLeft(0.9);
        lightOn();

    }
}