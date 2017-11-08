package org.firstinspires.ftc.teamcode.FnoblyCode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by matth on 11/3/2017.
 */
@TeleOp(name="Robot: slowMode", group="Robot")
//@Disabled

public class HdriveTeleOpFinal extends OpMode{

    Servo grabber;
    //Servo grabber2;
    Servo sideArm;
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor midDrive;
    DcMotor Raise;
    final static double grabGlyph = .85;
    final static double releaseGlyph = .46;
    double left;
    double right;
    double slowMode = 1;


    public void init() {

        grabber = hardwareMap.servo.get("grabberH");
        //grabber2 = hardwareMap.servo.get("grabberH2");
        sideArm = hardwareMap.servo.get("Side_Arm");
        Raise = hardwareMap.dcMotor.get("Raise_Motor");
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        midDrive = hardwareMap.dcMotor.get("strafing_drive");
        rightDrive = hardwareMap.dcMotor.get("right_drive");
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        Raise.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Raise.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sideArm.setPosition(.9);

    }


    public void loop() {

        if(gamepad1.right_trigger > .9 || gamepad1.left_trigger > .9){
            slowMode = .5;
        }

        if(gamepad1.right_trigger > .9 && gamepad1.left_trigger > .9){
            slowMode = slowMode- .3;
        }

        if(gamepad1.right_trigger < .1 && gamepad1.left_trigger < .1){
            slowMode = 1;
        }

        sideArm.setPosition(.9);

        left = -gamepad1.left_stick_y*slowMode;
        right = -gamepad1.right_stick_y*slowMode;

        leftDrive.setPower(left);
        rightDrive.setPower(right);
        telemetry.addData("Raise Position", "ticks " + Raise.getCurrentPosition());
        if (gamepad2.y) {
            Raise.setPower(.6);
            Raise.setTargetPosition(-5228);
            //if using N20 change values
        }

        if (gamepad2.x) {
            Raise.setPower(.6);
            Raise.setTargetPosition(-1000);
        }

        if(gamepad2.a) {
            Raise.setPower(.4);
            Raise.setTargetPosition(0);
        }


        if (gamepad2.b) {
            grabber.setPosition(releaseGlyph);
            telemetry.addData("Grabber", "Release Glyph");
        } else {
            grabber.setPosition(grabGlyph);
            telemetry.addData("Grabber", "Grab Glyph");
        }

       /* if (gamepad2.a) {
            grabber2.setPosition(releaseGlyph);
        } else {
            grabber2.setPosition(grabGlyph);

        }
        */

        if (gamepad1.dpad_left) {
            midDrive.setPower(-1*slowMode);
        }

        if (gamepad1.dpad_right) {
            midDrive.setPower(1*slowMode);
        }

        if(!gamepad1.dpad_right&&!gamepad1.dpad_left) {
            midDrive.setPower(0);
        }

        if(gamepad2.right_bumper)
        {
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setPower(.5);
            rightDrive.setTargetPosition(11328);
//5664
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftDrive.setPower(.5);
            leftDrive.setTargetPosition(-11328);
//5664
        }
    }
}
