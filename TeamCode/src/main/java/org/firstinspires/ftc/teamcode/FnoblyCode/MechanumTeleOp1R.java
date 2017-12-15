package org.firstinspires.ftc.teamcode.FnoblyCode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Thread.sleep;

@TeleOp(name = "MTeleOp1R")
public class MechanumTeleOp1R extends OpMode {

    //ModernRoboticsI2cGyro dog;
    // IntegratingGyroscope gyro;
    //  double If = dog.getIntegratedZValue();
    //double df = .1;

    double slowMode = 1;
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;

    Servo sideArm;

    boolean lastX1;
    double scaleFactor = 1;
    double strafe;

    boolean lastY2;
    boolean liftOffsetEnabled;

    boolean lastY1;
    boolean holdCapBall;

    DcMotor Raise;
    final static double grabGlyphL = .025;
    final static double releaseGlyphL = 0;
    final static double grabGlyphR = .38;
    final static double releaseGlyphR = .41;
//values from tests w/John

    Servo grabber;
    Servo grabber2;

    public void init() {

        grabber = hardwareMap.servo.get("grabberH");
        grabber2 = hardwareMap.servo.get("grabberH2");
        sideArm = hardwareMap.servo.get("Side_Arm");
        rightFront = hardwareMap.dcMotor.get("RF");
        rightBack = hardwareMap.dcMotor.get("RB");
        leftFront = hardwareMap.dcMotor.get("LF");
        leftBack = hardwareMap.dcMotor.get("LB");
        Raise = hardwareMap.dcMotor.get("Raise_Motor");
        Raise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Raise.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sideArm.setPosition(.92);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        ///  dog = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
        //gyro = (IntegratingGyroscope)dog;
        //dog.calibrate();

    }

    public void loop() {
       sideArm.setPosition(.92);

        if (gamepad1.right_trigger > .9 || gamepad1.left_trigger > .9) {
            slowMode = .5;
        }

        if (gamepad1.right_trigger > .9 && gamepad1.left_trigger > .9) {
            slowMode = slowMode - .2;
        }

        if (gamepad1.right_trigger < .1 && gamepad1.left_trigger < .1) {
            slowMode = 1;
        }

        //Drive
        //double errorPf = dog.getIntegratedZValue();

        //  telemetry.addData("position", errorPf);

        //  if(errorPf>If){
        //    If = errorPf;
        // }
        if(gamepad1.dpad_right){
            strafe = 1;
        }
        if(gamepad1.dpad_left){
            strafe = -1;
        }

        if(!gamepad1.dpad_left && !gamepad1.dpad_right){
            strafe = 0;
        }

        double Y1 = -gamepad1.left_stick_y * slowMode; //Forwards/Backwards
        double X1 = gamepad1.left_stick_x + strafe * slowMode; //Left/Right
        double X2 = gamepad1.right_stick_x * slowMode; //Rotate


        // rightFront.setPower(Y1 + (-X2 -df*errorPf+If) - X1);
        rightFront.setPower(Y1 - X2 - X1);
        rightBack.setPower(-Y1 - X2 + X1);
        // leftFront.setPower(Y1 + (X2-df*errorPf+If) + X1);
        leftFront.setPower(Y1 + X2 + X1);
        leftBack.setPower(-Y1 + X2 - X1);

        //diff slowmode
        lastX1 = gamepad1.x;
    // ???????    lastY2 = gamepad2.y; ???????????
        lastY1 = gamepad1.y;

        telemetry.addData("Raise Position", "ticks " + Raise.getCurrentPosition());
        if (gamepad1.y) {
            Raise.setPower(1);
            Raise.setTargetPosition(-2939);
        }

        if (gamepad1.x) {
            Raise.setPower(1);
            Raise.setTargetPosition(-1470);
        }

        if(gamepad1.a) {
            Raise.setPower(1);
            Raise.setTargetPosition(0);
        }

        if(Raise.getCurrentPosition() == 0)
        {
            Raise.setPower(0);
        }

        if (gamepad1.b) {

            grabber.setPosition(releaseGlyphL);
            grabber2.setPosition(releaseGlyphR);

            telemetry.addData("Grabber", "Release Glyph");
        } else {
            grabber.setPosition(grabGlyphL);
            grabber2.setPosition(grabGlyphR);
            telemetry.addData("Grabber", "Grab Glyph");

        }

        if(gamepad1.right_bumper){
            Raise.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Raise.setPower(1);
            Raise.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        if(gamepad1.left_bumper){
            Raise.setPower(0);
            Raise.setMode(DcMotor.RunMode.RESET_ENCODERS);
            Raise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

    }
}

