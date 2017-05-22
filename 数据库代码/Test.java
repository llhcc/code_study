package com.whty.cms.web.netdisk.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	public static void main(String args[]){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@127.0.200.55:1521/edu";
			String user = "";
			String password = "";
			conn = DriverManager.getConnection(url, user, password);
			String sql = "";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				String aaa = rs.getString("aaa");
				System.out.println(aaa);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {
				if(null != rs){
					rs.close();
				}
				if(null != ps){
					ps.close();
				}
				if(null != conn){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
