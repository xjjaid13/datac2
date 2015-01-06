package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import javax.net.ssl.TrustManager;  
import javax.net.ssl.X509TrustManager;  

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;  
import java.security.cert.X509Certificate;  

import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.ResponseHandler;  
import org.apache.http.conn.ClientConnectionManager;  
  
import org.apache.http.conn.scheme.Scheme;  
import org.apache.http.conn.scheme.SchemeRegistry;  
import org.apache.http.conn.scheme.SchemeSocketFactory;  
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;  
import org.apache.http.impl.client.BasicResponseHandler;  
import org.apache.http.impl.client.ClientParamsStack;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.params.DefaultedHttpParams;  
import org.apache.http.params.HttpParams;  

import javax.net.ssl.SSLContext; 

public class HttpClientHandle {

	private static Logger log = Logger.getLogger(RssUtil.class);
	
	public static String returnHtmlContent(String url){
		CloseableHttpResponse response1 = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0");
			response1 = httpclient.execute(httpGet);
		    HttpEntity entity1 = response1.getEntity();
		    
		    InputStream in = entity1.getContent();
		    
		    String charset = new CharsetDetector().detectFirstAllLCharset(in);
		    
		    InputStreamReader inReader = new InputStreamReader(entity1.getContent(),charset);
		    BufferedReader rb = new BufferedReader(inReader);
			int tempbyte;
			StringBuffer strBuf = new StringBuffer();
			while((tempbyte = rb.read()) != -1){
				strBuf.append((char)tempbyte);
			}
		    return strBuf.toString();
		} catch(Exception e){
			log.error(e);
		} finally {
		    try {
				response1.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return null;
	}
	
	public static String returnHttpsHtmlContent(String url) throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException{
		 HttpClient httpclient = new DefaultHttpClient();  
         //Secure Protocol implementation.  
		SSLContext ctx = SSLContext.getInstance("SSL");  
		         //Implementation of a trust manager for X509 certificates  
		X509TrustManager tm = new X509TrustManager() {  
		
		 public void checkClientTrusted(X509Certificate[] xcs,  
		         String string) throws CertificateException {  
		
		 }  
		
		 public void checkServerTrusted(X509Certificate[] xcs,  
		         String string) throws CertificateException {  
		 }  
		
		 public X509Certificate[] getAcceptedIssuers() {  
		     return null;  
		 }  
		};  
		ctx.init(null, new TrustManager[] { tm }, null);  
		SSLSocketFactory ssf = new SSLSocketFactory(ctx);  
		ssf.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier()); 
		
		ClientConnectionManager ccm = httpclient.getConnectionManager();  
		         //register https protocol in httpclient's scheme registry  
		SchemeRegistry sr = ccm.getSchemeRegistry();  
		sr.register(new Scheme("https", 443, ssf));  
		
		HttpGet httpget = new HttpGet(url);  
		HttpParams params = httpclient.getParams();  
		
		params.setParameter("param1", "paramValue1");  
		
		httpget.setParams(params);  
		System.out.println("REQUEST:" + httpget.getURI());  
		ResponseHandler responseHandler = new BasicResponseHandler();  
		String responseBody;  
		
		responseBody = httpclient.execute(httpget, responseHandler);  
		return responseBody;
	}
	
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
		System.out.println(HttpClientHandle.returnHtmlContent("http://ecs.aliyuncs.com/?ImageId=m-23k2dnem9&SecurityGroupId=sg-23yxaiskq&Format=JSON&SignatureMethod=HMAC-SHA1&Password=lhLJKzsSDd3l&InternetMaxBandwidthIn=25&InternetChargeType=PayByTraffic&Signature=kzWcYPPYNsvuiQlpIBmfGbGwD6k%3D&HostName=mountEcs&InstanceType=ecs.t1.xsmall&TimeStamp=2015-01-06T02%3A46%3A55Z&InternetMaxBandwidthOut=25&Action=CreateInstance&AccessKeyId=uL9Pltll2WA6x4wg&RegionId=cn-hangzhou&SignatureNonce=a0639406-d7db-4237-b3e3-130a29dedf68&SignatureVersion=1.0&Version=2014-05-26"));
	}
	
}
