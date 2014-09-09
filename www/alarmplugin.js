var alarmPlugin = {
    programAlarm: function(dAlarm, successCallback, errorCallback) {
        cordova.exec(
            successCallback,
            errorCallback,
            "AlarmPlugin",
            "programAlarm",
            [dAlarm]
        );
    }
};
module.exports = alarmPlugin;
