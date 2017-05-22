package com.whty.framework.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

import com.whty.framework.util.DBUtils;

public class KeyValOptionsTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(KeyValOptionsTag.class);
	
	private String value;
	
	private String paramName;
	
	@Override
	public int doStartTag() throws JspException {
		String sql = "select PARAM_KEY, PARAM_VAL from T_KEY_VAL_PARAMS where PARAM_NAME=? order by SORT_NUM";
		Object[] params = new Object[]{paramName};
		List<Map<String,Object>> list = DBUtils.queryForMapList(sql, params);
		StringBuffer buff = new StringBuffer();
		
		for (Map<String,Object> map : list) {
			String key = map.get("PARAM_KEY").toString();
			String val = map.get("PARAM_VAL").toString();
			if (key.equals(value)) {
				buff.append("<option value=\"" + key + "\" selected>" + val + "</option>");
			} else {
				buff.append("<option value=\"" + key + "\">" + val + "</option>");
			}
		}

		JspWriter out = this.pageContext.getOut();
		try {
			out.print(buff.toString());
		} catch(IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

}
