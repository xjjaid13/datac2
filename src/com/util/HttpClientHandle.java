package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class HttpClientHandle {

	private static Logger log = Logger.getLogger(RssUtil.class);
	
	public static String returnHtmlContent(String url) throws Exception{
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
			throw e;
		} finally {
		    try {
		    	if(response1 != null){
		    		response1.close();
		    	}
			} catch (IOException e) {
				log.error(e);
			}
		}
	}
	
}
