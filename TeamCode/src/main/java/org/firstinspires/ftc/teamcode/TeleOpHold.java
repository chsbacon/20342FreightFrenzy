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
        double mltpr= 1.0, mltprArm = 1.0, topPwr = 0.0;
        boolean armFMoving = false, armBMoving = false;
        boolean depositMoving = false, collectMoving = false;
        boolean topperMovingForward = false, topperMovingBack = false;
        boolean dUpMoving = false, dDownMoving = false, dRightMoving = false, dLeftMoving = false;
        boolean tooFar=false, tooLow=false;
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            //POV Mode driving (left stick go forward/back, right stick turn)
            if(gamepad1.right_trigger>=0.1) mltpr = 0.25;
            else mltpr = 1.0;

            if(gamepad2.left_trigger>=0.1) mltprArm = 0.35;
            else mltprArm = 1.0;

            tooFar = robot.topMotor.getCurrentPosition()>1670;
            tooLow = robot.topMotor.getCurrentPosition()<-32;
            if(tooFar || tooLow){
                if(topPwr != 0.0) {
                    topPwr = 0.0;
                    robot.topMotor.setPower(0);
                    topperMovingForward = false;
                    topperMovingBack = false;
                }
            }


            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double leftPower = Range.clip(drive + turn, -1.0, 1.0);
            double rightPower = Range.clip(drive - turn, -1.0, 1.0);
            robot.leftMotor.setPower(leftPower*mltpr);
            robot.rightMotor.setPower(rightPower*mltpr);
            //topper forward
            if (gamepad1.right_bumper && !topperMovingForward && !tooFar) {
                robot.topMotor.setPower(0.5);
                topPwr = 0.5;
                topperMovingForward = true;
            }
            else if(!gamepad1.right_bumper && topperMovingForward){
                robot.topMotor.setPower(0);
                topPwr = 0.0;
                topperMovingForward = false;
            }
            //topper back
            if (gamepad1.left_bumper && !topperMovingBack && !tooLow) {
                robot.topMotor.setPower(-0.5);
                topPwr = -0.5;
                topperMovingBack = true;
            }
            else if(!gamepad1.left_bumper && topperMovingBack){
                robot.topMotor.setPower(0);
                topPwr = 0.0;
                topperMovingBack = false;
            }
            //arm forward
            if (gamepad2.b && !armFMoving) {
                if(mltprArm != 1.0){
                    robot.armMotor.setPower(-0.1);
                    robot.armMotor2.setPower(-0.1);
                }
                else {
                    robot.armMotor.setPower(-0.6);
                    robot.armMotor2.setPower(-0.6);
                }
                armFMoving = true;
            }
            else if(!gamepad2.b && armFMoving){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armFMoving = false;
            }

            double mltprSpecial;
            //arm backward
            if (gamepad2.x && !armBMoving) {

                robot.armMotor.setPower(0.7*mltprArm);
                robot.armMotor2.setPower(0.7*mltprArm);
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

            if(gamepad2.dpad_left && !dLeftMoving) {
                robot.carouselMotor.setPower(0.2);
                dLeftMoving = true;
            }
            else if(!gamepad2.dpad_left && dLeftMoving){
                robot.carouselMotor.setPower(0);
                dLeftMoving = false;
            }

            if(gamepad2.dpad_right && !dRightMoving) {
                robot.carouselMotor.setPower(-0.2);
                dRightMoving = true;
            }
            else if(!gamepad2.dpad_right && dRightMoving){
                robot.carouselMotor.setPower(0);
                dRightMoving = false;
            }


            //carousel
            if(gamepad2.dpad_up && !dLeftMoving && !dRightMoving){
                runCarousel();
            }

            //carousel
            if(gamepad2.dpad_down){
                runCarousel2();
            }

            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Ticks", ": " + robot.topMotor.getCurrentPosition());
            telemetry.update();
        }

    }
}
