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
        waitForB_Button();
        runForward();
        runForward();
        waitForB_Button();
        runRight();
        runRight();
        waitForB_Button();
        runBack();
        waitForB_Button();
        runRight();
        waitForB_Button();
        runForward();
        runForward();
        waitForB_Button();
        runLeft();
        waitForB_Button();
        lightOn();
        waitForB_Button();
        runForward();
        runForward();
        waitForB_Button();
        runLeft();
        runLeft();
        waitForB_Button();
        runBack();
        waitForB_Button();
        runLeft();
        waitForB_Button();
        runForward();
        runForward();
        waitForB_Button();
        runRight();
        waitForB_Button();
        runForward();
        runForward();
    }
}