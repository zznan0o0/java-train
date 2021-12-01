package encode.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSASignatureUtils {

	private static final String KEY_ALGORITHM = "RSA"; // 密钥算法

	// 签名算法：MD5withRSA，MD2withRSA,SHA1WithRSA,SHA256withRSA,SHA384withRSA,SHA512withRSA
	private static final String SIGN_ALGORITHM = "MD5withRSA";

//	public static final String CRT_FILE_NAME = "pub_rsa_oct.crt";
//	public static final String PRI_KEY_NAME = "pkcs8_zhpetro.key";
	public static final String CRT_FILE_NAME = "pub_rsa_oct.crt";
	public static final String PRI_KEY_NAME = "pkcs8_zhpetro.key";

	// public static final String CRT_FILE_NAME="pp_public.crt";

	/**
	 * 加载私钥
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * 生成私钥字符串
	 * 
	 * @return
	 */
	public static String initPrivateKey(KeyPair keyPair) {
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return ((new BASE64Encoder()).encodeBuffer(privateKey.getEncoded()));
	}

	/**
	 * 生成公钥字符串
	 * 
	 * @return
	 */
	public static String initPublicKey(KeyPair keyPair) {
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		return ((new BASE64Encoder()).encodeBuffer(publicKey.getEncoded()));
	}

	/**
	 * 加载公钥
	 * 
	 * @param publicKeyStr
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	/**
	 * 初始化RSA公钥私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static KeyPair initKey() throws Exception {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			keyPairGenerator.initialize(1024);
			return keyPairGenerator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法", e);
		}
	}

	/**
	 * 签名（原数据，私钥） 两要素
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey priKey = keyFactory.generatePrivate(keySpec);

			Signature signature = Signature.getInstance(SIGN_ALGORITHM);
			signature.initSign(priKey);
			signature.update(data);
			return signature.sign();
		} catch (Exception e) {
			throw new Exception("无此算法", e);
		}
	}

	/**
	 * 校验签名（原数据，公钥，签名） 三要素
	 * 
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean valid(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(SIGN_ALGORITHM);
			signature.initVerify(pubKey);
			signature.update(data);
			return signature.verify(sign);
		} catch (Exception e) {
			throw new Exception("无此算法", e);
		}
	}

	/**
	 * @param data     原数据
	 * @param encoding 数据的编码
	 * @return 一个base64的签名
	 * @throws Exception
	 */
	public static String sign(String data, String encoding) throws Exception {
		try {
			PrivateKey privateKey = getPrivateKey();
			byte[] sign = sign(data.getBytes(encoding), privateKey);
			return Base64.encodeBase64String(sign);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("不支持的encoding", e);
		}

	}

	public static boolean valid(String data, String encoding, String sign) throws Exception {
		try {
			PublicKey publicKey = getPublicKey();
			byte[] byteData = data.getBytes(encoding);
			byte[] byteSign = Base64.decodeBase64(sign);
			return valid(byteData, publicKey.getEncoded(), byteSign);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("不支持的encoding", e);
		}
	}

	/**
	 * 从证书文件中读取公钥
	 * 
	 * @param crtPath 文件路径
	 * @return
	 */
	public static RSAPublicKey readPublicKeyFromCry(String crtPath) {
		try {
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			FileInputStream fileInputStream = new FileInputStream(crtPath);
			X509Certificate Cert = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
			PublicKey pKey = Cert.getPublicKey();
			return (RSAPublicKey) pKey;
		} catch (Exception e) {
			throw new RuntimeException("从证书文件中读取公钥失败", e);
		}
	}

	private static PublicKey getPublicKey() throws Exception {
//		System.out.println(RSASignatureUtils.class.getClassLoader().getResource(CRT_FILE_NAME));
//		String crtPath = RSASignatureUtils.class.getClassLoader().getResource(CRT_FILE_NAME).getFile();
//		return readPublicKeyFromCry(crtPath);
		return readPublicKeyFromCry("src/pub_rsa_oct.crt");

//		URL url = RSASignatureUtils.class.getClassLoader().getResource(CRT_FILE_NAME);
//		String libpath =  url.getPath();
//		String crtPath = URLDecoder.decode(libpath, "UTF-8");
//		System.out.println(crtPath);
//		return readPublicKeyFromCry("src/pub_rsa_oct.crt");
//		System.out.println(crtPath);
//		return readPublicKeyFromCry(crtPath);
	}

	private static PrivateKey getPrivateKey() throws Exception {
		String TEST_PRIVATE_KEY = readFile(PRI_KEY_NAME);
		TEST_PRIVATE_KEY = TEST_PRIVATE_KEY.replace("-----BEGIN RSA PRIVATE KEY-----", "")
				.replace("-----END RSA PRIVATE KEY-----", "").replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "");
		return loadPrivateKey(TEST_PRIVATE_KEY);
	}

	public static String readFile(String fileName) throws Exception {
		String path = "src/" + fileName;
		File file = new File(path);
		FileInputStream fStream = null;
		InputStreamReader iReader = null;
		BufferedReader bReader = null;
		try {
			if (file.exists()) {
				fStream = new FileInputStream(file);
				iReader = new InputStreamReader(fStream);
				bReader = new BufferedReader(iReader);

				StringBuffer result = new StringBuffer();
				String line = null;
				while ((line = bReader.readLine()) != null) {
					result.append(line);
				}
				return result.toString();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			bReader.close();
			iReader.close();
			fStream.close();
		}
		return "File not found!";
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String input = "11111111";
		String signdate = sign(input, "GB18030");
		System.out.println(signdate);
		System.out.println(valid(input, "GB18030", signdate));
	}
}
