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
        boolean armFMoving = false, armBMoving = false;
        boolean depositMoving = false, collectMoving = false;
        boolean dUpMoving = false, dDownMoving = false, dRightMoving = false, dLeftMoving = false;
        robot.init(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            //POV Mode driving (left stick go forward/back, right stick turn)
            double drive = -gamepad1.left_stick_y;
            double turn  =  gamepad1.right_stick_x*0.8;
            double leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            double rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            robot.leftMotor.setPower(leftPower);
            robot.rightMotor.setPower(rightPower);

            //arm forward
            if (gamepad2.b && !armFMoving) {
                robot.armMotor.setPower(-0.5);
                robot.armMotor2.setPower(-0.5);
                armFMoving = true;
            }
            else if(!gamepad2.b && armFMoving){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armFMoving = false;
            }

            //arm backward
            if (gamepad2.x && !armBMoving) {
                robot.armMotor.setPower(0.5);
                robot.armMotor2.setPower(0.5);
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

            //slow forward
            if (gamepad1.dpad_up && !dUpMoving) {
                robot.leftMotor.setPower(0.2);
                robot.rightMotor.setPower(0.2);
                dUpMoving = true;
            }
            else if(!gamepad1.dpad_up && dUpMoving){
                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
                dUpMoving = false;
            }

            //slow backward
            if (gamepad1.dpad_down && !dDownMoving) {
                robot.leftMotor.setPower(-0.2);
                robot.rightMotor.setPower(-0.2);
                dDownMoving = true;
            }
            else if(!gamepad1.dpad_down && dDownMoving){
                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
                dDownMoving = false;
            }

            //slow right
            if (gamepad1.dpad_right && !dRightMoving) {
                robot.leftMotor.setPower(-0.3);
                robot.rightMotor.setPower(0.3);
                dRightMoving = true;
            }
            else if(!gamepad1.dpad_right && dRightMoving){
                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
                dRightMoving = false;
            }

            //slow left
            if (gamepad1.dpad_left && !dLeftMoving) {
                robot.leftMotor.setPower(0.3);
                robot.rightMotor.setPower(-0.3);
                dLeftMoving = true;
            }
            else if(!gamepad1.dpad_left && dLeftMoving){
                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
                dLeftMoving = false;
            }

            //carousel
            if(gamepad2.dpad_up){
                runCarousel();
            }
            //hold carousel?

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}