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
        double mltprMove=1.0, mltprArm=1.0;
        boolean armFMoving = false, armBMoving = false;
        boolean depositMoving = false, collectMoving = false;
        boolean topperMovingForward = false, topperMovingBack = false;
        boolean dUpMoving = false, dDownMoving = false, dRightMoving = false, dLeftMoving = false;
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            //POV Mode driving (left stick go forward/back, right stick turn)
            if(gamepad1.right_trigger>=0.1) {
                mltprMove = 0.5;
            } else {
                mltprMove = 1.0;
            }
            if(gamepade2.left_trigger>=0.1) {
                mltprArm = 0.5;
            } else {
                mltprArm = 1.0; 
            }
            double drive = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double leftPower = Range.clip(drive + turn, -1.0, 1.0);
            double rightPower = Range.clip(drive - turn, -1.0, 1.0);
            robot.leftMotor.setPower(leftPower*mltprMove);
            robot.rightMotor.setPower(rightPower*mltprMove);
            //topper forward
            if (gamepad1.right_bumper && !topperMovingForward) {
                robot.topMotor.setPower(0.5);
                robot.topMotor.setPower(0.5);
                topperMovingForward = true;
            }
            else if(!gamepad1.right_bumper && topperMovingForward){
                robot.topMotor.setPower(0);
                robot.topMotor.setPower(0);
                topperMovingForward = false;
            }
            //topper back
            if (gamepad1.left_bumper && !topperMovingBack) {
                robot.topMotor.setPower(-0.5);
                robot.topMotor.setPower(-0.5);
                topperMovingBack = true;
            }
            else if(!gamepad1.left_bumper && topperMovingBack){
                robot.topMotor.setPower(0);
                robot.topMotor.setPower(0);
                topperMovingBack = false;
            }
            //arm forward
            if (gamepad2.b && !armFMoving) {
                robot.armMotor.setPower(-0.5*mltprArm);
                robot.armMotor2.setPower(-0.5*mltprArm);
                armFMoving = true;
            }
            else if(!gamepad2.b && armFMoving){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armFMoving = false;
            }

            //arm backward
            if (gamepad2.x && !armBMoving) {
                robot.armMotor.setPower(0.5*mltprArm);
                robot.armMotor2.setPower(0.5*mltprArm);
                armBMoving = true;
            }
            else if(!gamepad2.x && armBMoving){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armBMoving = false;
            }

            //deposit
            if(gamepad2.right_bumper && !depositMoving) {
                robot.intakeMotor.setPower(0.6);
                depositMoving = true;
            }
            else if(!gamepad2.right_bumper && depositMoving){
                robot.intakeMotor.setPower(0);
                depositMoving = false;
            }

            //collector
            if(gamepad2.left_bumper && !collectMoving) {
                robot.intakeMotor.setPower(-1);
                collectMoving = true;
            }
            else if(!gamepad2.left_bumper && collectMoving){
                robot.intakeMotor.setPower(0);
                collectMoving = false;
            }

            //carousel
            if(gamepad2.dpad_up){
                runCarousel();
            }

            //carousel
            if(gamepad2.dpad_down){
                runCarousel2();
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}
