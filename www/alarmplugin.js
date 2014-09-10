var alarm = {
    set: function(alarmDate, successCallback, errorCallback) {
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
