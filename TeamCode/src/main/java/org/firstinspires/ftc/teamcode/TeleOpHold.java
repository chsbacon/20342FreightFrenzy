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
 * This code is structured as a Basic OpMode
 **/

@TeleOp(name = "TeleOpHold", group = "Examples")
//@Disabled

public class TeleOpHold extends TeleBasicOpMode{
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        double drive, turn, leftPower, rightPower;
        boolean armFChanged = false, depositChanged = false, collectChanged = false, armBChanged = false;

        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            //POV Mode driving (left stick go forward/back, right stick turn)
            driveJoysticks();

            //arm forward
            if (gamepad1.b && !armFChanged) {
                robot.armMotor.setPower(-1);
                robot.armMotor2.setPower(-1);
                armFChanged = true;
            }
            else if(!gamepad1.b){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armFChanged = false;
            }

            //arm backward
            if (gamepad1.x && !armBChanged) {
                robot.armMotor.setPower(1);
                robot.armMotor2.setPower(1);
                armBChanged = true;
            }
            else if(!gamepad1.x){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armBChanged = false;
            }

            //collector
            if(gamepad1.right_bumper && !collectChanged) {
                robot.intakeMotor.setPower(1);
                collectChanged = true;
            }
            else if(!gamepad1.right_bumper){
                robot.intakeMotor.setPower(0);
                collectChanged = false;
            }

            //deposit
            if(gamepad1.left_bumper && !depositChanged) {
                robot.intakeMotor.setPower(1);
                collectChanged = true;
            }
            else if(!gamepad1.right_bumper){
                robot.intakeMotor.setPower(0);
                collectChanged = false;
            }

            //carousel
            if(gamepad1.dpad_up){
                runCarousel();
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}