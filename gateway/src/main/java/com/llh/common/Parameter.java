package com.llh.common;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Parameter {
	
	private String name; // 字段名称
	
	private String type; // 字段类型
	
	private Integer maxLength; // 最大长度; 如果为null或者小于1则不做校验

	private Integer isNull; // 0=可选; 1=必填
	
	@Override
	public String toString() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("type", type);
		map.put("maxLength", maxLength);
		map.put("isNull", isNull);
		return new Gson().toJson(map);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getIsNull() {
		return isNull;
	}

	public void setIsNull(Integer isNull) {
		this.isNull = isNull;
	}

}
