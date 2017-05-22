package com.whty.framework.tags;

import java.io.IOException;
import java.util.Arrays;
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

public class SqlCheckboxTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(SqlCheckboxTag.class);
	
	private String name;
	
	private Object value;
	
	private String sqlName;
	
	private String delimiter = "";
	
	private Integer wrapNum; // 几个一换行
	
	@Override
	public int doStartTag() throws JspException {
		String sql = "select SQL_STATEMENT from T_SQL_PARAMS where SQL_NAME=?";
		Object[] params = new Object[]{sqlName};
		Object obj = DBUtils.getValue(sql, params);
		StringBuffer buff = new StringBuffer();
		List<String> valueList = getValueList();
		if (obj != null) {
			String sqlStatement = obj.toString();
			List<Map<String,Object>> list = DBUtils.queryForMapList(sqlStatement, null);
			int idx = 1;
			for (Map<String,Object> map : list) {
				Set<String> keys = map.keySet();
				List<String> keyList = CollectionUtils.newArrayList();
				keyList.addAll(keys);
				String checked = "";
				if (valueList != null && valueList.contains(map.get(keyList.get(0)))) {
					checked = "checked";
				}
				String wrap = "";
				if (wrapNum != null && wrapNum > 0) {
					if (idx % wrapNum == 0) {
						wrap = "<br/>";
					}
				}
				buff.append("<input type=\"checkbox\" name=\"" + name + "\" value=\"" + map.get(keyList.get(0)) + "\" " + checked + "/>&nbsp;" + map.get(keyList.get(1)) + delimiter + wrap);
				idx++;
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
	
	@SuppressWarnings("unchecked")
	private List<String> getValueList() {
		List<String> values = null;
		if (value instanceof List) {
			values = (List<String>) value;
		}
		if (value instanceof String[]) {
			String[] chkedValues = (String[]) value;
			values = Arrays.asList(chkedValues);
		}
		return values;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public Integer getWrapNum() {
		return wrapNum;
	}

	public void setWrapNum(Integer wrapNum) {
		this.wrapNum = wrapNum;
	}

}
