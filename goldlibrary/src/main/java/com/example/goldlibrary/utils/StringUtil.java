package com.example.goldlibrary.utils;

/**通用字符串(String)相关类,为null时返回""
 * @use StringUtil.
 */
public class StringUtil {
	private static final String TAG = "StringUtil";

	/**
	 * 获取string,为null则返回""
	 * @param s
	 * @return
	 */
	public static String getString(String s) {
		return s == null ? "" : s;
	}


	/**
	 * 获取去掉前后空格后的string,为null则返回""
	 * @param s
	 * @return
	 */
	public static String getTrimedString(String s) {
		return getString(s).trim();
	}

	public static String getNoBlankString(String s) {
		return getString(s).replaceAll(" ", "");
	}

}
