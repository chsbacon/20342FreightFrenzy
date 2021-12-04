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

@TeleOp(name = "TeleOpPress", group = "Examples")
//@Disabled

public class TeleOpPress extends TeleBasicOpMode{
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        boolean armFChanged = false, depositChanged = false, collectChanged = false, armBChanged = false;

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

            //arm forward
            if (gamepad1.b) {
                if(!armFChanged){
                    robot.armMotor.setPower(-1);
                    robot.armMotor2.setPower(-1);
                    armFChanged = true;
                }
                else if(armFChanged){
                    robot.armMotor.setPower(0);
                    robot.armMotor2.setPower(0);
                    armFChanged = false;
                }

            }

            //arm backward
            if (gamepad1.x) {
                if(!armBChanged){
                    robot.armMotor.setPower(1);
                    robot.armMotor2.setPower(1);
                    armBChanged = true;
                }
                else if(armBChanged){
                    robot.armMotor.setPower(0);
                    robot.armMotor2.setPower(0);
                    armBChanged = false;
                }
            }

            //collector
            if (gamepad1.right_bumper) {
                if(!collectChanged){
                    robot.intakeMotor.setPower(0.6);
                    collectChanged = true;
                }
                else if(collectChanged){
                    robot.intakeMotor.setPower(0);
                    collectChanged = false;
                }
            }

            //deposit
            if (gamepad1.left_bumper) {
                if(!depositChanged){
                    robot.intakeMotor.setPower(-1);
                    depositChanged = true;
                }
                else if(depositChanged){
                    robot.intakeMotor.setPower(0);
                    depositChanged = false;
                }
            }

            //carousel
            /*if(gamepad1.dpad_up){
                runCarousel();
            }*/

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}