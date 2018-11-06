package com.example.sejil.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

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
                if (msgsender.equals("+9820004000")) {
                    String[] split_enters = msgbody.split("\n");
                    String[] split_money = split_enters[1].split(":");
                    if (split_money[0].equals("انتقال") || split_money[0].equals("خریداینترنتی") || split_money[0].equals("برداشت") || split_money[0].equals("خودپرداز")) {
                        Intent i = new Intent(context, MainActivity.class);
                        i.putExtra("MSG_SENDER", msgsender);
                        i.putExtra("MSG_BODY", msgbody);

                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                }else if(msgsender.equals("Bank Mellat")){
                    Log.d("onReceive", "onReceive: BANK MELLAT");
//                    Intent i = new Intent(context, MainActivity.class);
//                    i.putExtra("MSG_SENDER", msgsender);
//                    i.putExtra("MSG_BODY", msgbody);
//
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(i);
                }
            }
        }
    }

}