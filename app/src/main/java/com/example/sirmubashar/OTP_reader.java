package com.example.sirmubashar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class OTP_reader extends BroadcastReceiver {
    private  static EditText index0,index1,index2,index3;
    String OTP;
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] smsMessages= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage smsMessage:smsMessages)
        {
            String mesaage_body=smsMessage.getMessageBody();
            OTP=mesaage_body.split(":")[1];
            index0.setText(OTP.substring(0));
            index1.setText(OTP.substring(1));
            index2.setText(OTP.substring(2));
            index3.setText(OTP.substring(3));
         }
    }
    public void setOTPtext(EditText index0,EditText index1,EditText index2,EditText index3 )
    {
        OTP_reader.index0=index0;
        OTP_reader.index1=index1;
        OTP_reader.index2=index2;
        OTP_reader.index3=index3;
    }

}
