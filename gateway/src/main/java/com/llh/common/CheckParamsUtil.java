package com.llh.common;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.digester.Digester;
import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger; 

import com.llh.common.exception.BizException;

public class CheckParamsUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(CheckParamsUtil.class);
	
	private static Map<String,List<Parameter>> rulesMap = new HashMap<String,List<Parameter>>();
	
	private static final String TYPE_STRING = "String";
	
	private static final String TYPE_INTEGER = "Integer";
	
	private static final String TYPE_LONG = "Long";
	
	private static final String TYPE_FLOAT = "Float";
	
	private static final String TYPE_STRING_ARR = "String[]";
	
	private static final String TYPE_INTEGER_ARR = "Integer[]";
	
	private static final String TYPE_LONG_ARR = "Long[]";
	
	static {
		init();
	}
	
	private static void init() {
		InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("checkParams.xml");
		Digester digester = new Digester();
		digester.addObjectCreate("checkParams", CheckParams.class);
		digester.addObjectCreate("checkParams/checkParam", CheckParam.class);
		digester.addSetProperties("checkParams/checkParam");
		digester.addObjectCreate("checkParams/checkParam/parameter", Parameter.class);
		digester.addSetProperties("checkParams/checkParam/parameter");
		digester.addSetNext("checkParams/checkParam/parameter", "addParam");
		digester.addSetNext("checkParams/checkParam", "addCheckParam");
		try {
			CheckParams checkParams = (CheckParams) digester.parse(inStream);
			List<CheckParam> list = checkParams.getCheckParamList();
			for (CheckParam checkParam : list) {
				rulesMap.put(checkParam.getUrl(), checkParam.getParamList());
			}
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	public static Boolean checkParams(String url, String jsonBody) throws BizException {
		List<Parameter> rules = rulesMap.get(url);
		Boolean result = true;
		if (rules != null && rules.size() > 0) {
			JSONObject json = JSONObject.fromObject(jsonBody);
			for (Parameter rule : rules) {
				String name = rule.getName();
				String type = rule.getType();
				Integer maxLength = rule.getMaxLength();
				Integer isNull = rule.getIsNull();
				if (isNull == 1) { // 必填
					/** 校验非空性 */
					Object fieldValue = json.opt(name);
					if (fieldValue == null) {
						throw new BizException("014003", "参数" + name + "为必传项，请核对！");
					}
					/** 校验字段类型 */
					try {
						if (TYPE_STRING.equals(type)) {
							Object value = json.get(name);
							if (!(value instanceof String)) {
								throw new Exception();
							}
						} else if (TYPE_INTEGER.equals(type)) {
							json.getInt(name);
						} else if (TYPE_LONG.equals(type)) {
							json.getLong(name);
						} else if (TYPE_FLOAT.equals(type)) {
							json.getDouble(name);
						} else if (TYPE_STRING_ARR.equals(type)) {
							JSONArray array = json.getJSONArray(name);
							for (int i = 0; i < array.size(); i++) {
								Object value = array.get(i);
								if (!(value instanceof String)) {
									throw new Exception();
								}
							}
						} else if (TYPE_INTEGER_ARR.equals(type)) {
							JSONArray array = json.getJSONArray(name);
							for (int i = 0; i < array.size(); i++) {
								array.getInt(i);
							}
						} else if (TYPE_LONG_ARR.equals(type)) {
							JSONArray array = json.getJSONArray(name);
							for (int i = 0; i < array.size(); i++) {
								array.getLong(i);
							}
						}
					} catch(Exception ex) {
						throw new BizException("015003", "参数" + name + "类型不正确！");
					}
					/** 校验字段长度 */
					if (maxLength >= 1) {
						if (TYPE_STRING.equals(type) || TYPE_INTEGER.equals(type) || TYPE_LONG.equals(type)) {
							String strFieldValue = json.getString(name);
							if (strFieldValue.length() > maxLength) {
								throw new BizException("015003", "参数" + name + "长度超过限制" + maxLength);
							}
						} else {
							JSONArray array = json.getJSONArray(name);
							if (array.size() > maxLength) {
								throw new BizException("015003", "参数" + name + "长度超过限制" + maxLength);
							}
						}
					}
				} else if (isNull == 0) { // 非必填
					Object fieldValue = json.opt(name);
					if (fieldValue != null) { // 对于非必填字段，如果传了，就做校验
						/** 校验字段类型 */
						try {
							if (TYPE_STRING.equals(type)) {
								Object value = json.getString(name);
								if (!(value instanceof String)) {
									throw new Exception();
								}
							} else if (TYPE_INTEGER.equals(type)) {
								json.getInt(name);
							} else if (TYPE_LONG.equals(type)) {
								json.getLong(name);
							} else if (TYPE_STRING_ARR.equals(type)) {
								JSONArray array = json.getJSONArray(name);
								for (int i = 0; i < array.size(); i++) {
									Object value = array.get(i);
									if (!(value instanceof String)) {
										throw new Exception();
									}
								}
							} else if (TYPE_INTEGER_ARR.equals(type)) {
								JSONArray array = json.getJSONArray(name);
								for (int i = 0; i < array.size(); i++) {
									array.getInt(i);
								}
							} else if (TYPE_LONG_ARR.equals(type)) {
								JSONArray array = json.getJSONArray(name);
								for (int i = 0; i < array.size(); i++) {
									array.getLong(i);
								}
							}
						} catch(Exception ex) {
							throw new BizException("015003", "参数" + name + "类型不正确！");
						}
						/** 校验字段长度 */
						if (maxLength >= 1) {
							if (TYPE_STRING.equals(type) || TYPE_INTEGER.equals(type) || TYPE_LONG.equals(type)) {
								String strFieldValue = json.getString(name);
								if (strFieldValue.length() > maxLength) {
									throw new BizException("015003", "参数" + name + "长度超过限制" + maxLength);
								}
							} else {
								JSONArray array = json.getJSONArray(name);
								if (array.size() > maxLength) {
									throw new BizException("015003", "参数" + name + "长度超过限制" + maxLength);
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

}
