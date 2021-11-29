package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
public class armFunc extends LinearOpMode {
    public void runOpMode() {
    }
    EncoderHMap hwMap = null;
    ElapsedTime runtime = null;

    public void init(EncoderHMap ahwMap, ElapsedTime runtime) {
        this.hwMap = ahwMap;
        this.runtime = runtime;
    }
    public void runIntakeMotor(double time, boolean deposit) {
        runtime.reset();
        if(deposit) {
            telemetry.addData("Status", "Intake depositing");
            telemetry.update();
            while(runtime.seconds() < time) {
                hwMap.intakeMotor.setPower(-1);
            }
        } else {
            telemetry.addData("Status", "Intake collecting");
            telemetry.update();
            while(runtime.seconds() < time) {
                hwMap.intakeMotor.setPower(1);
            }
        }
    }
    public void armMove(int degrees) {
        double ticks = (degrees/360.0)*288;
        hwMap.armMotor.setTargetPosition((int)ticks);
        hwMap.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hwMap.armMotor.setPower(1);
        while(hwMap.armMotor.isBusy()) {
            telemetry.addData("Position", hwMap.armMotor.getCurrentPosition());
            telemetry.update();
        }
        hwMap.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hwMap.armMotor.setPower(0);
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
