/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.FnoblyCode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


public class FnoblyHardware
{
    /* Public OpMode members. */
    public DcMotor  topLeftDrive   = null;
    public DcMotor  topRightDrive  = null;
    public DcMotor  bottomRightDrive  = null;
    public DcMotor  bottomLefttDrive  = null;
    //public DcMotor  strafer = null;
    public Servo arm = null;
    public ColorSensor sensorColor;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public FnoblyHardware() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;


        sensorColor = hwMap.colorSensor.get("sensor");
        topLeftDrive  = hwMap.dcMotor.get("top_left_drive");
        topRightDrive = hwMap.dcMotor.get("top_right_drive");
        bottomLefttDrive = hwMap.dcMotor.get("bottom_left_drive");
        bottomRightDrive = hwMap.dcMotor.get("bottom_right_drive");
        //strafer = hwMap.dcMotor.get("strafing_drive");
        arm = hwMap.servo.get("Side_Arm");
        topLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        topRightDrive.setDirection(DcMotor.Direction.FORWARD);
        bottomLefttDrive.setDirection(DcMotor.Direction.REVERSE);
        bottomRightDrive.setDirection(DcMotor.Direction.FORWARD);
        //strafer.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        topLeftDrive.setPower(0);
        topRightDrive.setPower(0);
        //strafer.setPower(0);

        // Set all motors to run with encoders.
        topLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        topRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //strafer.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
