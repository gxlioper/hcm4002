package com.hjw.util;

public class XORBinary {
	// define a binary instance


	public static String XOR(String mes, int length) {
		String res = "";
		int m = 0;
		int m1 = Integer.parseInt(mes.substring(0, 2), 16);
		int m2;
		for (int i = 1; i < length; i++) {
			m2 = Integer.parseInt(mes.substring(i * 2, i * 2 + 2), 16);
			m1 ^= m2;
		}

		String s = Integer.toHexString(m1);
		if (s.length() < 2)
			s = "0" + "" + s;
		res = s;
		return res.toUpperCase();
	}

}