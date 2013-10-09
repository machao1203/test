var toast = {
    createEvent: function(msg,successCallback, errorCallback) {
        console.log('test3333333333333');
		cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'ToastTest', // service name  mapped to our native Java class called "CalendarPlugin"
            'addToastEntry', // with this action name
            [{
            'msg':msg
			}]
        ); 
    }
}
module.exports = toast;