package com.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

public class CharsetDetector {

	private boolean found = false;
	private String result;
	private int lang;

	public String[] detectChineseCharset(InputStream in) throws IOException {
		lang = nsPSMDetector.CHINESE;
		String[] prob;
		nsDetector det = new nsDetector(lang);
		det.Init(new nsICharsetDetectionObserver() {
			public void Notify(String charset) {
				found = true;
				result = charset;
			}
		});
		BufferedInputStream imp = new BufferedInputStream(in);
		byte[] buf = new byte[1024];
		int len;
		boolean isAscii = true;
		while ((len = imp.read(buf, 0, buf.length)) != -1) {
			if (isAscii){
				isAscii = det.isAscii(buf, len);
			}
			if (!isAscii) {
				if (det.DoIt(buf, len, false)){
					break;
				}
			}
		}
//		imp.close();
//		in.close();
		det.DataEnd();
		if (isAscii) {
			found = true;
			prob = new String[] { "ASCII" };
		} else if (found) {
			prob = new String[] { result };
		} else {
			prob = det.getProbableCharsets();
		}
		return prob;
	}

	public String[] detectAllCharset(InputStream in) throws IOException {
		try {
			lang = nsPSMDetector.ALL;
			return detectChineseCharset(in);
		} catch (IOException e) {
			throw e;
		}
	}
	
	public String detectFirstAllLCharset(InputStream in) throws IOException {
		try {
			lang = nsPSMDetector.ALL;
			String[] charsets = detectChineseCharset(in);
			if(DataHandle.isNullOrEmpty(charsets)){
				return "UTF-8";
			}else{
				return charsets[0];
			}
		} catch (IOException e) {
			throw e;
		}
	}

}
