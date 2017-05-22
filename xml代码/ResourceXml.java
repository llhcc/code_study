package com.whty.cms.web.netdisk.controller;

import java.util.ArrayList;
import java.util.List;

public class ResourceXml {
	/** 文件MD5名 */
	private String fileMd5Name;
	
	/** 文件后缀 */
	private String fileExt;
	
	/** 文件全路径 */
	private String filePath;
	
	/** 资源标题 */
    private String title;
    
    /** 关键词 */
    private String keyword;
    
    /** 资源简介 */
    private String intro;
    
    /** 资源形式 : 1=实体资源; 2=网络资源*/
    private String resForm;
    
    /** URL资源地址 */
    private String url;
    
    /** 学段ID */
    private String periodId;
    
    /** 学科ID */
    private String subjectId;
    
    /** 版本ID */
    private String editionId;
    
    /** 年级ID */
    private String gradeId;
    
    /** 册别ID */
    private String volumeId;
    
    /** 教材ID */
    private String textbookId;
    
    /** 教材章节ID */
    private List<String> chapterIdList = new ArrayList<String>();
    
    /** 资源类型ID */
    private List<String> resTypeIdList = new ArrayList<String>();
    
    /** 用户ID */
    private String userId;
    
    /** 用户名称  */
    private String userName;
    
    /** 用户账号 */
    private String userAccount;
    
    /** 用户所属机构ID */
    private String orgId;
    
    /** 用户所属机构名称*/
    private String orgName;

	public String getFileMd5Name() {
		return fileMd5Name;
	}

	public void setFileMd5Name(String fileMd5Name) {
		this.fileMd5Name = fileMd5Name;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getResForm() {
		return resForm;
	}

	public void setResForm(String resForm) {
		this.resForm = resForm;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getEditionId() {
		return editionId;
	}

	public void setEditionId(String editionId) {
		this.editionId = editionId;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getVolumeId() {
		return volumeId;
	}

	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}

	public String getTextbookId() {
		return textbookId;
	}

	public void setTextbookId(String textbookId) {
		this.textbookId = textbookId;
	}

	public List<String> getChapterIdList() {
		return chapterIdList;
	}

	public void setChapterIdList(List<String> chapterIdList) {
		this.chapterIdList = chapterIdList;
	}

	public List<String> getResTypeIdList() {
		return resTypeIdList;
	}

	public void setResTypeIdList(List<String> resTypeIdList) {
		this.resTypeIdList = resTypeIdList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
    
	public void addChapterId(String value) {
		chapterIdList.add(value);
	}
	
	public void addResTypeId(String value) {
		resTypeIdList.add(value);
	}
}
