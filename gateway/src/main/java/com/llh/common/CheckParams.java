package com.llh.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("checkParams")
public class CheckParams {

	private List<CheckParam> checkParamList = new ArrayList<CheckParam>();
	
	public void addCheckParam(CheckParam checkParam) {
		checkParamList.add(checkParam);
	}

	public List<CheckParam> getCheckParamList() {
		return checkParamList;
	}

	public void setCheckParamList(List<CheckParam> checkParamList) {
		this.checkParamList = checkParamList;
	}

}
