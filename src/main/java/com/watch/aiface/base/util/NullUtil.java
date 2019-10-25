package com.watch.aiface.base.util;

/**
 * 判断是否为空的封装类
 *
 */
public class NullUtil {


	public static boolean isBlank(Object object) {
		if (object == null || "".equals(object.toString().trim()) || "null".equals(object)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为NULL
	 * 
	 * @param obj
	 * @return
	 */
	public static final boolean isNull(Object obj) {
		if (null == obj) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为非NULL
	 * 
	 * @param obj
	 * @return
	 */
	public static final boolean isNotNull(Object obj) {
		if (null != obj) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static final boolean isEmpty(Object obj) {
		if (null == obj || "".equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static final boolean isNullOrEmpty(Object obj) {
		if (null == obj || "".equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为非空
	 * 
	 * @param obj
	 * @return
	 */
	public static final boolean isNotEmpty(Object obj) {
		if (null != obj && !"".equals(obj) && !"null".equals(obj)) {
			return true;
		} else {
			return false;
		}
	}

}
