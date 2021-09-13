package common.utils;
import lombok.SneakyThrows;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 *
 * @author JianLiMa
 * @Email majianli@mingxinsk.com
 * @create 2021/7/15 11:25
 */
public class SignUtil {
    /**
     * 签名
     *
     * @param dataMap 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(TreeMap<String, String> dataMap, PrivateKey privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        PrivateKey key = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(sortParamsStr(dataMap).getBytes());
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    /**
     * 验签
     *
     * @param params 原始字符串
     * @param publicKeyStr 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verifySign(Map<String, String> params, String publicKeyStr, String sign)
            throws Exception {
        TreeMap<String, String> dataMap = new TreeMap<>();
        dataMap.putAll(params);
        PublicKey publicKey = getPublicKey(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        PublicKey key = KeyFactory.getInstance("RSA").generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(sortParamsStr(dataMap).getBytes());
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 按照参数名ASCII码递增排序并用‘&’连接
     * @param dataMap
     * @return
     */
    private static String sortParamsStr(TreeMap<String, String> dataMap){
        Set<Map.Entry<String, String>> entries = dataMap.entrySet();
        Iterator<Map.Entry<String, String>> iterators = entries.iterator();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (iterators.hasNext()){
            Map.Entry entry = (Map.Entry) iterators.next();
            String k = String.valueOf(entry.getKey());
            String v = String.valueOf(entry.getValue());
            if (v != null && !"".equals(v) && entry.getValue() !=null) {
                if (i < 1){
                    sb.append(k).append("=");
//                            .append(URLEncoder.encode(v, Charset.forName(String.valueOf(StandardCharsets.UTF_8))));
                    i++;
                }else {
                    sb.append("&")
                            .append(k).append("=");
//                            .append(URLEncoder.encode(v, Charset.forName(String.valueOf(StandardCharsets.UTF_8))));
                }
            }
        }
        return sb.toString();
    }


    @SneakyThrows
    public static void main(String[] args) {
        //        //公共请求参数 TODO
        TreeMap<String, String> dataMap = new TreeMap<>();
        dataMap.put("request_id", "4e770f101c9a4191bfd4609d26c6e0bd");
        dataMap.put("app_id", "20210909885567978169434112");
//        dataMap.put("access_token", "84e406c563593aab0868d789825a4d43");
//        dataMap.put("method", "isv.manager.add");
        dataMap.put("method", "auth.access.token");
        dataMap.put("version", "1.0");
        dataMap.put("timestamp", "2020-11-01 13:44:11");



        PrivateKey privateKey = getPrivateKey("MIICeAIBADAEFAASCAmIwggJeAgEAAoGBAK6reHzd1iyqvTzvXJsjeKgC" +
                "PPmIsvb0UAN0vfLwZsKZQTwZ6k7aVpY+BUSCc8k5A1fSrUaXZ0YOh2bp4fzIiJ/48sz5vD97bKy4IB8tbaGAq79T/RTB6W0wUAfzBQ" +
                "gKXd+mKKsh2lALggvFYxDKve5wPDbwjLyNG4rgRUfOIvCgYEAlsrNj+7rsVANYwe5yO0Mgu67uSk8Z1pvoSwtuAe2jxVD" +
                "biFpWH3B9p7AaW69iuA/dJgaozCeJarxVfVf9Z0h5JuMWx+h0L97SrGFX15WrNSLG0Q3tw2kpToffgr7tZ4TbdqYYQP03B" +
                "qNZtKHZwIh5RubuWW0KVDGXsECQQDgByPpAumZg7G8IZuG1mY55NfU/CnoLkAC5YSWQS4RVlVg2UYmTr0YE95SAQE3bsUR3" +
                "HB9jDO0VonRJAkEAx5kIWTDNZMEzNPKCeFVPn8+3yMRkxwN8EnOcO3lAME5vBJ3iVKrGV1jDJkzx3BxxDNCHjG7FPmr6JbbC91e9jQ" +
                "JBANLCBOeWhNuWiKQb2C7V1cyiJseosd+YgqOPIKg2DmhYW3V3hgTZ3kYjyVE2CKJtpLhPRrATAf0oaOLPa0/6KHkCQQC+SM+jsBnv" +
                "9xwpWxU1QSei1LWCanElJdZQBPWp4WNnai7+uMJAM3rZZN7PEcX2MQs5y0eegy/53KTT9ZOTmIs1AkA0YhHFq0iLgVmmzAHae43Uag" +
                "GYxZbmnUmViM3U5uZYwug/ls0uk6o4RKNfVbDVofGubFh7jkFtkK9dGDkt7qYt");
        //参数签名SIGN
        String sign = sign(dataMap, privateKey);
        System.out.println("sign :" + sign);

        //dataMap.put("sign", sign);
        System.out.println(verifySign(dataMap, "MIGfMAb3DQEBAQUAA4GNADCBiQKBgQCuq3h83dYsqr0871ybI3io" +
                "Ajz5iLL29FADdL3y8GbCmUE8GepO2laWPgVEgnPJOQNX0qGDodm6eH8yIif+PLM+bw/e2ysuCAfLW2hgKu/U/0UweltMFAH8w" +
                "UICl3fpiirIdpQC4ILxWMQyr3ucDw28Iy8jRuK4EVHziLxNQIDAQAB", sign));
    }
}
