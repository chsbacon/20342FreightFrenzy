package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Hardware map for robot with encoders
**/

public class MecanumHMap
{
    /* Public OpMode members. */
    public DcMotor  LHMotor     = null;
    public DcMotor  RHMotor     = null;
    public DcMotor  FVMotor    = null;
    public DcMotor  BVMotor      = null;

    public RevBlinkinLedDriver blinkinLedDriver;
    public RevBlinkinLedDriver.BlinkinPattern pattern;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public MecanumHMap(HardwareMap ahwMap){
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        LHMotor    = hwMap.get(DcMotor.class, "LH");
        RHMotor    = hwMap.get(DcMotor.class, "RH");
        FVMotor    = hwMap.get(DcMotor.class, "FV");
        BVMotor    = hwMap.get(DcMotor.class, "BV");
    }

    /* Initialize standard Hardware interfaces */
    public void init() {
        //Reverse right motor
        LHMotor.setDirection(DcMotor.Direction.REVERSE);
        RHMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        LHMotor.setPower(0);
        RHMotor.setPower(0);
        FVMotor.setPower(0);
        BVMotor.setPower(0);

        //stop and reset?
        // Set motors to run with encoders.
        LHMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RHMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FVMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BVMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior
        LHMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RHMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FVMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BVMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
 }
