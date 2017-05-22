package com.whty.cms.web.netdisk.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;



public class Test {
	public static void main(String args[]){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("开始尝试连接数据库！");
			String url = "jdbc:oracle:thin:@127.0.200.55:1521/edu";
			String userName = "cmsuser";
			String password = "cmsuser";
			conn = DriverManager.getConnection(url,userName,password);
			System.out.println("连接成功！");
			String sql = "select E.CHAPTER_ID as chapterId from t_chapter e where e.textbook_id in ('TEX193','TEX194','TEX195','TEX191','26544','26546') and E.CHAPTER_PID='0' order by e.textbook_id,e.sort_num";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int num = 2;
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream("E:\\test_xls\\1.xls"));
			HSSFCellStyle cellStyle=workbook.createCellStyle(); 
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			cellStyle.setWrapText(true);
			HSSFSheet sheet0 = workbook.getSheetAt(0);
			workbook.createSheet("第四个sheet");
			sheet0.addMergedRegion(new CellRangeAddress(1,1,0,5));
			while(rs.next()){
				System.out.println("章节ID：" + rs.getString("chapterId"));
				String sql1 = "select E.TEXTBOOK_ID as textbookId,E.CHAPTER_ID as chapterId,E.CHAPTER_TITLE as chapterTitle,e.depth as depth,e.chapter_pid as chapterPId,e.is_leaf as isLeaf from t_chapter e start with E.CHAPTER_ID='" + rs.getString("chapterId") +"' connect by prior E.CHAPTER_ID=E.CHAPTER_PID";
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()){
					System.out.println("教材ID：" + rs1.getString("textbookId"));
					String textbookId = rs1.getString("textbookId");
					String chapterId = rs1.getString("chapterId");
					String chapterTitle = rs1.getString("chapterTitle");
					String depth = rs1.getString("depth");
					String chapterPId = rs1.getString("chapterPId");
					String isLeaf = rs1.getString("isLeaf");
					HSSFRow row = sheet0.getRow(num);
					if(null == row){
						row = sheet0.createRow(num);
					}
					HSSFCellStyle style=workbook.createCellStyle(); 
					style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
					row.createCell(0);
					row.getCell(0).setCellStyle(style);
					row.getCell(0).setCellValue(textbookId);
					row.createCell(1);
					row.getCell(1).setCellStyle(style);
					row.getCell(1).setCellValue(chapterId);
					row.createCell(2);
					row.getCell(2).setCellStyle(style);
					row.getCell(2).setCellValue(chapterTitle);
					row.createCell(3);
					row.getCell(3).setCellStyle(style);
					row.getCell(3).setCellValue(depth);
					row.createCell(4);
					row.getCell(4).setCellStyle(style);
					row.getCell(4).setCellValue(chapterPId);
					row.createCell(5);
					row.getCell(5).setCellStyle(style);
					row.getCell(5).setCellValue(isLeaf);
					num++;
				}
			}
			FileOutputStream fileOut = new FileOutputStream("E:\\test_xls\\1.xls");
            workbook.write(fileOut);
            fileOut.close();
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
				System.out.println("数据库连接已关闭！");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
