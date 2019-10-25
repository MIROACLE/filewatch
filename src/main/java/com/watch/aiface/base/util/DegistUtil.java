package com.watch.aiface.base.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.watch.aiface.base.ai.AI;

/**
 * 加密算法
 * 
 * @author long
 */
public class DegistUtil {

	public static String sha256(String msg) {
		String result = null;
		MessageDigest degist = null;
		try {
			degist = MessageDigest.getInstance("SHA-256");
			byte[] results = degist.digest(msg.getBytes("UTF-8"));
			result = bytes2Hex(results);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String md5(String msg) {
		String result = null;
		MessageDigest degist = null;
		try {
			degist = MessageDigest.getInstance("MD5");
			byte[] results = degist.digest(msg.getBytes("UTF-8"));
			result = bytes2Hex(results);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 验证业务员安全码
	 * 
	 * @param securityCode
	 * @return
	 */
	public static boolean checkSecurityCode(String securityCode) {
		if (securityCode == null) {
			return false;
		}
		String code = DegistUtil.md5(AI.ServiceCode.TF + JodaTimeUtil.dateToStr(new Date(), JodaTimeUtil.FORMAT_YMDH))
				.toUpperCase();
		if (code.equals(securityCode.toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}

	public static String securityCode() {
		String code = DegistUtil.md5(AI.ServiceCode.TF + JodaTimeUtil.dateToStr(new Date(), JodaTimeUtil.FORMAT_YMDH))
				.toUpperCase();
		return code;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des.toUpperCase();
	}

}
