package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSmsActivity extends AppCompatActivity {

    Button sendSMSBtn;
    EditText toPhoneNumberET;
    EditText smsMessageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        sendSMSBtn = (Button) findViewById(R.id.btnSendSMS);
        toPhoneNumberET = (EditText) findViewById(R.id.editTextPhoneNo);
        smsMessageET = (EditText) findViewById(R.id.editTextSMS);
        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    protected void sendSMS() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            MyMessage();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }
    }

        private void MyMessage(){
            String toPhoneNumber = toPhoneNumberET.getText().toString().trim();
            String smsMessage = smsMessageET.getText().toString().trim();

            if(!toPhoneNumberET.getText().toString().equals("") || !smsMessageET.getText().toString().equals("")) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);

                Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter number or message", Toast.LENGTH_SHORT).show();
            }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //
        switch (requestCode) {
            case 0: {
                if (grantResults.length >= 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyMessage();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_SHORT).show();
                    }
                break;
            }
        }
    }

    public void goToInbox(View view) {
        Intent intent = new Intent(SendSmsActivity.this, ReceiveSmsActivity.class);
        startActivity(intent);
    }
}
