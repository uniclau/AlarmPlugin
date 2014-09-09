package com.uniclau.alarmplugin;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AlarmBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intentp)
    {
        if (intentp.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
        	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        	long dt = settings.getLong("AlarmPlugin.AlarmDate", 0);
        	long now = (new Date()).getTime();
        	if (dt>now) {        		
            	AlarmManager alarmMgr = (AlarmManager)(context.getSystemService(Context.ALARM_SERVICE));
				
				PendingIntent alarmIntent;     
				Intent intent = new Intent(context, AlarmReceiver.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
				
				alarmMgr.set(AlarmManager.RTC_WAKEUP,  dt, alarmIntent);        		
        	}
        }
    }
}
