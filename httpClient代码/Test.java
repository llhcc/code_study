package com.whty.cms.web.common.filter;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.whty.framework.util.HttpClient;
import com.whty.framework.util.MapUtils;

public class Test {
	public static void main(String args[]){
		//post
		String url = "";
		JSONObject param = new JSONObject();
		param.put("orgid", "");
		param.put("userid", "");
		try {
			String retResponse = new HttpClient(url).doPost(param.toString());
			JSONObject responseJson = JSONObject.fromObject(retResponse);
			String retCode = responseJson.optString("retCode");
			if ("000000".equals(retCode)) {
				String result = responseJson.optString("result");
			} else {
				System.out.println("获取用户官职信息异常->");
			}
		} catch (Exception e) {
			System.out.println("获取用户官职信息异常->" + e.getMessage());
		}
             
               HttpClient httpClient = new HttpClient(SysConf.getString("api.sme.url") + "/point/addpoint");
	       Map<String,String> contentMap = MapUtils.newHashMap();
	       contentMap.put("usercode", userId);
	       contentMap.put("typecode", typeCode);
	       JSONObject content = JSONObject.fromObject(contentMap);
	       String response = httpClient.doPost(content.toString());



		
		//get
		try {
			String retResponse = new HttpClient(url).doGet();
			JSONObject responseJson = JSONObject.fromObject(retResponse);
			String retCode = responseJson.optString("result");
			if ("000000".equals(retCode)) {
				JSONArray list = responseJson.optJSONArray("list");
				Map<String, String> childMap = MapUtils.newHashMap();
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						JSONObject childObj = list.getJSONObject(i);
						String childId = childObj.optString("personid");
						String name = childObj.optString("name");
						childMap.put(childId, name);
					}
				}
			} else {
				System.out.println("获取孩子信息异常->");
			}
		} catch (Exception e) {
			System.out.println("获取孩子信息异常->" + e.getMessage());
		}
	}
}
