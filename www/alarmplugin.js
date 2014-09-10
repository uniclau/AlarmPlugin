var alarm = {
    set: function(alarmDate, successCallback, errorCallback) {
        if(alarmDate < new Date())
    		return;
    	
        cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "programAlarm",
            [alarmDate]
        );
    }
};
module.exports = alarm;
