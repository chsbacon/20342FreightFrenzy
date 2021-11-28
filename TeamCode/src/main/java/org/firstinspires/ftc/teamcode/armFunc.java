package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ElapsedTime;

public class armFunc {
    EncoderHMap hwMap2 = null;
    ElapsedTime runtime2 = null;

    public void init(EncoderHMap ahwMap, ElapsedTime runtime) {
        hwMap2 = ahwMap;
        runtime2 = runtime;
    }
    public void runIntakeMotor(double time, boolean deposit) {
        runtime2.reset();
        if(deposit) {
            while(runtime2.seconds() < time) {
                hwMap2.intakeMotor.setPower(-1);
            }
        } else {
            while(runtime2.seconds() < time) {
                hwMap2.intakeMotor.setPower(1);
            }
        }
    }
    public void armMove(int degrees) {
        double ticks = (degrees/360.0)*288;
        hwMap2.armMotor.setTargetPosition((int)ticks);
        hwMap2.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hwMap2.armMotor.setPower(1);
        while(hwMap2.armMotor.isBusy()) {
            //Nothing needed here
        }
        hwMap2.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hwMap2.armMotor.setPower(0);
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
