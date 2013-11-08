package org.wdc.socket;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class socket extends CordovaPlugin {
	OutputStream out = null;
	BufferedReader in = null;
	Socket newsocket;
	boolean mstop;
	public static byte[] send_Buffer = new byte[512];
	int send_length ;
	String content = null, msg_send;
	List<String> Recv_msgs_list = new ArrayList<String>();

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if (action.equals("connect")) {
			this.socket_connect(callbackContext);
			return true;
		} else if (action.equals("close")) {
			Log.w("socket", "socket will close");
			mstop = false;
			callbackContext.success();
			return true;
		} else if (action.equals("send")) {
			msg_send = args.getString(0);
			send_length = msg_send.length();
			if(send_length%2 == 1)
			{
				msg_send +="0";
			}
			for(int i = 0,j = 0;i < send_length;i = i+2,j++)
			{
					String substr = msg_send.substring(i, i+2);
					send_Buffer[j] = (byte) Integer.parseInt(substr,16);
			}
			send_length = send_length/2;	
			
			this.socket_send(callbackContext);
			return true;
		} else if (action.equals("rollpoling")) {
			//Log.w("socket", "socket poll111111111111");
			if (Recv_msgs_list.size() > 0) {
				Log.w("socket", "socket poll222222222222");
				String msg = (String) Recv_msgs_list.get(0);
				callbackContext.success(msg);
				Recv_msgs_list.remove(0);
				return true;
			}
		}else if (action.equals("hanzi")) {
			
			String hz  = args.getString(0);
			Log.w("hanzi", hz);
			int hz_len = hz.length();
			int hz_effictive_len = 32;
			byte[] hz_Buffer = new byte[32];
			for(int i = 0,j = 0;i < hz_len;i = i+2,j++)
			{
					String substr = hz.substring(i, i+2);
					if((substr.equals("00")) || (substr.equals("FF")) || (substr.equals("ff")))
					{
						hz_effictive_len = j;
						break;
					}
					hz_Buffer[j] = (byte) Integer.parseInt(substr,16);
					
			}
			
			String HZ_str;
			if(hz_effictive_len > 0)
			{
				byte hzs[] = new byte[hz_effictive_len];
				for (int j = 0; j < hz_effictive_len; j++) {
					hzs[j] = hz_Buffer[j];
				}
				try {
					HZ_str = new String(hzs, "GB2312");
					callbackContext.success(HZ_str);
					return true;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		callbackContext.error("false");
		return false; // Returning false results in a "MethodNotFound" error.
	}

	public void socket_connect(final CallbackContext callbackContext) {
		this.cordova.getThreadPool().execute(new Runnable() {

			public void run() {
				try {
					newsocket = null;
					newsocket = new Socket("219.147.26.62", 2047);
					out = newsocket.getOutputStream();
					in = new BufferedReader(new InputStreamReader(newsocket
							.getInputStream()));
					out.write(("i am phone").getBytes("utf-8"));
					mstop = true;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callbackContext.error("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					callbackContext.error("");
				}
				if (newsocket != null) {
					callbackContext.success();
					char[] buf = new char[4096];
					while (mstop) {
						try {
							if(in.ready())
							{
								int len = in.read(buf);
								if(len > 0)
								{
									content = "";
									for(int i = 0;i<len;i++)
									{
										content += buf[i];
									}
									Log.e("socket", content);
									Recv_msgs_list.add(content);
								}
							}
							else
							{
								try {
									Thread.sleep(200);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							/*
							while ((content = in.readLine()) != null) {
								Log.e("socket", content);
								Recv_msgs_list.add(content);
							}*/
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					Log.e("socket", "mstop is false ");

					try {
						in.close();
						out.close();
						newsocket.close();
						newsocket = null;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					callbackContext.error("");
				}

			}
		});
	}

	public void socket_send(final CallbackContext callbackContext) {
		this.cordova.getThreadPool().execute(new Runnable() {
			public void run() {
				try {
					out.write(send_Buffer, 0, send_length);
					callbackContext.success();
					return;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				callbackContext.error(0);
			}
		});
	}
}
