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

            robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.leftMotor.setPower(leftPower);
            robot.rightMotor.setPower(rightPower);

            //arm forward
            if (gamepad1.b && !armFMoving) {
                robot.armMotor.setPower(-0.5);
                robot.armMotor2.setPower(-0.5);
                armFMoving = true;
            }
            else if(!gamepad1.b && armFMoving){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armFMoving = false;
            }

            //arm backward
            if (gamepad1.x && !armBMoving) {
                robot.armMotor.setPower(0.5);
                robot.armMotor2.setPower(0.5);
                armBMoving = true;
            }
            else if(!gamepad1.x && armBMoving){
                robot.armMotor.setPower(0);
                robot.armMotor2.setPower(0);
                armBMoving = false;
            }

            //collector
            if(gamepad1.right_bumper && !collectMoving) {
                robot.intakeMotor.setPower(0.6);
                collectMoving = true;
            }
            else if(!gamepad1.right_bumper && collectMoving){
                robot.intakeMotor.setPower(0);
                collectMoving = false;
            }

            //deposit
            if(gamepad1.left_bumper && !depositMoving) {
                robot.intakeMotor.setPower(-1);
                depositMoving = true;
            }
            else if(!gamepad1.left_bumper && depositMoving){
                robot.intakeMotor.setPower(0);
                depositMoving = false;
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