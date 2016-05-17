package com.example.administrator.myapplication.bean;

import com.example.administrator.myapplication.MyAplication;

import java.io.Serializable;

public class HeadBean implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String txCode;


	private String reqTime;
	private String sessionId;
	private String token;

	private String osVersion;
	private String clientModel;
	private String key;
	private int noKeySave; // "1"不保存，"0"或不传，保存

	private MyAplication application;

	public HeadBean(MyAplication application) {
		this.application = application;
	}


	public String getReqTime() {
		return reqTime = System.currentTimeMillis() + "";
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getSessionId() {
		return "";
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getToken() {
		return token = "";
	}

	public void setToken(String token) {
		this.token = token;
	}


	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public void setClientModel(String clientModel) {
		this.clientModel = clientModel;
	}


	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getNoKeySave() {
		return noKeySave;
	}

	public void setNoKeySave(int noKeySave) {
		this.noKeySave = noKeySave;
	}

	@Override
	public String toString() {
		return "HeadBean [txCode=" + txCode + ", reqTime="
				+ reqTime + ", sessionId=" + sessionId + ", token=" + token
				+ ", osVersion=" + osVersion
				+ ", clientModel=" + clientModel
				+ ", key=" + key + ", noKeySave=" + noKeySave + "]";
	}

}
