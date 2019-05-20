package com.avst.meetingcontrol.common.util.baseaction;

/**
 * 请求的参数
 * 用于被继承
 * @author wb
 *
 */
public class BaseReqParam {


	private String version ;

	/**
	 * 请求时间
	 */
	private String reqtime;


	/**
	 * 请求的加密码
	 */
	private String token;



	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getReqtime() {
		return reqtime;
	}

	public void setReqtime(String reqtime) {
		this.reqtime = reqtime;
	}

}
