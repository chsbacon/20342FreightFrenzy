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

@Autonomous(name="armDrive", group="Examples")
@Disabled

public class armDrive extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    EncoderHMap robot = new EncoderHMap();

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

        robot.rightMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        robot.armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        runIntakeMotor(2.5, true);
        runIntakeMotor(2.5, false);
        armSet(1);
        armSet(2);
        armSet(3);
        armSet(4);
    }
    public void runIntakeMotor(double time, boolean deposit) {
        runtime.reset();
        if(deposit) {
            while(opModeIsActive() && runtime.seconds() < time) {
                robot.intakeMotor.setPower(-1);
                telemetry.addData("Status", "Intake depositing");
                telemetry.update();
            }
        } else {
            while(opModeIsActive() && runtime.seconds() < time) {
                robot.intakeMotor.setPower(1);
                telemetry.addData("Status", "Intake collecting");
                telemetry.update();
            }
        }
    }
    public void armMove(int degrees) {
        double ticks = (degrees/360.0)*288;
        robot.armMotor.setTargetPosition((int)ticks);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armMotor.setPower(1);
        while(robot.armMotor.isBusy()) {
            //Nothing needed here
        }
        robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.armMotor.setPower(0);
    }
    public void armSet(int Setting) {
        switch(Setting) {
            case 1:
                armMove(120); //1st level
                telemetry.addData("Status", "Arm to first level");
                telemetry.update();
                runIntakeMotor(2.5, true);
                break;
            case 2:
                armMove(135); //2nd level
                telemetry.addData("Status", "Arm to first level");
                telemetry.update();
                runIntakeMotor(2.5, true);
                break;
            case 3:
                armMove(150); //3rd level
                telemetry.addData("Status", "Arm to first level");
                telemetry.update();
                runIntakeMotor(2.5, true);
                break;
            case 4:
                armMove(-10); //Collecting
                telemetry.addData("Status", "Arm to first level");
                telemetry.update();
                runIntakeMotor(2.5, false);
                break;
            case 5: //Neutral
                break;
        }
        armMove(0); //Resting position
        telemetry.addData("Status", "Arm motor to resting position");
        telemetry.update();
    }
}