package com.example.sejil.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSreceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();

        String msgsender = "";
        String msgbody = "";

        if (extras != null) {
            Object[] smsExtra = (Object[]) extras.get("pdus");

            assert smsExtra != null;
            for (Object aSmsExtra : smsExtra) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) aSmsExtra);

                msgsender = sms.getOriginatingAddress();
                msgbody = sms.getMessageBody();
            }

            if (msgsender != null) {
                if (msgsender.equals("+9820004000") || msgsender.equals("Bank Mellat")) {
                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("MSG_SENDER", msgsender);
                    i.putExtra("MSG_BODY", msgbody);

                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        }
    }

}