package com.whty.cms.web.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

/**
 * 
 * 增量打包工具类
 * 
 * @author 李鹏
 * @since 1.0
 * @version 2013-11-9 李鹏
 */
public class IncremenPublish {

	private String javaPath;

	private Date lastDate;

	private String classPath;

	private String webPath;

	private static final String tempFileDir = "D:\\publish";

	/**
	 * 
	 * 构造函数
	 * 
	 * @param lastDate
	 * @param webDirName 存放web应用的目录名称，比如说webapps、webContent等
	 */
	public IncremenPublish(String lastDate, String webDirName) {
		this.javaPath = System.getProperty("user.dir") + "\\src";
		this.lastDate = parseDate(lastDate);
		classPath = IncremenPublish.class.getClass().getResource("/").getPath();
		classPath = classPath.substring(1, classPath.length() - 1);
		webPath = System.getProperty("user.dir") + File.separator + webDirName;
	}

	/**
	 * 
	 * 获取增量文件
	 * 
	 * @Date 2013-11-9
	 * @author 李鹏
	 */
	public void getIncremenPublishClassFile() {
		System.out.println("########################开始获取增量#####################");
		System.out.println("###上次发布时间：" + lastDate.toString());
		try {
			File tempFile = new File(tempFileDir);
			FileUtils.deleteDirectory(tempFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		File file = new File(tempFileDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 获取class增量
		System.out.println("**********开始获取class增量**********");
		moveIncremenFile(javaPath, "classes");
		System.out.println("**********开始获取jsp增量**********");
		moveIncremenFile(webPath, null);
		System.out.println("########################获取增量完成#####################");
	}

	/**
	 * 获取增量文件
	 */
	public boolean moveIncremenFile(String javaPath, String dirName) {
		try {
			File file = new File(javaPath);
			if (!file.isDirectory()) {
				Date fileDate = new Date(file.lastModified());
				if (fileDate.getTime() > lastDate.getTime()) {
					copyFile(file, dirName);
				}
			} else if (file.isDirectory() && !file.getAbsolutePath().contains("svn")
					&& !file.getAbsolutePath().endsWith("WEB-INF")) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(javaPath + File.separator + filelist[i]);
					if (!readfile.isDirectory()) {
						Date fileDate = new Date(readfile.lastModified());
						if (fileDate.getTime() > lastDate.getTime()) {
							copyFile(readfile, dirName);
						}
					} else if (readfile.isDirectory()) {
						moveIncremenFile(javaPath + File.separator + filelist[i], dirName);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("获取增量文件  Exception:" + e.getMessage());
		}
		return true;
	}

	public static void main(String[] args) {
		IncremenPublish publish = new IncremenPublish("2014-04-08","webapps");
		publish.getIncremenPublishClassFile();
	}

	/**
	 * 
	 * parseDate
	 * 
	 * @Date 2013-11-12
	 * @author 李鹏
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) {
		Date date = null;
		String pattern = "yyyy-MM-dd";
		if (strDate.length() > pattern.length()) {
		    pattern = "yyyy-MM-dd HH:mm";
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * 移动增量文件
	 * 
	 * @Date 2013-11-12
	 * @author 李鹏
	 * @param file
	 * @param dirName
	 */
	private void copyFile(File file, String dirName) {
		if (dirName == null) {
			copyJspFile(file);
		} else {
			copyClassFile(file, dirName);
		}

	}

	/**
	 * 
	 * 迁移class文件
	 * 
	 * @Date 2013-11-12
	 * @author 李鹏
	 * @param file
	 * @param dirName
	 */
	private void copyClassFile(File file, String dirName) {
		String path1 = file.getPath().replace(javaPath, classPath).replace("java", "class");
		File tempFile = new File(path1);
		String path2 = path1.replace(classPath, tempFileDir + File.separator + dirName);
		File tempFile1 = new File(path2);
		tempFile1.getParentFile().mkdirs();
		try {
			FileUtils.copyFile(tempFile, tempFile1);
		} catch (Exception e) {
			System.out.println("拷贝class文件出错");
		}
		System.out.println("path=" + path2);
	}

	/**
	 * 
	 * 迁移jsp文件
	 * 
	 * @Date 2013-11-12
	 * @author 李鹏
	 * @param file
	 */
	private void copyJspFile(File file) {
		String path = file.getPath().replace(System.getProperty("user.dir"), tempFileDir);
		File tempFile = new File(path);
		tempFile.getParentFile().mkdirs();
		try {
			FileUtils.copyFile(file, tempFile);
		} catch (Exception e) {
			System.out.println("拷贝jsp文件出错");
		}
		System.out.println("path=" + path);
	}

}
