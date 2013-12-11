var exec = require('cordova/exec');
module.exports ={
    
    socket_connect: function(successCallBack,errorCallBack) {
        var xt_cycle = ip_xintiao*1000;
        exec(successCallBack, errorCallBack, "socket", "connect", [ip_addr,ip_port,xt_cycle]);
    },
    socket_setPara: function() {
        var xt_cycle = ip_xintiao*1000;
        exec(null, null, "socket", "setPara", [ip_addr,ip_port,xt_cycle]);
    },
    socket_close: function(successCallBack) {
    
        exec(successCallBack, null, "socket", "close", []);
    },
    socket_send:function(successCallBack,errorCallBack,msg)
    {
 
        exec(successCallBack, errorCallBack, "socket", "send", [msg]);
    },
    socket_read_data:function(successCallBack,errorCallBack,msg){
    	exec(successCallBack, errorCallBack, "socket", "read_data", [msg]);
    },
    gb2312Tohanzi:function(successCallBack,errorCallBack,msg)
    {
        exec(successCallBack, errorCallBack, "socket", "hanzi", [msg]);
    },
    hanzi2GB2312:function(successCallBack,errorCallBack,msg){
    	exec(successCallBack, errorCallBack, "socket", "hanzi2GB2312", [msg]);
    },
    start_xintiao:function(successCallBack,errorCallBack)
    {
        exec(successCallBack, errorCallBack, "socket", "start_xintiao",[]);
    },
};
