package com.whty.cms.ft.cloudstorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.whty.framework.util.CollectionUtils;
import com.whty.framework.util.MapUtils;

public final class DBUtils {
	
	private static ComboPooledDataSource dataSource;
	
	private static ResourceBundle rb = ResourceBundle.getBundle("prop", Locale.getDefault());
	
	static {
		try {
			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
			dataSource.setAcquireIncrement(1);
			dataSource.setInitialPoolSize(10);
			dataSource.setJdbcUrl(rb.getString("jdbcUrl"));
			dataSource.setMaxIdleTime(60);
			dataSource.setMaxPoolSize(50);
			dataSource.setMinPoolSize(0);
			dataSource.setUser(rb.getString("user"));
			dataSource.setPassword(rb.getString("password"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 获取单个值
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Object getValue(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object value = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				value = rs.getObject(1);
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return value;
	}
	
	/**
	 * 获取sequence值
	 * @param seqName
	 * @return
	 */
	public static Integer getSeqValue(String seqName) {
		String sql = "select " + seqName + ".NEXTVAL from dual";
		Integer value = Integer.valueOf(getValue(sql, null).toString());
		return value;
	}
	
	/**
	 * 查询列表，封装在map中
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> queryForMapList(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String,Object>> list = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs = pstmt.executeQuery();
			list = convertToMapList(rs);
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return list;
	}
	
	/**
	 * @author	xiongxiaofei
	 * @date	2015年2月13日
	 * @desc	查询单条记录
	 * @param sql
	 * @param params
	 * @return
	 */
	public static Map<String,Object> queryForMap(String sql, Object[] params) {
		Map<String,Object> map = null;
		List<Map<String,Object>> list = queryForMapList(sql, params);
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 批处理
	 * @param sql
	 * @param params
	 * @return
	 */
	public static int[] executeBatch(String sql, List<Object[]> batchArgs) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int[] ret = null;
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			for (Object[] arg : batchArgs) {
				for (int i = 0; i < arg.length; i++) {
					pstmt.setObject(i+1, arg[i]);
				}
				pstmt.addBatch();
			}
			ret = pstmt.executeBatch();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			close(conn, pstmt, null);
		}
		return ret;
	}
	
	private static void setParams(PreparedStatement pstmt,
			Object[] params) throws SQLException {
		if (params != null && params.length > 0) {
			int i = 1;
			for (Object param : params) {
				pstmt.setObject(i++, param);
			}
		}
	}
	
	private static List<Map<String,Object>> convertToMapList(ResultSet rs) 
			throws SQLException {
		List<Map<String,Object>> mapList = CollectionUtils.newArrayList();
		if (rs != null) {
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			while (rs.next()) {
				Map<String,Object> map = MapUtils.newLinkedHashMap();
				for (int i = 1; i <= count; i++) {
					String name = metaData.getColumnName(i);
					Object value = rs.getObject(name);
					map.put(name, value);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	private static void close(Connection conn, PreparedStatement pstmt,
			ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}
