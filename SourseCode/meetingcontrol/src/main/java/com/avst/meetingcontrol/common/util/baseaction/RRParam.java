package com.avst.meetingcontrol.common.util.baseaction;

/**
 * 通用的返回结果加说明，code值自己判断
 * @author wb
 *
 */
public class RRParam<T> {

	/**
	 * 返回结果的说明
	 */
	private String message;
	/**
	 * 请求的code值
	 * 1代表成功，其他自定义
	 */
	private int code=-1;

	/**
	 * 如果需要返回复杂的结果集用这个泛型
	 */
	private T t;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public RRParam(){
		message="请求失败";
		code=Code.FAIL.hashCode();//失败的hashcode值
	}

	/**
	 * 成功调用
	 */
	public void changeTrue(T t ){
		message="请求成功";
		this.t=t;
		code=1;
	}
}
