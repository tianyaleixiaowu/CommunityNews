package com.tianyalei.communitynews.utils.http;

import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.http.wolf.GetDataCallBack;
import com.tianyalei.communitynews.application.MyApplication;

import java.util.HashMap;

/**
 * 网络工具类
 * @author wuwf
 *
 */
public class HttpUtil {
	/**
	 * 发送网络请求的方法
	 */
	public static void sendRequest(int requestCode, HashMap<String, String> map,
			final GetDataCallBack call) {
		String url = getUrl(requestCode, map);
		if(map == null) {
			send(isGet(requestCode), url, null, call);
			return;
		}
		AbRequestParams params = new AbRequestParams(map);
		send(isGet(requestCode), url, params, call);
	}
	
	/**
	 * 真正的请求
	 */
	private static void send(boolean get, String url, AbRequestParams params,
			final GetDataCallBack call) {
		AbHttpUtil abHttpUtil  = AbHttpUtil.getInstance(MyApplication.getInstance());
		ResponseListener listener = new ResponseListener(call);
		// 如果是get请求
		if (get) {
			//如果无参数
			if(params == null) {
				abHttpUtil.get(url, listener);
				return;
			}
			abHttpUtil.get(url, params, listener);
			return;
		}
 
		
		// post请求
		if(params == null) {
			abHttpUtil.post(url, listener);
			return;
		}
		
		abHttpUtil.post(url, params, listener);
	}

	/**
	 * 根据请求码获取url地址
	 */
	private static String getUrl(int requestCode, HashMap<String, String> map) {
		String url = null;
		switch (requestCode) {
		case 90:
			url = "http://shequproject.duapp.com/pushs/api/sendnotice.php";
			break;
		case 100:
			url = "http://shequproject.duapp.com/pushs/api/inituser.php?";
			break;
		default:
			break;
		}

		return url;
	}
	
	/**
	 * 如果请求码是1XX则是get请求，如果是2XX则是post请求
	 */
	private static boolean isGet(int requestCode) {
		if (requestCode >= 100 && requestCode < 200) {
			return true;
		}
		return false;
	}
	
	private static class ResponseListener extends AbStringHttpResponseListener {
		private GetDataCallBack call;
		public ResponseListener(GetDataCallBack call) {
			this.call = call;
		}
		
		@Override
		public void onStart() {
			if(call != null)
				call.start();
		}

		@Override
		public void onFinish() {
			if(call != null)
				call.finish();
		}

		@Override
		public void onFailure(int statusCode, String content,
				Throwable error) {
			if(call != null)
				call.failure();
		}


		@Override
		public void onSuccess(int statusCode, String content) {
			if(call != null)
				call.success(content);
			
		}
	}
}
