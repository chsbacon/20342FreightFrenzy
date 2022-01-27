package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.robocol.Heartbeat;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.opencv.core.Scalar;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Locale;

public class AutoBasicOpMode extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    EncoderHMap robot = new EncoderHMap();

    static final double     COUNTS_PER_MOTOR_REV = 1548.3;
    static final double     DRIVE_GEAR_REDUCTION = 60.0/72.0;   // output (wheel) speed / input (motor) speed
    static final double     WHEEL_DIAMETER_INCHES = 5.0;
    static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.5;
    static final double TURN_SPEED = 0.8;
    static int IMAGE_LEVEL = 1;
    public boolean wait = false, shallow = false, blue = false, carousel = false;

    @Override
    public void runOpMode(){}

    public void runCarousel() {
        robot.carouselMotor.setPower(0.2);
        sleep(2300);
        robot.carouselMotor.setPower(0);

        telemetry.addData("Carousel", "Complete");
    }

    public void runIntakeMotor(int time, boolean deposit) {
        if(deposit) robot.intakeMotor.setPower(0.6);
        else robot.intakeMotor.setPower(-1); //collect

        sleep(time);
        robot.intakeMotor.setPower(0);
    }

    public void armMove(int degrees) {
        double ticks = ((degrees)/360.0)*288.0*(125.0/40.0);
        robot.armMotor.setTargetPosition((int)ticks);
        robot.armMotor2.setTargetPosition((int)ticks);
        robot.armMotor.setPower(1);
        robot.armMotor2.setPower(1);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.armMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(robot.armMotor.isBusy()) {
        }
        robot.armMotor.setPower(0);
        robot.armMotor2.setPower(0);
        robot.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void armSet(int Setting) {
        switch(Setting) {
            case 1:
                armMove(30); //1st level
                encoderDrive(DRIVE_SPEED,2,2);  //go forward more
                runIntakeMotor(2000, true);
                break;
            case 2:
                encoderDrive(DRIVE_SPEED, 2, 2);
                armMove(55); //2nd level
                runIntakeMotor(2000, true);
                break;
            case 3:
                encoderDrive(DRIVE_SPEED, 4, 4);
                armMove(65); //3rd level
                runIntakeMotor(2000, true);
                break;
            case 4:
                armMove(10); //Collecting
                runIntakeMotor(500, false);
                break;
            case 5: //Neutral
                break;
        }
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches) {

        // Ensure that the OpMode is still active
        if (!opModeIsActive()) return;

        // Determine new target position, and pass to motor controller
        int newLeftTarget = robot.leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH) ;
        int newRightTarget = robot.rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
        robot.leftMotor.setTargetPosition(newLeftTarget);
        robot.rightMotor.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start motion (specified speed)
        runtime.reset();
        robot.leftMotor.setPower(Math.abs(speed));
        robot.rightMotor.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        while (opModeIsActive() &&
                (!checkMotor(newLeftTarget, newRightTarget))) {
            telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d",
                    robot.leftMotor.getCurrentPosition(),
                    robot.rightMotor.getCurrentPosition());
            telemetry.update();
        }

        // Stop all motion;
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean checkMotor(int newLeftTarget, int newRightTarget){
        boolean checkMotorBool = true;
        int leftT = robot.leftMotor.getCurrentPosition();
        int rightT = robot.rightMotor.getCurrentPosition();
        if(leftT < newLeftTarget-20 || leftT > newLeftTarget+20){ checkMotorBool = false; }
        if(rightT < newRightTarget-20 || rightT > newRightTarget+20){ checkMotorBool = false; }
        return checkMotorBool;
    }

    public OpenCvCamera webcam;
    private static final int CAMERA_WIDTH  = 1920; // width  of wanted camera resolution
    private static final int CAMERA_HEIGHT = 1080; // height of wanted camera resolution

    // Blue Range                                      Y      Cr     Cb
    public static Scalar scalarLowerYCrCb = new Scalar(  0.0, 40.0, 160.0);
    public static Scalar scalarUpperYCrCb = new Scalar(255.0, 255.0, 255.0);

    public void initWebcam()
    {
        // OpenCV webcam
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        //OpenCV Pipeline
        ContourPipeline myPipeline;
        webcam.setPipeline(myPipeline = new ContourPipeline(0.0, 0.0, 0.0, 0.0));
        // Configuration of Pipeline
        myPipeline.configureScalarLower(scalarLowerYCrCb.val[0],scalarLowerYCrCb.val[1],scalarLowerYCrCb.val[2]);
        myPipeline.configureScalarUpper(scalarUpperYCrCb.val[0],scalarUpperYCrCb.val[1],scalarUpperYCrCb.val[2]);
        // Webcam Streaming
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
            }
        });

        // Only if you are using FTCDashboard
        //FtcDashboard dashboard = FtcDashboard.getInstance();
        //telemetry = dashboard.getTelemetry();
        //FtcDashboard.getInstance().startCameraStream(webcam, 10);

        //telemetry.update();

        while (!isStopRequested())
        {
            if(myPipeline.error){
                telemetry.addData("Exception: ", myPipeline.debug);
            }

            if(myPipeline.getRectArea() > 2000){
                if(myPipeline.getRectMidpointX() > 1011){
                    AUTONOMOUS_TOP();
                }
                else if(myPipeline.getRectMidpointX() > 484){
                    AUTONOMOUS_MIDDLE();
                }
                else {
                    AUTONOMOUS_BOTTOM();
                }
                break;
            }
        }
    }

    public void AUTONOMOUS_BOTTOM(){
        IMAGE_LEVEL = 1;
        telemetry.addData("WHERE: ", "Autonomous BOTTOM"); telemetry.update();
    }
    public void AUTONOMOUS_MIDDLE(){
        IMAGE_LEVEL = 2;
        telemetry.addData("WHERE: ","Autonomous MIDDLE"); telemetry.update();
    }
    public void AUTONOMOUS_TOP(){
        IMAGE_LEVEL = 3;
        telemetry.addData("WHERE: ","Autonomous TOP"); telemetry.update();
    }

}
