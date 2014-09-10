if(!navigator.plugins)
    navigator.plugins = {};

navigator.plugins.alarm = {
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
module.exports = navigator.plugins.alarm;
