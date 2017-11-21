package org.firstinspires.ftc.teamcode.FnoblyCode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Color_Sensor.HadiColorSensorBlue;


@Autonomous(name = "Autonomous_Red", group = "Prototype_Code")
public class Autonomous_Red extends LinearOpMode {

      //Assign the hardware code to the phrase "robot".
      FnoblyHardware robot = new FnoblyHardware();


    //Set variables for different distances.
    //We don't want to change these, so they are final.
      private final int ONE_REV = 537;
      private final int TWO_REV = 1075;
      private final int THREE_REV = 1613;
      private final int HALF_REV = 269;
      private final int FOURTH_REV = 134;


    //Set variables for encoder values.
    //These will be assigned values later, so they are not final.
    private int topLeftEncoder = 0;
    private int topRightEncoder = 0;
    private int bottomLeftEncoder = 0;
    private int bottomRightEncoder = 0;
    private int straferEncoder = 0;

    @Override
    public void runOpMode() {


        //Get data from the hardware
        robot.init(hardwareMap);

        //Confirm that the code is running with a silly phrase that displays in the driver station.
        telemetry.addData("Victory or death!", "Lok'tar Ogar!");
        telemetry.update();

        //Reset the encoder values
        robot.topLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.topRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.bottomLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.bottomRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.strafer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set the motors to run to a position rather than just until a certain time.
        robot.topLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.topRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.bottomLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.bottomRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //robot.strafer.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        //Wait until the start button is pressed
        waitForStart();

        //Move the arm down.
        robot.arm.setPosition(.4);

        //robot.sensorColor.enableLed(true);

        Color.RGBToHSV((int) (robot.sensorColor.red() * SCALE_FACTOR),
                (int) (robot.sensorColor.green() * SCALE_FACTOR),
                (int) (robot.sensorColor.blue() * SCALE_FACTOR),
                hsvValues);

        sleep(3000);

        //Color sensor detects red or blue and moves accordingly to knock off the right jewel.
        if (robot.sensorColor.red()>robot.sensorColor.blue() && opModeIsActive()) {
            telemetry.addData("Alpha", robot.sensorColor.alpha());
            telemetry.addData("Red  ", robot.sensorColor.red());
            telemetry.addData("Green", robot.sensorColor.green());
            telemetry.addData("Blue ", robot.sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();
            robot.arm.setPosition(.4);
            robot.topRightDrive.setPower(-.5);
            robot.topLeftDrive.setPower(-.5);
            robot.bottomLeftDrive.setPower(-.5);
            robot.bottomRightDrive.setPower(-.5);
            robot.topRightDrive.setTargetPosition(-269);
            robot.topLeftDrive.setTargetPosition(-269);
            robot.bottomLeftDrive.setTargetPosition(-269);
            robot.bottomRightDrive.setTargetPosition(-269);
        }

        else {
            telemetry.addData("Alpha", robot.sensorColor.alpha());
            telemetry.addData("Red  ", robot.sensorColor.red());
            telemetry.addData("Green", robot.sensorColor.green());
            telemetry.addData("Blue ", robot.sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();
            robot.arm.setPosition(.4);
            robot.topLeftDrive.setPower(.5);
            robot.topRightDrive.setPower(.5);
            robot.bottomLeftDrive.setPower(.5);
            robot.bottomRightDrive.setPower(.5);
            robot.topLeftDrive.setTargetPosition(269);
            robot.topRightDrive.setTargetPosition(269);
            robot.bottomLeftDrive.setTargetPosition(269);
            robot.bottomRightDrive.setTargetPosition(269);

        }

        sleep(3000);
        //Move the arm back up.
        robot.arm.setPosition(1);


        //Unused code for strafing. May be used at a later date, but not currently.

        /*robot.strafer.setPower(.5);

        robot.strafer.setTargetPosition(THREE_REV);

        while (robot.strafer.isBusy() && opModeIsActive()) {
            telemetry.addData("Strafer_Encoder", robot.strafer.getCurrentPosition());
            telemetry.update();
        }

        straferEncoder = robot.strafer.getCurrentPosition();*/


        //Robot drives forward into the safe zone.

        //Set power to move forward.
        robot.topRightDrive.setPower(-.5);
        robot.topLeftDrive.setPower(-.5);
        robot.bottomRightDrive.setPower(-.5);
        robot.bottomLeftDrive.setPower(-.5);

        //Setting target, the robot will drive until the encoders read this value.
        robot.topLeftDrive.setTargetPosition(-THREE_REV);
        robot.topRightDrive.setTargetPosition(-THREE_REV);
        robot.bottomLeftDrive.setTargetPosition(-THREE_REV);
        robot.bottomRightDrive.setTargetPosition(-THREE_REV);

        //Keep the robot from moving on until it stops.
        while (robot.topLeftDrive.isBusy() && robot.topRightDrive.isBusy() &&
                robot.bottomLeftDrive.isBusy() && robot.bottomRightDrive.isBusy() && opModeIsActive()) {
            //Shows the encoder readout while the robot is moving.
            telemetry.addData("Top_Left_Encoder", robot.topLeftDrive.getCurrentPosition());
            telemetry.addData("Top_Right_Encoder", robot.topRightDrive.getCurrentPosition());
            telemetry.addData("Bottom_Left_Encoder", robot.bottomLeftDrive.getCurrentPosition());
            telemetry.addData("Bottom_Right_Encoder", robot.bottomRightDrive.getCurrentPosition());
            telemetry.update();
        }


        //Save the current encoder values for later.
        topLeftEncoder = robot.topLeftDrive.getCurrentPosition();
        topRightEncoder = robot.topRightDrive.getCurrentPosition();
        bottomLeftEncoder = robot.bottomLeftDrive.getCurrentPosition();
        bottomRightEncoder = robot.bottomRightDrive.getCurrentPosition();

        //Beep boop



     }
}
