package com.common.view.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

public class Utils {

	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int[] getDeviceSize(Context context) {
		int[] size = new int[2];

		int measuredWidth = 0;
		int measuredHeight = 0;
		Point point = new Point();
		WindowManager wm = ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE));

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			wm.getDefaultDisplay().getSize(point);
			measuredWidth = point.x;
			measuredHeight = point.y;
		} else {
			DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
			measuredWidth = dm.widthPixels;
			measuredHeight = dm.heightPixels;
		}

		size[0] = measuredWidth;
		size[1] = measuredHeight;
		return size;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp(Context context, int value) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value,
				context.getResources().getDisplayMetrics());
	}

	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		return w_screen;
	}

	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		int h_screen = dm.heightPixels;
		return h_screen;
	}

	public static int getDeviceWidth(Context context) {
		return getDeviceSize(context)[0];
	}

	public static int getDeviceHeight(Context context) {
		return getDeviceSize(context)[1];
	}

	public static float getDensity(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
		wm.getDefaultDisplay().getMetrics(metrics);

		return metrics.density;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@SuppressLint("NewApi")
	public static int getSoftButtonsBarHeight(Activity context) {
		// getRealMetrics is only available with API 17 and +
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			DisplayMetrics metrics = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int usableHeight = metrics.heightPixels;
			context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
			int realHeight = metrics.heightPixels;
			if (realHeight > usableHeight)
				return realHeight - usableHeight;
			else
				return 0;
		}
		return 0;
	}

	public static boolean isPackageExisted(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
		}
		return packageInfo != null;
	}

	public static void openApp(Context context, String packageName) throws NameNotFoundException {
		PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);

		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);

		List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

		Iterator<ResolveInfo> iterator = apps.iterator();
		while (iterator.hasNext()) {
			ResolveInfo ri = iterator.next();
			if (ri != null) {
				packageName = ri.activityInfo.packageName;
				String className = ri.activityInfo.name;
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				ComponentName cn = new ComponentName(packageName, className);
				intent.setComponent(cn);
				context.startActivity(intent);
			}
		}
	}

	public static List<PackageInfo> getNonSystemApps(Context context) {
		List<PackageInfo> list = context.getPackageManager().getInstalledPackages(0);
		List<PackageInfo> systemApps = new ArrayList<PackageInfo>();
		for (PackageInfo info : list) {
			if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
				systemApps.add(info);
			}
		}
		list.removeAll(systemApps);
		return list;
	}

	public static void showToast(Context context, int id) {
		Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getResources().getString(id),
				Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String string) {
		if (!TextUtils.isEmpty(string)) {
			Toast.makeText(context.getApplicationContext(), string, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showToastLong(Context context, int id) {
		Toast.makeText(context.getApplicationContext(), context.getApplicationContext().getResources().getString(id),
				Toast.LENGTH_LONG).show();
	}

	public static void showToastLong(Context context, String string) {
		if (!TextUtils.isEmpty(string)) {
			Toast.makeText(context.getApplicationContext(), string, Toast.LENGTH_LONG).show();
		}
	}

	public static void showToast(Context context, String string, long duration) {
		final Toast toast = Toast.makeText(context.getApplicationContext(), string, Toast.LENGTH_SHORT);
		toast.show();
		new CountDownTimer(duration, 1000) {
			public void onTick(long millisUntilFinished) {
				toast.show();
			}

			public void onFinish() {
				toast.show();
			}

		}.start();
	}

	public static int getVersionCode(Context context) {
		int versionCode = Integer.MAX_VALUE;
		try {
			versionCode = context.getApplicationContext().getPackageManager()
					.getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static String getVersionName(Context context) {
		try {
			String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			return versionName;
		} catch (Exception e) {
		}
		return "Unknown";
	}

	public static Map<String, Object> buildMap(Object... keyValues) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (int i = 0; i < keyValues.length; i += 2) {
			resultMap.put((String) keyValues[i], keyValues[i + 1]);
		}
		return resultMap;
	}

	@SuppressLint("MissingPermission")
	public static String getMacAddress(Context context) {
		WifiManager wimanager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

		String macAddress = null;
		try {
			macAddress = wimanager.getConnectionInfo().getMacAddress();
		} catch (Exception e) {
		}

		if (TextUtils.isEmpty(macAddress)) {
			macAddress = "000000000000";
		} else {
			macAddress = macAddress.replace(":", "");
		}

		return macAddress;
	}

	public static String getBaiduMacAddress(Context context) {
		WifiManager wimanager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

		String macAddress = null;
		try {
			macAddress = wimanager.getConnectionInfo().getMacAddress();
		} catch (Exception e) {
		}

		if (TextUtils.isEmpty(macAddress)) {
			macAddress = "000000000000";
		}

		return macAddress;
	}

	public static String getAndroidId(Context context) {
		String androidId = null;
		try {
			ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
			androidId = Secure.getString(contentResolver, Secure.ANDROID_ID);
		} catch (Exception e) {
		}
		if (TextUtils.isEmpty(androidId)) {
			androidId = "0";
		}

		return androidId;
	}

	public static String convertByte(long total) {
		if (total < 1024) {
			return total + " B";
		} else if (total < 1024 * 1024) {
			return total / 1024 + " KB";
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			return nf.format(total / 1024 / 1024.0) + " MB";
		}
	}

	public static String convertSpeed(long total) {
		if (total < 1024) {
			return total + " B/s";
		} else if (total < 1024 * 1024) {
			return total / 1024 + " KB/s";
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			return nf.format(total / 1024 / 1024.0) + " MB/s";
		}
	}

	public static String convertByte(Long total) {
		if (total == null) {
			return "0 MB";
		}
		return convertByte(total.longValue());
	}

	public static String convertByteToKiloByte(long total) {
		return total / 1024 + "KB";
	}

	public static boolean isMIUI(Context paramContext) {
		if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
			return true;
		}
		return false;
	}

	public static boolean isMIUI_V5(Context paramContext) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (isMIUI(paramContext)) && ("V5".equalsIgnoreCase(properties.getProperty("ro.miui.ui.version.name")));
	}

	public static boolean isMIUI_V6(Context paramContext) {
		Properties properties = new Properties();
		int miui = 0;
		try {
			properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (properties.getProperty("ro.miui.ui.version.name") != null) {
			try {
				miui = Integer.parseInt(properties.getProperty("ro.miui.ui.version.name").substring(1));
			} catch (Exception e) {
			}
		}
		return (isMIUI(paramContext)) && (miui > 5);
	}

	public static String getAppChannel(Context context) {
		String appChannel = "";
		try {
			Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA).metaData;
			appChannel = (String) metaData.get("UMENG_CHANNEL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return appChannel != null ? appChannel : "";
	}

	public static void startApkInstall(Context context, String path) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + path), "com/library/application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try {
			context.startActivity(i);
		} catch (Exception re) {
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressWarnings("deprecation")
	public static void copyToClipboard(Context context, String content) {
		try {
			if (Build.VERSION.SDK_INT > 11) {
				android.content.ClipboardManager cmb = (android.content.ClipboardManager) context
						.getSystemService(Context.CLIPBOARD_SERVICE);
				cmb.setPrimaryClip(ClipData.newPlainText(null, content));
			} else {
				android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
						.getSystemService(Context.CLIPBOARD_SERVICE);
				cmb.setText(content);
			}
		} catch (Exception e) {
		}
	}

	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll("：", ":");
		String regEx = "[『』]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static void startActivityForBrowser(Context context, String url) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URIUtils.appendHttpPrefix(url)));
		try {
			context.startActivity(intent);
		} catch (Exception e) {
		}
	}

	public static void startActivityForUrl(Context context, boolean isAction, String urlOrAction, String exceptionMsg) {
		Intent intent;
		if (!isAction) {
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlOrAction));
		} else {
			intent = new Intent(urlOrAction);
		}
		try {
			context.startActivity(intent);
		} catch (Exception e) {
			Utils.showToast(context, exceptionMsg);
		}
	}

	public static long calculateDiskCacheSize(File paramFile) {
		long l1 = 20000000L;
		try {
			StatFs statFs = new StatFs(paramFile.getAbsolutePath());
			long l2 = statFs.getBlockCount() * statFs.getBlockSize() / 50L;
			l1 = l2;
		} catch (IllegalArgumentException e) {
		}
		return Math.max(Math.min(l1, 200000000L), 20000000L);
	}

	public static void setViewVisibility(View view, int visibility) {
		if (view != null && view.getVisibility() != visibility) {
			view.setVisibility(visibility);
		}
	}

	public static boolean isVersionEqualOrNewerThan(int version) {
		return Build.VERSION.SDK_INT >= version;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void setBackground(View view, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;

	}

	private static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	public static boolean personIdValidation(String text) {
		String regx = "[0-9]{17}x";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		String regX = "[0-9]{17}X";
		return text.matches(regx) || text.matches(reg1) || text.matches(regex) || text.matches(regX);
	}

	public static boolean checkPhone(String phone) {
		Pattern pattern = Pattern.compile("^(13[0-9]|15[0-9]|17[0-9]|15[6-9]|18[0|2|3|5-9])\\d{8}$");
		Matcher matcher = pattern.matcher(phone);

		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public static boolean simpleCheckPhone(String phone) {
		if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
			return true;
		}
		return false;
	}

	public static Boolean isEmail(String str) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(str);
		return matcher.find();
	}

	public static boolean isClassMember(String key, Class c) {
		if (TextUtils.isEmpty(key)) {
			return false;
		}
		Field[] fs = c.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			f.setAccessible(true);
			try {
				if (key.equals(f.getName())) {
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	public static boolean isForeground(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		ActivityManager manager = (ActivityManager) context.getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
		if (tasks == null || tasks.isEmpty()) {
			return false;
		} else {
			return packageName.equals(tasks.get(0).topActivity.getPackageName());
		}
	}

	public static boolean colorEqualExcludeAlpha(int c1, int c2) {
		return Color.red(c1) == Color.red(c2) && Color.blue(c1) == Color.blue(c2) && Color.green(c1) == Color.green(c2);
	}

	public static boolean isAccessibilitySettingsOn(Context context, String serviceName) {
		try {
			int accessibilityEnabled = 0;
			final String service = context.getPackageName() + "/" + serviceName;
			accessibilityEnabled = Secure.getInt(context.getApplicationContext().getContentResolver(),
					Secure.ACCESSIBILITY_ENABLED);
			TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

			if (accessibilityEnabled == 1) {
				String settingValue = Secure.getString(context.getApplicationContext().getContentResolver(),
						Secure.ENABLED_ACCESSIBILITY_SERVICES);
				if (settingValue != null) {
					mStringColonSplitter.setString(settingValue);
					while (mStringColonSplitter.hasNext()) {
						String accessibilityService = mStringColonSplitter.next();
						if (accessibilityService.equalsIgnoreCase(service)) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean checkServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(Integer.MAX_VALUE);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}

	public static boolean isComponentDisenabled(Context context, Class cls) {
		final PackageManager pm = context.getApplicationContext().getPackageManager();
		return pm.getComponentEnabledSetting(new ComponentName(context.getApplicationContext(),
				cls)) != PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
	}

	public static boolean isComponentEnabled(Context context, Class cls) {
		final PackageManager pm = context.getApplicationContext().getPackageManager();
		return pm.getComponentEnabledSetting(new ComponentName(context.getApplicationContext(),
				cls)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
	}

	public static void setComponentEnable(Context context, Class cls) {
		final PackageManager pm = context.getApplicationContext().getPackageManager();
		pm.setComponentEnabledSetting(new ComponentName(context.getApplicationContext(), cls),
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	}

	public static String toURLEncoded(String paramString) {
		if (paramString == null || paramString.equals("")) {
			return "";
		}
		try {
			String str = new String(paramString.getBytes(), "UTF-8");
			str = URLEncoder.encode(str, "UTF-8");
			return str;
		} catch (Exception localException) {
		}
		return "";
	}

	public static String getNetIp() {
		URL infoUrl = null;
		InputStream inStream = null;
		try {
			infoUrl = new URL("http://www.ip-api.com/json");
			URLConnection connection = infoUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = httpConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
				StringBuilder strber = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null)
					strber.append(line + "\n");
				inStream.close();
				String response = strber.toString();
				org.json.JSONObject jsonObject = new org.json.JSONObject(response);
				if (jsonObject != null) {
					return jsonObject.getString("query");
				}
				return "";
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static boolean isUsageAccessGranted(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
			AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
			int mode = 0;
			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
				mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid,
						applicationInfo.packageName);
			}
			return (mode == AppOpsManager.MODE_ALLOWED);

		} catch (Throwable e) {
			return false;
		}
	}

	@SuppressLint("MissingPermission")
	public static String getSimIccId(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) { // 大于等于Android
			// 5.1.0
			// L版本
			SubscriptionManager sub = (SubscriptionManager) context
					.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
			List<SubscriptionInfo> info = sub.getActiveSubscriptionInfoList();
			int count = sub.getActiveSubscriptionInfoCount();
			if (count > 0) {
				if (count > 1) {
					String icc1 = info.get(0).getIccId();
					String icc2 = info.get(1).getIccId();
					return icc1 + "," + icc2;
				} else {
					for (SubscriptionInfo list : info) {
						String icc1 = list.getIccId();
						return icc1;
					}
				}
			} else {
				return "";
			}
		} else {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
			return tm.getSimSerialNumber();
		}
		return "";
	}

	@TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
	public static boolean isScreenOn(Context context) {
		boolean result = false;
		try {
			if (context != null) {
				if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
					PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
					return pm.isInteractive();
				} else {
					DisplayManager dm = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
					for (Display display : dm.getDisplays()) {
						if (display.getState() != Display.STATE_OFF) {
							return true;
						}
					}
					return false;
				}
			}
		} catch (Throwable e) {
			result = true;
		}
		return result;
	}

	public static Spanned fromHtml(String source) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
		} else {
			return Html.fromHtml(source);
		}
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public static boolean checkTelephonySim(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
		@SuppressLint("MissingPermission")
		String simSer = telephonyManager.getSimSerialNumber();
		if (simSer == null || simSer.equals("")) {
			return false;
		}
		return true;
	}

	public static String getIPAddress(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager == null)
			return "";
		@SuppressLint("MissingPermission")
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info == null || !info.isConnected())
			return "";
		if (info != null && info.isConnected()) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {// 当前使用2G/3G/4G网络
				try {
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
							.hasMoreElements();) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
								.hasMoreElements();) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress();
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}

			} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {// 当前使用无线网络
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());// 得到IPV4地址
				return ipAddress;
			}
		} else {
		}
		return null;
	}

	public static String intIP2StringIP(int ip) {
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
	}

	public static boolean isDoubleOpen(Context context) {
		String path = context.getApplicationContext().getFilesDir().getAbsolutePath();
		List<String> list = new ArrayList<>();
		list.add("(/data/user/0/com.huaqian/files|/data/data/com.huaqian/files)");
		for (String filePath : list) {
			Pattern pattern = Pattern.compile(filePath);
			if (pattern.matcher(path).matches()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isActivityAlive(Activity activity) {
		if (activity == null || activity.isFinishing()) {
			return false;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (activity.isDestroyed()) {
				return false;
			}
		}
		return true;
	}
}
