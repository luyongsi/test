package ada;

public class URLinterface {
	/**
	 * HTTP方法post或get
	 */
	public String HTTP_methode;
	/**
	 * 商家名称
	 */
	public String name;
	/**
	 * URL地址
	 */
	public String url;
	/**
	 * 参数
	 */
	public String para;

	public String getHTTP_methode() {
		return HTTP_methode;
	}

	public void setHTTP_methode(String hTTP_methode) {
		HTTP_methode = hTTP_methode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String toString() {
		return "HTTP_methode : " + HTTP_methode + "  name : " + name + "  url : " + url + "  para : " + para;

	}

}
