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
import java.util.Timer;

import android.util.Log;

public class socket extends CordovaPlugin {
	static OutputStream out = null;
	BufferedReader in = null;
	String ip_addr;
	int ip_port;
	Socket newsocket;
	boolean mstop;
	public byte[] send_Buffer = new byte[512];
	static int send_length;
	String content = null, msg_send;
	List<String> Recv_msgs_list = new ArrayList<String>();
	private static boolean socketclose = false; // 关闭连接标志位，true表示关闭，false表示连接
	public static Timer Stimer = null, Rtimer = null;
	public static boolean sendError = false;
	public static int SendInteval = 10000;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if (action.equals("connect")) {
			ip_addr = args.getString(0);
			ip_port = args.getInt(1);

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
			if(send_length % 2 == 1) {
				msg_send += "0";
			}
			for (int i = 0, j = 0; i < send_length; i = i + 2, j++) {
				String substr = msg_send.substring(i, i + 2);
				send_Buffer[j] = (byte) Integer.parseInt(substr, 16);
			}
			send_length = send_length / 2;

			this.socket_send(callbackContext);
			return true;
		} else if (action.equals("rollpoling")) {
			int showstatus = args.getInt(0);
			if(socketclose && (showstatus == 1))
			{
				Log.w("socket", "连接从正常变为异常");
				callbackContext.success("00");
				return true;
			}
			else if(!socketclose && (showstatus == 0))
			{
				Log.w("socket", "连接从异常变为正常");
				callbackContext.success("11");
				return true;
			}
			else if (Recv_msgs_list.size() > 0) {
				Log.w("socket", "有数据上传");
				String msg = (String) Recv_msgs_list.get(0);
				callbackContext.success(msg);
				Recv_msgs_list.remove(0);
				return true;
			}
		} else if (action.equals("hanzi")) {

			String hz = args.getString(0);
			Log.w("hanzi", hz);
			int hz_len = hz.length();
			int hz_effictive_len = 32;
			byte[] hz_Buffer = new byte[32];
			for (int i = 0, j = 0; i < hz_len; i = i + 2, j++) {
				String substr = hz.substring(i, i + 2);
				if ((substr.equals("00")) || (substr.equals("FF"))
						|| (substr.equals("ff"))) {
					hz_effictive_len = j;
					break;
				}
				hz_Buffer[j] = (byte) Integer.parseInt(substr, 16);

			}

			String HZ_str;
			if (hz_effictive_len > 0) {
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
					Log.e("socket", ip_addr + " " + ip_port);
					newsocket = new Socket(ip_addr, ip_port);
					newsocket.setKeepAlive(true);// 开启保持活动状态的套接字
					out = newsocket.getOutputStream();
					in = new BufferedReader(new InputStreamReader(newsocket
							.getInputStream()));
					// out.write(("i am phone").getBytes("utf-8"));
					mstop = true;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					newsocket = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					newsocket = null;
				}
				if (newsocket != null) {
					callbackContext.success();
					Stimer = new Timer();
					Rtimer = null;
					Stimer.schedule(new MyTask(), 1000, SendInteval);
					char[] buf = new char[4096];
					while (mstop) {
						if (!socketclose) {
							try {
								if (in.ready()) {
									int len = in.read(buf);
									if (len > 0) {
										content = "";
										for (int i = 0; i < len; i++) {
											content += buf[i];
										}
										if (content.equals("AAAA")) {

										} else {
											Recv_msgs_list.add(content);
										}
										if (Rtimer != null) { //接收到任何数据都要将接收计时器关闭
											Rtimer.cancel();
											Rtimer = null;
										}
										Log.e("socket", content);
									}
								} else {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Log.e("SocketError", "回复超时，主动关闭连接");
							try {
								in.close();
								out.close();
								if (newsocket != null) {
									newsocket.close();
								}
						
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							try {
								newsocket = null;
								Log.e("SocketError", "重新连接……");
								newsocket = new Socket(ip_addr, ip_port);
								newsocket.setKeepAlive(true);// 开启保持活动状态的套接字
								out = newsocket.getOutputStream();
								in = new BufferedReader(new InputStreamReader(
										newsocket.getInputStream()));
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (newsocket != null) {
								Log.e("SocketError", "重新连接成功");
								socketclose = false;
								Rtimer = null;
								Stimer = new Timer();
								Stimer.schedule(new MyTask(), 1000, SendInteval);
							} else {
								Log.e("SocketError", "重新连接失败");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					Log.e("socket", "mstop is false ");

					try {
						if (newsocket != null) {
							newsocket.close();
							in.close();
							out.close();
							newsocket = null;
						}
						if (Rtimer != null) {
							Rtimer.cancel();
							Rtimer = null;
						}
						if (Stimer != null) {
							Stimer.cancel();
							Stimer = null;
						}
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
					if(Stimer != null)
					{
						Stimer.cancel();
						Stimer = new Timer();
						Stimer.schedule(new MyTask(), SendInteval, SendInteval);
					}
					if (Rtimer == null) {
						Log.w("send", "send 有效数据 !");
						Rtimer = new Timer();
						Rtimer.schedule(new RTask(), SendInteval * 2);
					}
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

	static class MyTask extends java.util.TimerTask {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				out.write(("AAAA").getBytes("utf-8"));
				sendError = false;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				sendError = true;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				sendError = true;
				e.printStackTrace();
			}
			if (sendError) {
				socketclose = true;
				Log.w("send", "send error !");
				if(Stimer != null)
				{
					Stimer.cancel();
					Stimer = null;
				}
				if (Rtimer != null) {
					Rtimer.cancel();
					Rtimer = null;
				}
			} else {
				Log.w("send", "send SUCCESS !");
				if (Rtimer == null) {
					Log.w("send", "send 222 !");
					Rtimer = new Timer();
					Rtimer.schedule(new RTask(), SendInteval * 2);
				}
			}
		}
	}

	static class RTask extends java.util.TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Rtimer = null;
			Log.w("recv", "recv timeout !");
			socketclose = true;
			if (Stimer != null) {
				Stimer.cancel();
				Stimer = null;
			}
		}
	}
}
