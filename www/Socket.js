var exec = require('cordova/exec');
module.exports ={
    
socket_connect: function(successCallBack,errorCallBack) {
        exec(successCallBack, errorCallBack, "socket", "connect", [ip_addr,ip_port]);
    },
    socket_close: function(successCallBack) {
    
        exec(successCallBack, null, "socket", "close", []);
    },
    socket_send:function(successCallBack,errorCallBack,msg)
    {
 
        exec(successCallBack, errorCallBack, "socket", "send", [msg]);
    },
    socket_poll:function(successCallBack){
    	exec(successCallBack, null, "socket", "rollpoling", []);
    },
    gb2312Tohanzi:function(successCallBack,errorCallBack,msg)
    {
        exec(successCallBack, errorCallBack, "socket", "hanzi", [msg]);
    },
};
