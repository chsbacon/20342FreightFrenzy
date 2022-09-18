package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
 * Robot goes forward for 0.3 seconds, stop for 0.25, and goes forward again
 */
@Autonomous(name="AutoExample", group="Autonomous")

public class AutoExample extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    MecanumHMap robot = new MecanumHMap();

    @Override
    public void runOpMode(){ //main method
        robot.init(hardwareMap);
        waitForStart();

        runForward();
        waits();
        runForward();
    }

    public void waits(){ //program stalls for 0.25 seconds
        double start_time = runtime.seconds();
        while(runtime.seconds() < start_time+0.25 && opModeIsActive()){
            telemetry.addData("Waiting in between ", runtime.seconds());
            telemetry.update();
        }
    }

    public void runForward() { //goes forward 0.3 seconds
        runtime.reset();
        while(runtime.seconds()<=0.3 && opModeIsActive()) {
            robot.RHMotor.setPower(0.5);
            robot.LHMotor.setPower(0.5);
            telemetry.addData("Forward ", runtime.seconds());
            telemetry.update();
        }
        robot.RHMotor.setPower(0);
        robot.LHMotor.setPower(0);
        wait25(1.5);
    }
}


