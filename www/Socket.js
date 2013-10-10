/*var toast = {
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
*/
var exec = require('cordova/exec');
module.exports ={
	 /**
     * Open a native alert dialog, with a customizable title and button text.
     *
     * @param {String} message              Message to print in the body of the alert
     * @param {Function} completeCallback   The callback that is called when user clicks on a button.
     * @param {String} title                Title of the alert dialog (default: Alert)
     * @param {String} buttonLabel          Label of the close button (default: OK)
     */
    socket_connect: function(completeCallback) {
        exec(completeCallback, null, "socket", "connect", []);
    });