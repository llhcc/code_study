package com.whty.framework.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger; 

import com.whty.framework.util.DBUtils;

public class SqlDefTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(SqlDefTag.class);
	
	private String sqlName;
	
	private String id;
	
	@Override
	public int doStartTag() throws JspException {
		String sql = "select SQL_STATEMENT from T_SQL_PARAMS where SQL_NAME=?";
		Object[] params = new Object[]{sqlName};
		Object obj = DBUtils.getValue(sql, params);
		String result = "";
		if (obj != null) {
			String sqlStatement = obj.toString();
			Object value = DBUtils.getValue(sqlStatement, new Object[]{id});
			if (value != null) {
				result = value.toString();
			}
		}
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

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
