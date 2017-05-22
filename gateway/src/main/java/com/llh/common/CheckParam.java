package com.llh.common;

import java.util.ArrayList;
import java.util.List;

public class CheckParam {
	
	private String url;
	
	private List<Parameter> paramList = new ArrayList<Parameter>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addParam(Parameter param) {
		paramList.add(param);
	}

	public List<Parameter> getParamList() {
		return paramList;
	}

	public void setParamList(List<Parameter> paramList) {
		this.paramList = paramList;
	}

}
