package com.inpassing.helper;

/**
 * Created by zw on 2018/9/17.
 */

public class JsoupHtmlAnalysisHelper {
	private String PATH_URL = null;

	public enum JsoupType {
		DEFAULT, VIDEO, SALL_VIDEO
	}

	public String pathUrl(JsoupType jsoupType) {
		switch (jsoupType) {
			case DEFAULT:
				return PATH_URL;
			case VIDEO:
				return PATH_URL = "http://www.dingzibd.com/tv/list.html";
			case SALL_VIDEO:
				return PATH_URL = "http://204.44.66.96:9999/avt/cate";
		}
		return PATH_URL;
	}
}
