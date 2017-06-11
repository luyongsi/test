package ada;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ada.HttpRequest;

public class smssend {
	private static Logger getlog = LoggerFactory.getLogger(smssend.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HttpRequest httprequest = new HttpRequest();
		HashMap<Integer, URLinterface> urlmap = httprequest.gettext();
		int i=0;
		for (Integer str : urlmap.keySet()) {
			//System.out.println("name : " + str);
			// System.out.println("value : "+urlmap.get(str).toString());
			String tmp = urlmap.get(str).getUrl() + urlmap.get(str).getPara();
			if (urlmap.get(str).getHTTP_methode().equals("get") ) {
				getlog.error("-----------------start----------------");
				++i;
				String temp=tmp.replaceAll("手机号", "18362972380");
				getlog.error(temp);
				String s = httprequest.sendGet(temp);
				getlog.error(httprequest.unicode2String(s));
				getlog.error("-----------------end----------------");
				
			}
		}
		
		

		// 发送 GET 请求
		// String
		// s=HttpRequest.sendGet("http://user.memeyule.com/authcode/send_mobile",
		// "mobile=18362972380&type=1&callback=jQuery18303737469934858382_1490941147187&_=1490941147240");
		// System.out.println(s);

		// 发送 POST 请求
		// String
		// sr=HttpRequest.sendPost("https://www.juhe.cn/register/sendsms",
		// "j7b2a95b9a6e7780de4f1b212f5129328h6903=18362972380%26j478031873cbc0807dba2dcc990c9abbfh5949=456regdf%26j1100ecc6122b5dc6dcb0e2c5724063beh7796=%26jafe4631fbe2538ef35f101db72d7afd3h3109=36F449A1A1DFBAB4B375D6483108B0945DBFD83F");
		// System.out.println(sr);
		// String temp=HttpRequest.unicode2String(sr);
		// System.out.println(temp);

	}

}
