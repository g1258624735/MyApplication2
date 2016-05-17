package com.example.administrator.myapplication.bean;

import java.io.Serializable;


public class BusBean implements Serializable {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private HeadBean head ;
	private String body ;

	public HeadBean getHead() {
		return head;
	}

	public void setHead(HeadBean head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "BusBean [head=" + head + ", body=" + body + "]";
	}
	
	
}
