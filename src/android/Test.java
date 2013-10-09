package org.wdc.test;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.Toast;

public class Test extends CordovaPlugin {
    public static final String ACTION_ADD_TEST_ENTRY = "addTestEntry";
    
   @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (ACTION_ADD_TEST_ENTRY.equals(action)) {
        Toast.makeText(getApplicationContext(), "test success",
							Toast.LENGTH_SHORT).show();
        return true;
    }
    return false;  // Returning false results in a "MethodNotFound" error.
}
}
}