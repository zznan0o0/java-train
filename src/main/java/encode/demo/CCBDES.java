package encode.demo;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

//import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
//import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import org.apache.commons.codec.binary.Base64;

public class CCBDES {

	private static final String BASE_KEY = "333705661205A5E3D950933325240713";
	// 加密模式
	private static final String EN_MODE = "DES/ECB/NoPadding";

	private static final String CHAR_SET_GBK = "GB18030";

	@SuppressWarnings("unused")
	private static final String CHAR_SET_UTF8 = "UTF-8";

	public static final String CHAR_SET_GB2312 = "GB18030";

	/**
	 * 加密函数
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            密钥
	 * @return 加密后返回的数据
	 */
	private static byte[] ECBEncrypt(byte[] data, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec desKeySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			Cipher cipher = Cipher.getInstance(EN_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密函数
	 * 
	 * @param data
	 *            解密数据
	 * @param key
	 *            密钥
	 * @return 解密后的数据
	 */
	private static byte[] ECBDecrypt(byte[] data, byte[] key) {
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec desKeySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			Cipher cipher = Cipher.getInstance(EN_MODE);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 3DES加密
	 * 
	 * @param buf
	 *            需要加密的数据
	 * @param key
	 *            密钥
	 * @return
	 */
	private static byte[] encrypt3DESofDouble(byte[] buf, byte[] key) {
		byte[] leftkey = new byte[8];
		byte[] rightkey = new byte[8];
		System.arraycopy(key, 0, leftkey, 0, 8);
		System.arraycopy(key, 8, rightkey, 0, 8);

		return ECBEncrypt(ECBDecrypt(ECBEncrypt(buf, leftkey), rightkey), leftkey);
	}

	/**
	 * 3DES解密
	 * 
	 * @param buf
	 *            需要解密的数据
	 * @param key
	 *            密钥
	 * @return
	 */
	private static byte[] decrypt3DESofDouble(byte[] buf, byte[] key) {
		byte[] leftkey = new byte[8];
		byte[] rightkey = new byte[8];
		System.arraycopy(key, 0, leftkey, 0, 8);
		System.arraycopy(key, 8, rightkey, 0, 8);

		return ECBDecrypt(ECBEncrypt(ECBDecrypt(buf, leftkey), rightkey), leftkey);
	}

	/**
	 * 将16进制字符串字节数组转换成字节数组
	 * 
	 * @param bytes
	 *            16进制字符串字节数组
	 * @param i
	 *            字符串字节数组长度
	 * @return
	 */
	private static byte[] fromHexString(byte[] bytes, int i) {
		int j = 0;
		if (bytes[0] == 48 && (bytes[1] == 120 || bytes[1] == 88)) {
			j += 2;
			i -= 2;
		}
		int k = i / 2;
		byte[] abyte1 = new byte[k];
		for (int l = 0; l < k; l++) {
			abyte1[l] = (byte) ((hexValueOf(bytes[j]) << 4 | hexValueOf(bytes[j + 1])) & 0xff);
			j += 2;
		}

		return abyte1;
	}

	private static int hexValueOf(int i) {
		if (i >= 48 && i <= 57) {
			return i - 48;
		}
		if (i >= 97 && i <= 102) {
			return (i - 97) + 10;
		}
		if (i >= 65 && i <= 70) {
			return (i - 65) + 10;
		} else {
			return 0;
		}

	}

	/**
	 * 将字节数组转换成字符串
	 * 
	 * @param bytes
	 * @return
	 */
	private static String printHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xff);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 获得工作密钥
	 * 
	 * @param workkey
	 * @param baseKey
	 * @return
	 * @throws Base64DecodingException 
	 */
	public static String getWorkKey(String workkey, String baseKey) throws Base64DecodingException {
		// 将字节数组转换成字符串
		String key = printHex(Base64.decodeBase64(workkey));
		// 将16进制字符串字节数组转换成字节数组
		byte[] work_key = fromHexString(key.getBytes(), 32);
		byte[] base_key = fromHexString(baseKey.getBytes(), 32);

		return printHex(decrypt3DESofDouble(work_key, base_key));
		// req: basekey_A + workkey 1->encrypt encrypt ->basekey_B + workkey 1
		// resp: basekeyB+workkey2 ->encrypt ena

	}

	/**
	 * 解密字符串
	 * 
	 * @param str
	 * @param workKey
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Base64DecodingException
	 */
	public static String decryptStr(String str, String workKey)
			throws UnsupportedEncodingException, Base64DecodingException {
		byte[] srcStr = Base64.decodeBase64(str);
		String src = printHex(srcStr);
		int len = src.getBytes().length;
		byte[] srcBytes = decrypt3DESofDouble(fromHexString(src.getBytes(), len),
				fromHexString(workKey.getBytes(), 32));
		String result = new String(srcBytes, CHAR_SET_GBK);
		if (result != null && !"".equals(result)) {
			int i = result.lastIndexOf('>');
			if (i > 0) {
				result = result.substring(0, i + 1);
			}
		}
		return result;
	}

	/**
	 * 加密字符串
	 * 
	 * @param str
	 * @param workKey
	 * @return
	 */
	public static String encryptStr(String str, String workKey) {
		byte[] src = null;
		try {
			src = str.getBytes(CHAR_SET_GBK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int len = src.length;
		byte[] ibyte = new byte[len + 8 - len % 8];
		System.arraycopy(src, 0, ibyte, 0, len);
		byte[] key = fromHexString(workKey.getBytes(), 32);
		byte[] enSrc = encrypt3DESofDouble(ibyte, key);
		String srcStr = printHex(enSrc);
		byte[] srcBytes = fromHexString(srcStr.getBytes(), srcStr.getBytes().length);
		String baseEncodeStr = Base64.encodeBase64String(srcBytes);

		return baseEncodeStr.replaceAll("[\\s*\t\n\r]", "");
	}

	public static void main(String[] args) {
		try {
			String data = "<txCode>11</txCode>\r\n" + "<sendXml>11</sendXml>\r\n" + "<recvXml>11</recvXml>\r\n"
					+ "<txDate>11</txDate>\r\n" + "<txTime>11</txTime>";
			String key = "KVUDAVc0CavCG+lnsTlbWA==";
//			String key = "333705661205A5E3D950933325240713";
			System.out.println("要传送的数据:" + data);
			String workKey = getWorkKey(key, BASE_KEY);
			System.out.println(workKey);
			data = encryptStr(data, workKey);
			System.out.println("加密后的数据：" + data);
			data = decryptStr(data, BASE_KEY);
//			String d = new String(data.getBytes("GB18030"), "UTF-8");
//			String d = new String(data.getBytes("UTF-8"), "GB18030");
//			System.out.println(d);
			System.out.println("解密后的数据：" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
