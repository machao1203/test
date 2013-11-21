var exec = require('cordova/exec');
module.exports ={
    
    socket_connect: function(successCallBack,errorCallBack) {
        exec(successCallBack, errorCallBack, "socket", "connect", [ip_addr,ip_port,ip_xintiao]);
    },
    socket_setPara: function() {
        exec(null, null, "socket", "setPara", [ip_addr,ip_port,ip_xintiao]);
    },
    socket_close: function(successCallBack) {
    
        exec(successCallBack, null, "socket", "close", []);
    },
    socket_send:function(successCallBack,errorCallBack,msg)
    {
 
        exec(successCallBack, errorCallBack, "socket", "send", [msg]);
    },
    socket_poll:function(successCallBack,errorCallBack,msg){
    	exec(successCallBack, errorCallBack, "socket", "rollpoling", [msg]);
    },
    gb2312Tohanzi:function(successCallBack,errorCallBack,msg)
    {
        exec(successCallBack, errorCallBack, "socket", "hanzi", [msg]);
    },
};
