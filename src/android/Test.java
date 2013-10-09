package org.wdc.test;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

public class Test extends CordovaPlugin {
    public static final String ACTION_ADD_TEST_ENTRY = "addTestEntry";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (ACTION_ADD_TEST_ENTRY.equals(action)) { 
               /*JSONObject arg_object = args.getJSONObject(0);
              
                      Builder builder = new AlertDialog.Builder(this);
			   builder.setTitle("test");
			   builder.setMessage("test success");
			   builder.setPositiveButton("OK", null);
			   builder.show();   */  
			   this.beep(10);
               callbackContext.success();
               return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        } 
    }
}