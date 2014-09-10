package com.uniclau.alarmplugin;

import java.text.SimpleDateFormat;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.Date;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.Log;


public class AlarmPlugin extends CordovaPlugin {

	
	   @Override
	    public void onPause(boolean multitasking) {
	        Log.d("AlarmPlugin", "onPause");
	        super.onPause(multitasking);
	    }

	    @Override
	    public void onResume(boolean multitasking) {
	        Log.d("AlarmPlugin", "onResume " );
	        super.onResume(multitasking);
	        
	        PowerManager pm = (PowerManager)this.cordova.getActivity().getSystemService(Context.POWER_SERVICE);
	        WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
	        wakeLock.acquire();
	 
	        KeyguardManager keyguardManager = (KeyguardManager) this.cordova.getActivity().getSystemService(Context.KEYGUARD_SERVICE); 
	        KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("TAG");
	        keyguardLock.disableKeyguard();
	    }
	    
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
			if ("programAlarm".equals(action)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				Date aDate = sdf.parse(args.getString(0).replace("Z", "+0000"));
				
				Date n = new Date();
				if(aDate.before(n)) {
					callbackContext.error("The date is in the past");
					return true;
				}
				
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.cordova.getActivity());
				SharedPreferences.Editor editor = settings.edit();
	            editor.putLong("AlarmPlugin.AlarmDate", aDate.getTime()); //$NON-NLS-1$
	            editor.commit();
				
				AlarmManager alarmMgr = (AlarmManager)(this.cordova.getActivity().getSystemService(Context.ALARM_SERVICE));
				
				PendingIntent alarmIntent;     
				Intent intent = new Intent(this.cordova.getActivity(), AlarmReceiver.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				alarmIntent = PendingIntent.getBroadcast(this.cordova.getActivity(), 0, intent, 0);
				
				alarmMgr.cancel(alarmIntent);
				alarmMgr.set(AlarmManager.RTC_WAKEUP,  aDate.getTime(), alarmIntent);
				
				callbackContext.success("Alarm set at: " +sdf.format(aDate));
			    return true; 		
			}
			return false;		
		} catch(Exception e) {
		    System.err.println("Exception: " + e.getMessage());
		    callbackContext.error(e.getMessage());
		    return false;
		} 
	}
}
