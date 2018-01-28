package com.tangdi.production.mpbase.util;

import java.math.BigInteger;

/** 异或 加解密 */
public class EncryptAndDecryptUtil {
	private static final int RADIX = 16;
	private static final String SEED = "0933910847463829232312312";

	public static final String encrypt(String password) {
		if (password == null)
			return "";
		if (password.length() == 0)
			return "";

		BigInteger bi_passwd = new BigInteger(password.getBytes());

		BigInteger bi_r0 = new BigInteger(SEED);
		BigInteger bi_r1 = bi_r0.xor(bi_passwd);

		return bi_r1.toString(RADIX);
	}

	public static final String decrypt(String encrypted) {
		if (encrypted == null)
			return "";
		if (encrypted.length() == 0)
			return "";

		BigInteger bi_confuse = new BigInteger(SEED);

		try {
			BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);

			return new String(bi_r0.toByteArray());
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String args[]) {
		System.out.println(EncryptAndDecryptUtil.encrypt("111111"));
		System.out.println(EncryptAndDecryptUtil.decrypt("c5c36892a1af06cb6ec9"));

	}
}
