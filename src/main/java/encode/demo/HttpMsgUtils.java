package encode.demo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class HttpMsgUtils {

	@SuppressWarnings("unused")
	private static String SSL_PROTOCOL = "TLS";

	private static int SOCKET_TIMEOUT = 60 * 1000;

	private static int CONNECT_TIMEOUT = 30 * 1000;

	/**
	 * 
	 * @param url
	 * @param postMsg
	 * @param returnCharset
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url, String postMsg, String returnCharset) throws Exception {
		CloseableHttpClient httpClient = null;
		String retMsg = null;
		try {
			httpClient = HttpClients.createDefault();

			HttpPost httpPost = new HttpPost(url);
			StringEntity strEntity = new StringEntity(postMsg);
			System.out.println(strEntity);
			httpPost.setEntity(strEntity);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
					.setConnectTimeout(CONNECT_TIMEOUT).build();
			httpPost.setConfig(requestConfig);

			httpPost.setHeader("accept", "*/*");
			httpPost.setHeader("connection", "Keep-Alive");
			httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpPost.setHeader("Content-Type", "text/xml");

			CloseableHttpResponse response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode < 200 || statusCode > 300) {
				throw new Exception("partner server return error, code=" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				retMsg = EntityUtils.toString(entity, returnCharset);
			}
		} catch (Exception e) {

		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					System.out.println("httpsPost has error!");
				}
			}
		}
		return retMsg;
	}

	public static void main(String[] args) throws Exception {
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat timeSdf = new SimpleDateFormat("HHmmss");
		String traceNo = "105584041800001" + timeSdf.format(new Date()); // 生成唯一traceNo
		// Z001密钥同步,该步骤在第一次对接时调用,如无特殊情况,无需重复调用
		JSONObject req = new JSONObject();
		req.put("TxTime", timeSdf.format(new Date()));
		req.put("TxDate", dateSdf.format(new Date()));
		req.put("TxCode", "Z001");
		req.put("TraceNo", traceNo);
		String signData = RSASignatureUtils.sign(req.toString(), "GB18030");
		JSONObject head = new JSONObject();
		head.put("TxCode", "Z001");
		head.put("OrgNo", "105584041800001");
		head.put("TraceNo", traceNo);
		head.put("SecNo", "");
		head.put("SignData", signData);
		JSONObject json = new JSONObject();
		json.put("Head", head);
		json.put("Req", req);
		System.out.println("报文内容：" + json.toString());

		// POST
		String url = "Your url address";
		String resp = httpPost(url, json.toString(), "UTF-8");

		System.out.println("resp：" + resp);
		System.out.println("------------------------------------------------");
		System.out.println("------------------------------------------------");

		// 获取<Dck>十组密钥
		JSONObject resp2 = new JSONObject(resp);
		String Dck = resp2.getJSONObject("Resp").getString("Dck");
		// 生成随机的一组密钥，范围：1-10
		int secNo = (int) (Math.random() * 10) + 1;
		// 截取对应的加密密钥
		String encrypt_key = Dck.substring((secNo - 1) * 24, secNo * 24);
		String BASE_KEY = "333705661205A5E3D950933325240713";
		// *计算出工作密钥
		String work_key = CCBDES.getWorkKey(encrypt_key, BASE_KEY);
		traceNo = traceNo + "1";
		// 根据自己的需要组相应的Req
		JSONObject req2 = new JSONObject();
		req.put("TxTime", dateSdf.format(new Date()));
		req.put("TxDate", timeSdf.format(new Date()));
		req2.put("TxCode", "SLS002");
		req2.put("PtCode", "PT003");
		req2.put("TraceNo", traceNo);
		req2.put("CredApprSeriNO", "PT00319051410015940400MCODE01");
		System.out.println(work_key);
		//*先加密
		String encryptReq = CCBDES.encryptStr(req2.toString(), work_key);
		//*在签名
		String signData2 = RSASignatureUtils.sign(encryptReq, "GB18030");
		JSONObject head2 = new JSONObject();
		head2.put("TxCode", "SLS002");
		head2.put("OrgNo", "105584041800001");
		head2.put("TraceNo", traceNo);
		head2.put("SecNo", secNo);
		//* 签名key
		head2.put("SignData", signData2);
		JSONObject json2 = new JSONObject();
		json2.put("Head", head2);
		//*加密key
		json2.put("Req", encryptReq);
		System.out.println("报文内容：" + json2.toString());

		// Post
		resp = httpPost(url, json2.toString(), "UTF-8");
		System.out.println("resp：" + resp);

		// 获取SignData
		JSONObject respJson = new JSONObject(resp);
		String SignData = respJson.getJSONObject("Head").getString("SignData");
		String respBody = respJson.getString("Resp");
		// 验签
		if (RSASignatureUtils.valid(respBody, "GB18030", SignData)) {
			int secno = Integer.parseInt(respJson.getJSONObject("Head").getString("SecNo"));
			// 计算工作密钥
			String decrypt_key = Dck.substring((secno - 1) * 24, secno * 24);
			work_key = CCBDES.getWorkKey(decrypt_key, BASE_KEY);
			System.out.println("work_key:" + work_key);
			String s = CCBDES.decryptStr(respBody, work_key);
			System.out.println(s);
		}
	}
}
