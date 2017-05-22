package com.whty.cms.gateway.user.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whty.cms.api.metadata.service.TextbookService;
import com.whty.cms.gateway.common.message.ResponseMessage;
import com.whty.framework.util.MapUtils;

@Controller
public class TestController {
	
	@Resource
	private TextbookService textbookService;
	
	
	@RequestMapping(value="/getTextbookNamesList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage getTextbookNamesList(@RequestBody JSONObject obj) throws Exception {
		
		String textbookIds = obj.optString("textbookIds");
		if(StringUtils.isEmpty(textbookIds)){
			ResponseMessage responseMessage = ResponseMessage.getFailedResponse();
			responseMessage.setResultDesc("参数不完整");
			return responseMessage;
		}
		List<Map<String,Object>> mapList = textbookService.findTextbookListByTextbookIds(textbookIds);
		ResponseMessage responseMessage = ResponseMessage.getSuccessResponse();
		Map<String,Object> data = MapUtils.newHashMap();
		data.put("textbookList", mapList);
		responseMessage.setData(data);
		return responseMessage;
	}

}
