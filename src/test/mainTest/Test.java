package test.mainTest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "format%3Dxml%26partner%3D2088701730148610%26req_data%3D%3Cauth_and_execute_req%3E%3Crequest_token%3E2014081564f5dd4c6154f3b3e1de44957bf6dbc4%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E%26sec_id%3DMD5%26service%3Dalipay.wap.auth.authAndExecute%26v%3D2.0%26sign%3D17e049b6d012a286368477d4d93e0385";
		System.out.println(URLDecoder.decode(s));
	}
	
}
