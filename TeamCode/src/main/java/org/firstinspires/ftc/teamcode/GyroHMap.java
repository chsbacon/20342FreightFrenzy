package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Hardware map for robot with encoders
**/

public class GyroHMap
{
    /* Public OpMode members. */
    public DcMotor    leftMotor, rightMotor;
    public DcMotor    armMotor,  intakeMotor;
    public DcMotor    carouselMotor;
    public GyroSensor gyro; 


    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public GyroHMap(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor     = hwMap.get(DcMotor.class, "LM");  //P0
        rightMotor    = hwMap.get(DcMotor.class, "RM");  //P1
        armMotor      = hwMap.get(DcMotor.class, "AM");  //P
        carouselMotor = hwMap.get(DcMotor.class, "CM");  //P
        //intakeMotor   = hwMap.get(DcMotor.class, "");  //P
        gyro          = hwMap.get(GyroSensor.class, "GYRO"); 
        
        //Reverse right motor
        leftMotor.setDirection(DcMotor.Direction.FORWARD); 
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        
        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        armMotor.setPower(0);
        carouselMotor.setPower(0);
        //intakeMotor.setPower(0);
        
        //calibrate gyro
        gyro.calibrate();
        while (gyro.isCalibrating()){ ; }
      
        // Set motors to run with encoders.
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
 }
