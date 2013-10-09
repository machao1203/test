package org.wdc.toast;
 
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import android.widget.Toast;

import android.app.AlertDialog;

public class ToastTest extends CordovaPlugin {
    public static final String ACTION_ADD_TOAST_ENTRY = "addToastEntry";
    
   @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (ACTION_ADD_TOAST_ENTRY.equals(action)) {
		this.alert(args.getString(0), args.getString(1), args.getString(2), callbackContext);
        return true;
    }
	callbackContext.error("false");
    return false;  // Returning false results in a "MethodNotFound" error.
}
   /**
	  * Builds and shows a native Android alert with given Strings
	  * @param message			 The message the alert should display
	  * @param title			 The title of the alert
	  * @param buttonLabel		 The label of the button
	  * @param callbackContext	 The callback context
	  */

   public synchronized void alert(final String message, final String title, final String buttonLabel, final CallbackContext callbackContext) {

        final CordovaInterface cordova = this.cordova;

        Runnable runnable = new Runnable() {
            public void run() {

                AlertDialog.Builder dlg = new AlertDialog.Builder(cordova.getActivity());
                dlg.setMessage(message);
                dlg.setTitle(title);
                dlg.setCancelable(true);
                dlg.setPositiveButton(buttonLabel,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, 0));
                            }
                        });
                dlg.setOnCancelListener(new AlertDialog.OnCancelListener() {
                    public void onCancel(DialogInterface dialog)
                    {
                        dialog.dismiss();
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, 0));
                    }
                });

                dlg.create();
                dlg.show();
            };
        };
        this.cordova.getActivity().runOnUiThread(runnable);
    }
}
