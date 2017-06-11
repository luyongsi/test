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
	 * ��ָ��URL����GET����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return URL ������Զ����Դ����Ӧ���
	 */
	public  String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection connection = realUrl.openConnection();
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����ʵ�ʵ�����
			connection.connect();
			// ��ȡ������Ӧͷ�ֶ�
			Map<String, List<String>> map = connection.getHeaderFields();
			// �������е���Ӧͷ�ֶ�
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			getlog.error("����GET��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر�������
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
	 * ��ָ�� URL ����POST����������
	 * 
	 * @param url
	 *            ��������� URL
	 * @param param
	 *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return ������Զ����Դ����Ӧ���
	 */
	public  String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			getlog.error("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
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
	 * unicode ת�ַ���
	 */
	public  String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");
		string.append(hex[0]);

		for (int i = 1; i < hex.length - 1; i++) {

			// ת����ÿһ�������
			int data = Integer.parseInt(hex[i], 16);

			// ׷�ӳ�string
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
				System.out.println("�ļ�������");

			}
		} catch (Exception e) {
			System.out.println("���ļ�����" + e);
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
				System.out.println("�ļ�������");

			}
		} catch (Exception e) {
			System.out.println("���ļ�����" + e);
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