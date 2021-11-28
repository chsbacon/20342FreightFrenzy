package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class armFunc {
    private ElapsedTime runtime = new ElapsedTime();
    EncoderHMap robot = new EncoderHMap();
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
                runIntakeMotor(2.5, true);
                break;
            case 2:
                armMove(135); //2nd level
                runIntakeMotor(2.5, true);
                break;
            case 3:
                armMove(150); //3rd level
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
