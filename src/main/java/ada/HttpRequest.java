package ada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
	
	private static Logger getlog = LoggerFactory.getLogger(HttpRequest.class);
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public  String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			getlog.error("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public  String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			getlog.error("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * unicode 转字符串
	 */
	public  String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");
		string.append(hex[0]);

		for (int i = 1; i < hex.length - 1; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	public  HashMap<Integer, URLinterface> gettext() {
		HashMap<Integer, URLinterface> urlmap =new HashMap<>();
		try {
			String encoding = "UTF-8";
			File file = new File("D:\\code\\smshttp.txt");
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader buffread = new BufferedReader(read);
				String lineread = null;
				while ((lineread = buffread.readLine()) != null) {
					
					txt2map(lineread,urlmap);
					
				}
				read.close();
			} else {
				System.out.println("文件不存在");

			}
		} catch (Exception e) {
			System.out.println("读文件出错：" + e);
		}
		return urlmap;

	}
	
	public String[] getphonenumber(){
		StringBuffer phonenumber=new StringBuffer();
		try {
			String encoding = "UTF-8";
			File file = new File("D:\\code\\phone.txt");
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader buffread = new BufferedReader(read);
				String lineread = null;
				
				while ((lineread = buffread.readLine()) != null) {
					
					phonenumber.append(lineread);
					
				}
				read.close();
			} else {
				System.out.println("文件不存在");

			}
		} catch (Exception e) {
			System.out.println("读文件出错：" + e);
		}
		
		String[] phone =phonenumber.toString().split(",");
		return phone;
	}
	
	
	public  void txt2map(String str,HashMap<Integer, URLinterface> urlmap)
	{
		
		URLinterface newurl =new URLinterface();
		Pattern mpattern1 = Pattern.compile("<1>(.*?)<2>");
		Matcher mmatcher1 = mpattern1.matcher(str);
		while(mmatcher1.find())
		{
			newurl.setHTTP_methode(mmatcher1.group(1));
		
		}
		Pattern mpattern2 = Pattern.compile("<2>(.*?)<3>");
		Matcher mmatcher2 = mpattern2.matcher(str);
		while(mmatcher2.find())
		{
			newurl.setName(mmatcher2.group(1));
		
		}
		Pattern mpattern3 = Pattern.compile("<3>(.*?)<4>");
		Matcher mmatcher3 = mpattern3.matcher(str);
		while(mmatcher3.find())
		{
			newurl.setUrl(mmatcher3.group(1));
		
		}
		Pattern mpattern4 = Pattern.compile("<4>(.*?)<5>");
		Matcher mmatcher4 = mpattern4.matcher(str);
		while(mmatcher4.find())
		{
			newurl.setPara(mmatcher4.group(1));
		
		}
		urlmap.put(newurl.hashCode(), newurl);
		return ;
	}
	
	
}