package com.whty.framework.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

import com.whty.framework.util.DBUtils;

public class KeyValDefTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(KeyValDefTag.class);
	
	private Object paramKey;
	
	private String paramName;
	
	@Override
	public int doStartTag() throws JspException {
		String sql = "select PARAM_VAL from T_KEY_VAL_PARAMS where PARAM_NAME=? and PARAM_KEY=?";
		Object[] params = new Object[]{paramName, paramKey};
		Object value = DBUtils.getValue(sql, params);
		String result = value != null ? value.toString() : "";
		JspWriter out = this.pageContext.getOut();
		try {
			out.print(result);
		} catch(IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public Object getParamKey() {
		return paramKey;
	}

	public void setParamKey(Object paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

}
