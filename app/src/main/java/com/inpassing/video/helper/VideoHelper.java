package com.inpassing.video.helper;

import com.inpassing.helper.JsoupHtmlAnalysisHelper;
import com.inpassing.video.Video;
import com.lib.bean.JsoupAnalysis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by zw on 2018/9/17.
 */

public class VideoHelper {
	Document document = null;

	public VideoHelper() {
	}

	public void getViewList(final JsoupAnalysis jsoupAnalysis, final VideoCallBack videoCallBack) {

		final String url = new JsoupHtmlAnalysisHelper().pathUrl(JsoupHtmlAnalysisHelper.JsoupType.VIDEO);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					document = Jsoup.connect(url).get();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (document != null) {
					Elements listItem = document.getElementsByClass(jsoupAnalysis.getDiv());
					for (Element item : listItem) {
						Element elements = item.after(jsoupAnalysis.getA());
						String title = elements.attr(jsoupAnalysis.getHref());
					}
					// Elements itemList = item.select(jsoupAnalysis.getItem());

				}

			}
		}).start();
	}

	public interface VideoCallBack {
		void onSuccess(Video video);
	}
}
