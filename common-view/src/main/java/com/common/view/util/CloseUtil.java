package com.common.view.util;

import java.io.Closeable;
import java.net.HttpURLConnection;
import java.sql.Connection;

public class CloseUtil {

	public static void closeQuietly(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	public static void closeQuietly(HttpURLConnection httpUrlConnection) {
		if (httpUrlConnection != null) {
			try {
				httpUrlConnection.disconnect();
			} catch (Exception e) {
			}
		}
	}

	public static void closeQuietly(Closeable target) {
		if (target != null) {
			try {
				target.close();
			} catch (Exception e) {
			}
		}
	}
}
