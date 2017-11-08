package org.firstinspires.ftc.teamcode;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by matth on 10/26/2017.
 */
@TeleOp(name="test_robot_controls", group="Robot")
//@Disabled
public class test_robot_teleop extends OpMode {

    DcMotor leftback;
    DcMotor leftfront;
    DcMotor midDrive;
    DcMotor rightback;
    DcMotor rightfront;
    double left;
    double right;


    public void init() {

        leftback = hardwareMap.dcMotor.get("leftback");
        leftfront = hardwareMap.dcMotor.get("leftfront");
        midDrive = hardwareMap.dcMotor.get("mid_drive");
        rightfront = hardwareMap.dcMotor.get("rightfront");
        rightback = hardwareMap.dcMotor.get("rightback");
        leftback.setDirection(DcMotor.Direction.REVERSE);
        leftfront.setDirection(DcMotor.Direction.REVERSE);


    }


    public void loop() {

        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;

        leftfront.setPower(left);
        leftback.setPower(left);
        rightfront.setPower(right);
        rightback.setPower(right);


        if (gamepad1.dpad_left) {
            midDrive.setPower(1);
        }

        if (gamepad1.dpad_right) {
            midDrive.setPower(-1);
        }

        if(!gamepad1.dpad_left&&!gamepad1.dpad_right){
            midDrive.setPower(0);
        }
    }}