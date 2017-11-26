package com.example.kyung.broadcastreceiver.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

/**
 * 1. Manifest 에 receiver 및 권한 설정 설정
 * 2. 등록된 receiver 를 통해 넘어온 브로드캐스트 메시지를 받아서 처리 후 전달
 *    - 엑티비티가 떠 있을 경우 `onNewIntent` 에 intent가 담겨서 넘어옴
 */
public class SMSReceiver extends BroadcastReceiver {

    private TextView textView;

    public SMSReceiver(TextView textView){
        this.textView = textView;
    }

    // 등록된 receiver 를 통해 브로드캐스트 메시지가 intent에 담겨 넘어온다.
    @Override
    public void onReceive(Context context, Intent intent) {

        // manifest에 등록한 action인지 확인
        if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            Object msgs[] = (Object[]) bundle.get("pdus");

            SmsMessage smsMessage[] = new SmsMessage[msgs.length];
            for (int i = 0; i < msgs.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) msgs[i]);
                String msg = smsMessage[i].getMessageBody().toString();
                Log.i("Broadcast", "SMS=" + msg);

                /* (형식)
                [은행] 인증번호를 입력해주세요
                인증번호 : 8765
                 */
                if(msg.startsWith("[은행]")){
                    String verification_number =  msg.split(" : ")[1];
                    textView.setText(verification_number);
                }
            }
        }
    }
}
