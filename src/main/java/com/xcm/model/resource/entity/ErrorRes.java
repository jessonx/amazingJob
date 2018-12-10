package com.xcm.model.resource.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class ErrorRes {
	private String errorCode;
	private String errorMessage;

	public ErrorRes() {

	}

	public ErrorRes(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@XmlElement(name = "err_code", nillable = true)
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@XmlElement(name = "err_msg", nillable = true)
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
