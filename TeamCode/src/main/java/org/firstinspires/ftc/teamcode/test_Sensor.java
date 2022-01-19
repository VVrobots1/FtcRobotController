/**Copyright Dobrinel */
package org.firstinspires.ftc.teamcode;

//Librarii

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name="Sensor test", group="Linear Opmode")
public class test_Sensor extends LinearOpMode {
    private ColorRangeSensor lowSensor =null;
    private Rev2mDistanceSensor highSensor = null;

    @Override public void runOpMode()    {

        highSensor = hardwareMap.get(Rev2mDistanceSensor.class, "senzorDistanta");
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Distanta distanta",highSensor.getDistance(DistanceUnit.CM));
            telemetry.update();


        }
    }
}