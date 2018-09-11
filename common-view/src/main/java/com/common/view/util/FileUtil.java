package com.common.view.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

	public static void deleteFile(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			cleanDir(file);
		}
		file.delete();
	}

	public static void cleanDir(File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] files = dir.listFiles();
		if (files != null) {
			for (File file : files) {
				deleteFile(file);
			}
		}
	}

	public static void moveFile(File srcFile, File destFile) {
		moveFile(srcFile, destFile, false);
	}

	public static void moveFile(File srcFile, File destFile, boolean override) {
		if (!srcFile.exists()) {
			return;
		}
		if (override && destFile.exists()) {
			deleteFile(destFile);
		} else {
			srcFile.renameTo(destFile);
		}
	}

	public static void copyFile(File srcFile, File destFile) throws IOException {
		if (!srcFile.exists()) {
			return;
		}
		File parentFile = destFile.getParentFile();
		if (parentFile != null) {
			parentFile.mkdirs();
		}

		InputStream in = new FileInputStream(srcFile);
		OutputStream out = new FileOutputStream(destFile);
		try {
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} finally {
			CloseUtil.closeQuietly(in);
			CloseUtil.closeQuietly(out);
		}
	}

	public static void touchFile(File file) throws IOException {
		if (!file.exists()) {
			if (file.isDirectory()) {
				file.mkdirs();
			} else {
				File parentFile = file.getParentFile();
				if (parentFile != null) {
					parentFile.mkdirs();
				}
				file.createNewFile();
			}
		}
		file.setLastModified(System.currentTimeMillis());
	}

	public static File[] listFiles(File dir) {
		if (dir.exists()) {
			return dir.listFiles();
		}
		return null;
	}

	public static byte[] readFileToByteArray(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		try {
			return ByteStreams.toByteArray(in);
		} finally {
			CloseUtil.closeQuietly(in);
		}
	}

	public static String readFileToString(File file) throws IOException {
		byte[] bytes = readFileToByteArray(file);
		if (bytes == null) {
			return null;
		}
		return new String(bytes, "UTF-8");
	}

	public static void writeInputStreamToFile(File file, InputStream in) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		try {
			ByteStreams.copy(in, out);
		} finally {
			CloseUtil.closeQuietly(out);
		}
	}

	public static void writeByteArrayToFile(File file, byte[] bytes) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		try {
			out.write(bytes);
		} finally {
			CloseUtil.closeQuietly(out);
		}
	}

	public static void writeStringToFile(File file, String str) throws IOException {
		writeByteArrayToFile(file, str.getBytes("UTF-8"));
	}
}
