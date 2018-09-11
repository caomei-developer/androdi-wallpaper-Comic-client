package com.common.view.util;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.Map;

public class URIUtils {

	public static String appendParams(String targetUri, Map<String, String> paramMap) throws Exception {
		if (paramMap == null || paramMap.isEmpty()) {
			return targetUri;
		}
		int paramIndex = targetUri.indexOf('?');
		int hashStartIndex = targetUri.indexOf('#');
		String serverPath = targetUri;
		String params = "";
		String hash = "";
		if (paramIndex >= 0) {
			serverPath = targetUri.substring(0, paramIndex);
			if (hashStartIndex >= 0) {
				params = targetUri.substring(paramIndex + 1, hashStartIndex);
				hash = targetUri.substring(hashStartIndex + 1);
			} else {
				params = targetUri.substring(paramIndex + 1);
			}
		} else if (hashStartIndex >= 0) {
			serverPath = targetUri.substring(0, hashStartIndex);
			hash = targetUri.substring(hashStartIndex + 1);
		}
		StringBuffer resultBuffer = new StringBuffer();
		if (!TextUtils.isEmpty(serverPath)) {
			resultBuffer.append(serverPath);
		}
		resultBuffer.append("?");
		if (!TextUtils.isEmpty(params)) {
			resultBuffer.append(params);
		}
		if (!TextUtils.isEmpty(params)) {
			resultBuffer.append("&");
		}
		int index = 0;
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			if (index != 0) {
				resultBuffer.append("&");
			}
			resultBuffer.append(entry.getKey()).append("=").append(entry.getValue());
			index++;
		}
		if (!TextUtils.isEmpty(hash)) {
			resultBuffer.append("#").append(hash);
		}
		return resultBuffer.toString();
	}

	public static String appendParams(String targetUri, Map<String, Object> paramMap, String type) throws Exception {
		if (paramMap == null || paramMap.isEmpty()) {
			return targetUri;
		}
		int paramIndex = targetUri.indexOf('?');
		int hashStartIndex = targetUri.indexOf('#');
		String serverPath = targetUri;
		String params = "";
		String hash = "";
		if (paramIndex >= 0) {
			serverPath = targetUri.substring(0, paramIndex);
			if (hashStartIndex >= 0) {
				params = targetUri.substring(paramIndex + 1, hashStartIndex);
				hash = targetUri.substring(hashStartIndex + 1);
			} else {
				params = targetUri.substring(paramIndex + 1);
			}
		} else if (hashStartIndex >= 0) {
			serverPath = targetUri.substring(0, hashStartIndex);
			hash = targetUri.substring(hashStartIndex + 1);
		}
		StringBuffer resultBuffer = new StringBuffer();
		if (!TextUtils.isEmpty(serverPath)) {
			resultBuffer.append(serverPath);
		}
		resultBuffer.append("?");
		if (!TextUtils.isEmpty(params)) {
			resultBuffer.append(params);
		}
		if (!TextUtils.isEmpty(params)) {
			resultBuffer.append("&");
		}
		int index = 0;
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			if (index != 0) {
				resultBuffer.append("&");
			}
			resultBuffer.append(entry.getKey()).append("=")
					.append(URLEncoder.encode((String) entry.getValue(), "utf-8"));
			index++;
		}
		if (!TextUtils.isEmpty(hash)) {
			resultBuffer.append("#").append(hash);
		}
		return resultBuffer.toString();
	}

	public static String appendHttpPrefix(String targetUri) {
		String resultUrl = null;
		if (targetUri.startsWith("http://") || targetUri.startsWith("https://")) {
			resultUrl = targetUri;
		} else {
			resultUrl = "http://" + targetUri;
		}
		return resultUrl;
	}

	public static String getParameter(String url, String key) {
		if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(key)) {
			int questionMarkIndex = url.indexOf("?");
			if (questionMarkIndex >= 0) {
				String paramStr = url.substring(questionMarkIndex + 1);
				if (!TextUtils.isEmpty(paramStr)) {
					String[] paramArr = paramStr.split("&");
					for (String param : paramArr) {
						String[] keyValueArr = param.split("=");
						if (keyValueArr != null && keyValueArr.length == 2 && key.equals(keyValueArr[0])) {
							return keyValueArr[1];
						}
					}
				}
			}

		}
		return null;
	}

}
