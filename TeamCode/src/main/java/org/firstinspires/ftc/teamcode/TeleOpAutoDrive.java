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

@TeleOp(name = "TeleOpAutoDrive", group = "Examples")
//@Disabled

public class TeleOpAutoDrive extends TeleBasicOpMode{
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        boolean armFMoving = false, armBMoving = false;
        boolean depositMoving = false, collectMoving = false;
        boolean dUpMoving = false, dDownMoving = false, dRightMoving = false, dLeftMoving = false;
        robot.init(hardwareMap);
        int ticks = 0;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()){
            //POV Mode driving (left stick go forward/back, right stick turn)

            //slow forward
            if (gamepad1.dpad_up && !dUpMoving) {
                robot.leftMotor.setPower(0.3);
                robot.rightMotor.setPower(0.3);
                dUpMoving = true;
            }
            else if(!gamepad1.dpad_up && dUpMoving){
                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
                dUpMoving = false;
            }

            //slow backward
            if (gamepad1.dpad_down && !dDownMoving) {
                robot.leftMotor.setPower(-0.3);
                robot.rightMotor.setPower(-0.3);
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

            telemetry.addData("Status", "Left Ticks: " + robot.leftMotor.getCurrentPosition());
            telemetry.addData("Status", "Right Ticks: " + robot.rightMotor.getCurrentPosition());
            telemetry.update();
        }

    }
}