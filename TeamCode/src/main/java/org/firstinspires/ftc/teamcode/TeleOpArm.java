package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file is an example POV mode TeleOp
 * This code is structured as a Linear OpMode
 **/

@TeleOp(name = "TeleOpArm", group = "Examples")
//@Disabled

public class TeleOpArm extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    EncoderHMap robot = new EncoderHMap();
    armFunc MyArm = new armFunc();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            //POV Mode driving (left stick go forward/back, right stick turn)
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x;
            double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            robot.leftMotor.setPower(leftPower);
            robot.rightMotor.setPower(rightPower);

            if (gamepad1.a) {
                armSet(3);
            }
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }

    }
    public void runIntakeMotor(double time, boolean deposit) {
        runtime.reset();
        if(deposit) {
            while(runtime.seconds() < time) {
                robot.intakeMotor.setPower(-1);
            }
        } else {
            while(runtime.seconds() < time) {
                robot.intakeMotor.setPower(1);
            }
        }
    }
    public void armMove(int degrees) {
        double ticks = (degrees/360.0)*288.0*(45.0/125.0);
        robot.armMotor.setTargetPosition(52);
        robot.armMotor.setPower(1);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(robot.armMotor.isBusy()) {
            //Nothing needed here
        }
        robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armMotor.setPower(0);
    }
    public void armSet(int Setting) {
        robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        switch(Setting) {
            case 1:
                armMove(120); //1st level
                runIntakeMotor(2.5, true);
                break;
            case 2:
                armMove(135); //2nd level
                runIntakeMotor(2.5, true);
                break;
            case 3:
                armMove(10); //3rd level
                runIntakeMotor(2.5, true);
                break;
            case 4:
                armMove(-10); //Collecting
                runIntakeMotor(2.5, false);
                break;
            case 5: //Neutral
                break;
        }
        armMove(0); //Resting position
    }
}
