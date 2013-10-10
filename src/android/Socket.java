package org.wdc.socket;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.net.Socket;
import java.net.UnknownHostException;


import android.app.AlertDialog;

public class socket extends CordovaPlugin {
    
   @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("connect")) {
		try {
			Socket newsocket = new Socket("219.147.26.62", 2047);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
    }
	callbackContext.error("false");
    return false;  // Returning false results in a "MethodNotFound" error.
}
  
}
