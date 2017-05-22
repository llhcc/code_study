package com.whty.framework.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger; 

import com.whty.framework.util.CollectionUtils;
import com.whty.framework.util.DBUtils;

public class SqlOptionsTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(SqlOptionsTag.class);
	
	private String value;
	
	private String sqlName;
	
	@Override
	public int doStartTag() throws JspException {
		String sql = "select SQL_STATEMENT from T_SQL_PARAMS where SQL_NAME=?";
		Object[] params = new Object[]{sqlName};
		Object obj = DBUtils.getValue(sql, params);
		StringBuffer buff = new StringBuffer();
		if (obj != null) {
			String sqlStatement = obj.toString();
			List<Map<String,Object>> list = DBUtils.queryForMapList(sqlStatement, null);
			for (Map<String,Object> map : list) {
				Set<String> keys = map.keySet();
				List<String> keyList = CollectionUtils.newArrayList();
				keyList.addAll(keys);
				if (map.get(keyList.get(0)).equals(value)) {
					buff.append("<option value=\"" + map.get(keyList.get(0)) + "\" selected>" + map.get(keyList.get(1)) + "</option>");
				} else {
					buff.append("<option value=\"" + map.get(keyList.get(0)) + "\">" + map.get(keyList.get(1)) + "</option>");
				}
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

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

}
