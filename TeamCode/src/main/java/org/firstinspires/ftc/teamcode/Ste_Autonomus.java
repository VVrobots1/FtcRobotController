package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Ste_Autonomus", group="Auto")
public class Ste_Autonomus extends LinearOpMode {

    private final double diameter = 9.6;
    private final double countsPerMotorRev = 537.5;
    private final double gearRed = 1;
    private final double circumference = Math.PI*diameter;
    private final double actualCountsPerRev = countsPerMotorRev * gearRed;
    private final double countsPerCM = actualCountsPerRev / circumference;
    private final double robotLength = 31*1.33;
    private final double robotWidth = 17*1.33;/**de modificat*/
    private final double robotDiameter = Math.sqrt(robotLength*robotLength+robotWidth*robotWidth);
    private final double robotCircumference = Math.PI*robotDiameter;
    private String DuckPosition= null;

    private DcMotor FRwheelMotor = null;
    private DcMotor FLwheelMotor = null;
    private DcMotor BRwheelMotor = null;
    private DcMotor BLwheelMotor = null;
    private Rev2mDistanceSensor SensorRatusca = null;

    @Override
    public void runOpMode()
    {
        Initialize();
        waitForStart();
        DriveForward(0.9,40);
        DriveRight(0.8,8.5);

        DuckPosition=ScanDucks();
      /**  switch (DuckPosition)
        {
            case "dreapta":
                ScenarioDr();
                break;
            case "mijloc":
                ScenarioMij();
                break;
            case "stanga":
                ScenarioSt();
                break;
        }
        */

        telemetry.addData("ratiusca e ", DuckPosition);
        telemetry.update();
    }


    public void Initialize()
    {
        FRwheelMotor = hardwareMap.get(DcMotor.class,"fataDreapta");
        FLwheelMotor = hardwareMap.get(DcMotor.class,"fataStanga");
        BRwheelMotor = hardwareMap.get(DcMotor.class,"spateDreapta");
        BLwheelMotor = hardwareMap.get(DcMotor.class,"spateStanga");


        SensorRatusca = hardwareMap.get(Rev2mDistanceSensor.class, "senzorDistantaRatusca");

        FRwheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLwheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRwheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLwheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FRwheelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLwheelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRwheelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLwheelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FRwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FLwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BRwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BLwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FRwheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BRwheelMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        FLwheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        BLwheelMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void ScenarioSt() {
    }

    public void ScenarioMij() {

    }

    public void ScenarioDr(){
    }

    public String ScanDucks() {
        if (SensorRatusca.getDistance(DistanceUnit.CM) < 8)
            return "Dreapta";
        DriveLeft(1,20);
        if(SensorRatusca.getDistance(DistanceUnit.CM) < 8)
            return "Mijloc";
        else
            return "Stanga";
    }

    public void DriveTo(double speed, double frontRightCM, double backRightCM, double frontLeftCM, double backLeftCM)
    {

        if(opModeIsActive()) {

            int frontRightNewPos = FRwheelMotor.getCurrentPosition() + (int) (frontRightCM * countsPerCM);
            int backRightNewPos = BRwheelMotor.getCurrentPosition() + (int) (backRightCM * countsPerCM);
            int frontLeftNewPos = FLwheelMotor.getCurrentPosition() + (int) (frontLeftCM * countsPerCM);
            int backLeftNewPos = BLwheelMotor.getCurrentPosition() + (int) (backLeftCM * countsPerCM);

            FRwheelMotor.setTargetPosition(frontRightNewPos);
            BRwheelMotor.setTargetPosition(backRightNewPos);
            FLwheelMotor.setTargetPosition(frontLeftNewPos);
            BLwheelMotor.setTargetPosition(backLeftNewPos);

            FRwheelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FLwheelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BRwheelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BLwheelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            FRwheelMotor.setPower(speed);
            BRwheelMotor.setPower(speed);
            FLwheelMotor.setPower(speed);
            BLwheelMotor.setPower(speed);

            while(  opModeIsActive() &&
                    (       FRwheelMotor.isBusy() ||
                            BRwheelMotor.isBusy() ||
                            FLwheelMotor.isBusy() ||
                            BLwheelMotor.isBusy()
                    )
            ); /**{
                telemetry.addData("Scenario:" , DuckPosition);

                telemetry.addData("Destination", "FR: %7d ; BR: %7d ; FL: %7d ; BL: %7d",
                        frontRightNewPos,
                        backRightNewPos,
                        frontLeftNewPos,
                        backLeftNewPos);

                telemetry.addData("Position", "FR: %7d ; BR: %7d ; FL: %7d ; BL: %7d",
                        FRwheelMotor.getCurrentPosition(),
                        BRwheelMotor.getCurrentPosition(),
                        FLwheelMotor.getCurrentPosition(),
                        BLwheelMotor.getCurrentPosition());


                telemetry.update();}*/


            FRwheelMotor.setPower(0);
            BRwheelMotor.setPower(0);
            FLwheelMotor.setPower(0);
            BLwheelMotor.setPower(0);

            FRwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FLwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BRwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BLwheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(200);
        }

    }
    public void DriveForward(double speed, double distanceCM)
    {
        DriveTo(speed,distanceCM,distanceCM,distanceCM,distanceCM);
    }
    public void DriveBackwards(double speed, double distanceCM)
    {
        DriveTo(speed,-distanceCM,-distanceCM,-distanceCM,-distanceCM);
    }
    public void DriveRight(double speed, double distanceCM)
    {
        DriveTo(speed,-distanceCM,distanceCM,distanceCM,-distanceCM);
    }
    public void DriveLeft(double speed, double distanceCM)
    {
        DriveTo(speed,distanceCM,-distanceCM,-distanceCM,distanceCM);
    }
    public void TurnRight(double speed, double degrees)
    {
        double arcRatio=degrees/360;
        double distanceCM = robotCircumference*arcRatio;
        DriveTo(speed,distanceCM,distanceCM,-distanceCM,-distanceCM);
    }
    public void TurnLeft(double speed, double degrees)
    {
        double arcRatio=degrees/360;
        double distanceCM = (robotCircumference*arcRatio);
        DriveTo(speed,-distanceCM,-distanceCM,distanceCM,distanceCM);
    }
}