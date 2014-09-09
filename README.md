AlarmPlugin
===========

Alarm plugin for phonegap.

To install the plugin:

  cordova plugin add https://github.com/uniclau/AlarmPlugin

To call the plugin: 

alarmPlugin.programAlarm(wakeupdate, onSucces, onError)

Where wakedate (of type Date) is the date that the application will wake up. (Even if the device has been rebooted.

onSucces is called if the alarm is setted ol.

onError is called if an error has been produced.



