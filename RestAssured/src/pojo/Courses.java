package pojo;

import java.util.List;

public class Courses {
	private List<WebAutomation> webAutoamtion;
	private List<Api> api;
	private List<Mobile> mobile;
	
	public List<WebAutomation> getWebAutoamtion() {
		return webAutoamtion;
	}
	public void setWebAutoamtion(List<WebAutomation> webAutoamtion) {
		this.webAutoamtion = webAutoamtion;
	}
	public List<Api> getApi() {
		return api;
	}
	public void setApi(List<Api> api) {
		this.api = api;
	}
	public List<Mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}

}
