var test = {
    createEvent: function(successCallback, errorCallback) {
        cordova.exec(
			console.log('test3333333333333');
            successCallback, // success callback function
            errorCallback, // error callback function
            'Test', // mapped to our native Java class called "CalendarPlugin"
            'addTestEntry' // with this action name
        ); 
    }
};
module.exports = test;