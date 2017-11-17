package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp")
public class Mechanum_Drive_Controls extends OpMode {
    DcMotor rightFront;
    DcMotor rightBack;
    DcMotor leftFront;
    DcMotor leftBack;

    boolean lastX1;
    boolean slowMode;
    double scaleFactor = 1;

    boolean lastY2;
    boolean liftOffsetEnabled;

    boolean lastY1;
    boolean holdCapBall;


    @Override
    public void init() {
        rightFront = hardwareMap.dcMotor.get("RF");
        rightBack = hardwareMap.dcMotor.get("RB");
        leftFront = hardwareMap.dcMotor.get("LF");
        leftBack = hardwareMap.dcMotor.get("LB");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        //Drive
        if (gamepad1.x && !lastX1) {
            slowMode = !slowMode;
        }

        if (slowMode) {
            scaleFactor = 0.25;
        } else {
            scaleFactor = 1;
        }
        double Y1 = gamepad1.left_stick_y * scaleFactor; //Forwards/Backwards
        double X1 = -gamepad1.left_stick_x * scaleFactor; //Left/Right
        double X2 = -gamepad1.right_stick_x * scaleFactor; //Rotate

        rightFront.setPower(Y1 - X2 - X1);
        rightBack.setPower(Y1 - X2 + X1);
        leftFront.setPower(Y1 + X2 + X1);
        leftBack.setPower(Y1 + X2 - X1);

        lastX1 = gamepad1.x;
        lastY2 = gamepad2.y;
        lastY1 = gamepad1.y;
    }
}