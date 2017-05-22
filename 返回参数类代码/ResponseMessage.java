package com.whty.cms.gateway.common.message;

import com.whty.cms.gateway.common.constants.Constants;

public class ResponseMessage {
	
	private String result;
	
	private String resultDesc;
	
	private Object data;
	
	public static ResponseMessage getSuccessResponse() {
		ResponseMessage response = new ResponseMessage();
		response.setResult(Constants.SUCCESS_CODE);
		response.setResultDesc(Constants.SUCCESS_MESSAGE);
		return response;
	}
	
	public static ResponseMessage getFailedResponse() {
		ResponseMessage response = new ResponseMessage();
		response.setResult(Constants.FAILED_CODE);
		response.setResultDesc(Constants.FAILED_MESSAGE);
		return response;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
