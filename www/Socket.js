cordova.define("org.wdc.socket.socket", function(require, exports, module) {var exec = require('cordova/exec');
module.exports ={
    socket_connect: function(successCallBack,errorCallBack) {
    	console.log('123456787654321');
        exec(successCallBack, errorCallBack, "socket", "connect", []);
    },
    socket_close: function(successCallBack) {
    	console.log('socket close');
        exec(successCallBack, null, "socket", "close", []);
    },
    socket_send:function(successCallBack,errorCallBack,msg)
    {
        console.log('socket send');
        exec(successCallBack, errorCallBack, "socket", "send", [msg]);
    },
    socket_poll:function(successCallBack){
    	exec(successCallBack, null, "socket", "rollpoling", []);
    }
    };});
