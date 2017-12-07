package org.firstinspires.ftc.teamcode.FnoblyCode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "Autonomous_Blue", group = "Competition_Code")
public class Autonomous_Blue extends LinearOpMode {

    //Assign the hardware code to the phrase "robot".
    FnoblyHardware robot = new FnoblyHardware();


    //Set variables for different distances.
    //We don't want to change these, so they are final.
    private final int ONE_REV = 537;
    private final int TWO_REV = 1075;
    private final int THREE_REV = 1613;
    private final int HALF_REV = 269;
    private final int FOURTH_REV = 134;
    final static double releaseGlyphL = 0;
    final static double releaseGlyphR = .41;
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

        //Confirm that the code is running with a phrase that displays in the driver station.
        telemetry.addData("Robot is running", "Victory or death!");
        telemetry.update();

        //Reset the encoder values
        robot.FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //robot.strafer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Set the motors to run to a position rather than just until a certain time.
        robot.FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //robot.strafer.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        //Wait until the start button is pressed
        waitForStart();

        robot.grabber.setPosition(releaseGlyphL);
        robot.grabber2.setPosition(releaseGlyphR);

        //Move the arm down.
        robot.arm.setPosition(.4);

        //robot.sensorColor.enableLed(true);

        Color.RGBToHSV((int) (robot.sensorColor.red() * SCALE_FACTOR),
                (int) (robot.sensorColor.green() * SCALE_FACTOR),
                (int) (robot.sensorColor.blue() * SCALE_FACTOR),
                hsvValues);

        sleep(3000);

        //Color sensor detects red or blue and moves accordingly to knock off the right jewel.
        if (robot.sensorColor.blue()>robot.sensorColor.red() && opModeIsActive()) {
            telemetry.addData("Alpha", robot.sensorColor.alpha());
            telemetry.addData("Red  ", robot.sensorColor.red());
            telemetry.addData("Green", robot.sensorColor.green());
            telemetry.addData("Blue ", robot.sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();
            robot.arm.setPosition(.4);
            robot.FL.setPower(-.5);
            robot.FR.setPower(-.5);
            robot.BL.setPower(-.5);
            robot.BR.setPower(-.5);

            robot.FL.setTargetPosition(-FOURTH_REV);
            robot.FR.setTargetPosition(-FOURTH_REV);
            robot.BL.setTargetPosition(-FOURTH_REV);
            robot.BR.setTargetPosition(-FOURTH_REV);
        }

        else {
            telemetry.addData("Alpha", robot.sensorColor.alpha());
            telemetry.addData("Red  ", robot.sensorColor.red());
            telemetry.addData("Green", robot.sensorColor.green());
            telemetry.addData("Blue ", robot.sensorColor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();
            robot.arm.setPosition(.4);
            robot.FL.setPower(.5);
            robot.FR.setPower(.5);
            robot.BL.setPower(.5);
            robot.BR.setPower(.5);

            robot.FL.setTargetPosition(FOURTH_REV);
            robot.FR.setTargetPosition(FOURTH_REV);
            robot.BL.setTargetPosition(FOURTH_REV);
            robot.BR.setTargetPosition(FOURTH_REV);

        }

        sleep(3000);


        //Move the arm back up.
        robot.arm.setPosition(1);


        //Robot drives forward into the safe zone.

        //Set power to move forward.
        robot.FL.setPower(.5);
        robot.FR.setPower(.5);
        robot.BL.setPower(.5);
        robot.BR.setPower(.5);

        //Setting target, the robot will drive until the encoders read this value.
        robot.FL.setTargetPosition(-THREE_REV);
        robot.FR.setTargetPosition(-THREE_REV);
        robot.BL.setTargetPosition(-THREE_REV);
        robot.BR.setTargetPosition(-THREE_REV);

        //Keep the robot from moving on until it stops.
        while (robot.FL.isBusy() && robot.FR.isBusy() &&
                robot.BL.isBusy() && robot.BR.isBusy() && opModeIsActive()) {
            //Shows the encoder readout while the robot is moving.
            telemetry.addData("Top_Left_Encoder", robot.FL.getCurrentPosition());
            telemetry.addData("Top_Right_Encoder", robot.FR.getCurrentPosition());
            telemetry.addData("Bottom_Left_Encoder", robot.BL.getCurrentPosition());
            telemetry.addData("Bottom_Right_Encoder", robot.BR.getCurrentPosition());
            telemetry.update();
        }


        //Save the current encoder values for later.
        topLeftEncoder = robot.FL.getCurrentPosition();
        topRightEncoder = robot.FR.getCurrentPosition();
        bottomLeftEncoder = robot.BL.getCurrentPosition();
        bottomRightEncoder = robot.BR.getCurrentPosition();

        robot.FL.setTargetPosition(topLeftEncoder+FOURTH_REV);
        robot.FR.setTargetPosition(topRightEncoder+FOURTH_REV);
        robot.BL.setTargetPosition(bottomLeftEncoder+FOURTH_REV);
        robot.BR.setTargetPosition(bottomRightEncoder+FOURTH_REV);



    }
}
