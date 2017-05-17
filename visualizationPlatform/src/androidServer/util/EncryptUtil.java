/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.cloopen.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package androidServer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtil {

	private static final String UTF8 = "utf-8";

	/**
	 * MD5数字签名
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public String md5Digest(String src) throws Exception {
		// 定义数字签名方法, 可用：MD5, SHA-1
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(src.getBytes(UTF8));
		return this.byte2HexStr(b);
	}

	/**
	 * BASE64编码
	 * 
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public String base64Encoder(String src) throws Exception {
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(src.getBytes(UTF8));
	}

	/**
	 * BASE64解码
	 * 
	 * @param dest
	 * @return
	 * @throws Exception
	 */
	public String base64Decoder(String dest) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		return new String(decoder.decodeBuffer(dest), UTF8);
	}

	/**
	 * 字节数组转化为大�?16进制字符�?
	 * 
	 * @param b
	 * @return
	 */
	private String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s.toUpperCase());
		}
		return sb.toString();
	}
	
	
	
	
	
	
	public static String hexString(byte[] bytes) {
		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			int val = ((int) bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static byte[] eccrypt(String info) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] srcBytes = info.getBytes();
		// 使用srcBytes更新摘要
		md5.update(srcBytes);
		// 完成哈希计算，得到result
		byte[] resultBytes = md5.digest();
		return resultBytes;
	}


	public static String getNewFileName(){
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Random rand = new Random();
		//int tmp = Math.abs(rand.nextInt());
		int nextInt = Math.abs(rand.nextInt()) % 99999;
		String timeName = sdf.format(dt) + nextInt;
		byte[] resultBytes;
		try {
			resultBytes = EncryptUtil.eccrypt(timeName);
			String fileName = EncryptUtil.hexString(resultBytes);
			return fileName;
//			System.out.println("密文是：" + EncryptUtil.hexString(resultBytes));
//			System.out.println(fileName.length());

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
