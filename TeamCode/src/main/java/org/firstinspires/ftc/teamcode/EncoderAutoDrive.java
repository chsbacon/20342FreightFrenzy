package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "EncoderAutoDrive", group = "Examples")
//@Disabled

public class EncoderAutoDrive extends LinearOpMode{
    HardwareMap2022         robot = new HardwareMap2022();
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV = 560;
    static final double     DRIVE_GEAR_REDUCTION = 2.0; //! ask gear ratio
    static final double     WHEEL_DIAMETER_INCHES = 4.0; //! change
    static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                              (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.3;
    static final double TURN_SPEED = 0.2;

    @Override
    public void runOpMode(){
        robot.init(hardwareMap);

    }


}