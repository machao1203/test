var exec = require('cordova/exec');
module.exports ={
    socket_connect: function() {
    	console.log('123456787654321');
        exec(null, null, "socket", "connect", []);
    },
    socket_close: function() {
    	console.log('socket close');
        exec(null, null, "socket", "close", []);
    },
    socket_send:function(msg)
    {
        console.log('socket send');
        exec(null, null, "socket", "send", [msg]);
    }
    };