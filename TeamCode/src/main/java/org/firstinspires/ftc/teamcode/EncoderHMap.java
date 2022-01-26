package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Hardware map for robot with encoders
**/

public class EncoderHMap
{
    /* Public OpMode members. */
    public DcMotor  leftMotor     = null;
    public DcMotor  rightMotor    = null;
    public DcMotor  armMotor      = null;
    public DcMotor  armMotor2      = null;
    public DcMotor  carouselMotor = null;
    public DcMotor  intakeMotor   = null;
    public DcMotor  topMotor      = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public EncoderHMap(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor     = hwMap.get(DcMotor.class, "LM");  //P0
        rightMotor    = hwMap.get(DcMotor.class, "RM");  //P1
        armMotor      = hwMap.get(DcMotor.class, "AML");  //P
        armMotor2     = hwMap.get(DcMotor.class, "AMR");  //P
        carouselMotor = hwMap.get(DcMotor.class, "CM");  //P
        intakeMotor   = hwMap.get(DcMotor.class, "IM");  //P
        topMotor      = hwMap.get(DcMotor.class, "TM"); //P
        
        //Reverse right motor
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor2.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        armMotor.setPower(0);
        armMotor2.setPower(0);
        carouselMotor.setPower(0);
        intakeMotor.setPower(0);
        topMotor.setPower(0);

        //Stop and reset encoders
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to run with encoders.
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
 }
