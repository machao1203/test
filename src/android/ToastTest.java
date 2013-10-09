package org.wdc.toast;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.Toast;

public class ToastTest extends CordovaPlugin {
    public static final String ACTION_ADD_TOAST_ENTRY = "addToastEntry";
    
   @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (ACTION_ADD_TOAST_ENTRY.equals(action)) {
		String msg = args.getString(0);
        Toast.makeText(this.cordova.getActivity(), msg,
							Toast.LENGTH_SHORT).show();
		callbackContext.success();
        return true;
    }
	callbackContext.error("false");
    return false;  // Returning false results in a "MethodNotFound" error.
}
}