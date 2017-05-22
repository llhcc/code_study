package com.whty.cms.web.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.whty.cms.common.constants.MessageConstants;
import com.whty.cms.common.domain.SessionUser;
import com.whty.cms.common.util.SysConf;
import com.whty.cms.web.common.constants.CommonConstants;
import com.whty.cms.web.common.util.CommonFunction;
import com.whty.framework.message.MessageCode;
import com.whty.framework.util.CollectionUtils;
import com.whty.framework.util.MemcacheUtils;

public class CommonInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LogManager.getLogger(CommonInterceptor.class);
	
	private static int cacheSecs = 2 * 60 * 60; // 缓存时间
	
	private List<String> ignoreUrls = CollectionUtils.newArrayList();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		String servletPath = request.getServletPath();
		if (ignoreUrls.contains(servletPath)) {
			return true;
		}
		
		SessionUser userInfo = (SessionUser) request.getSession().getAttribute(CommonConstants.SESSION_USER);
		String usessionid = "";
		if (userInfo != null) {
			usessionid = userInfo.getUsesessionid();
		}
		String headerHtml = null;
		String footerHtml = null;
		String zoneHeader = null;
		String zoneFooter = null;
		String classHeader = null;
		String schoolHeader = null;
		String oneClassHeader = null;
		/* 增加访客访问头部 */
		String visitorHeader = null;
		String visitorFooter = null;
		String classId = StringUtils.isEmpty(request.getParameter("groupId")) ? null : request.getParameter("groupId");
		String schoolId = StringUtils.isEmpty(request.getParameter("schoolId")) ? null : request.getParameter("schoolId");
		/* 获取访客的用户Id */
		String userId = StringUtils.isEmpty(request.getParameter("userId")) ? null : request.getParameter("userId");
		/* 判断学校用户是否从学科资源页面发布资源 */
		String isOrgFlag = StringUtils.isEmpty(request.getParameter("isOrgFlag"))? null : request.getParameter("isOrgFlag");
		
		/* 增加社区访问头部 */
		String communityHeader = null;
		String communityId = StringUtils.isEmpty(request.getParameter("communityId")) ? null : request.getParameter("communityId");
		
		/** 门户头尾接口地址和平台编码 */
		String platformCode = (String) request.getAttribute(com.whty.cms.common.constants.CommonConstants.PLATFORM_CODE);
		if (StringUtils.isEmpty(platformCode)) {
			platformCode = com.whty.cms.web.common.constants.CommonConstants.DISTRICT_CODE_TY;
		}
		String commonHtmlUrl = (String) request.getAttribute(com.whty.cms.common.constants.CommonConstants.COMMON_HTML_URL);
		if (StringUtils.isEmpty(commonHtmlUrl)) {
			commonHtmlUrl = SysConf.getString("portal.php.url") + "commonhtml";
		}

		headerHtml = (String) MemcacheUtils.getObj(usessionid + "_headerHtml_" + platformCode);
		footerHtml = (String) MemcacheUtils.getObj(usessionid + "_footerHtml_" + platformCode);
		/** 学校账户在学科资源列表发布资源时不能取空间头 */
		if (StringUtils.isEmpty(isOrgFlag)) {
			zoneHeader = (String) MemcacheUtils.getObj(usessionid + "_zoneHeader_" + platformCode);
		}
		zoneFooter = (String) MemcacheUtils.getObj(usessionid + "_zoneFooter_" + platformCode);
		oneClassHeader = (String) MemcacheUtils.getObj(usessionid + "_oneClassHeader_" + platformCode);
		visitorHeader = (String) MemcacheUtils.getObj(userId  + "_" + usessionid + "_visitorHeader_" + platformCode);
		visitorFooter = (String) MemcacheUtils.getObj(userId  + "_" + usessionid + "_visitorFooter_" + platformCode);
		
		if (StringUtils.isNotEmpty(classId)) {
			classHeader = (String) MemcacheUtils.getObj(classId   + "_" + usessionid + "_classHeader_" + platformCode);
			if (StringUtils.isEmpty(classHeader)) {
				classHeader = getCommonHtml(usessionid, "class", classId, platformCode, commonHtmlUrl);
				MemcacheUtils.putObj(classId   + "_" + usessionid + "_classHeader_" + platformCode, classHeader, cacheSecs);
			}
			request.setAttribute("classHeader", classHeader);
		}

		if (StringUtils.isNotEmpty(schoolId)) {
			schoolHeader = (String) MemcacheUtils.getObj(schoolId + "_" + usessionid + "_schoolHeader_" + platformCode);
			if (StringUtils.isEmpty(schoolHeader)) {
				schoolHeader = getCommonHtml(usessionid, "school", schoolId, platformCode, commonHtmlUrl);
				MemcacheUtils.putObj(schoolId + "_" + usessionid + "_schoolHeader_" + platformCode, schoolHeader, cacheSecs);
			}
			if (StringUtils.isEmpty(isOrgFlag)) {
				request.setAttribute("schoolHeader", schoolHeader);
			}else {
				request.setAttribute("zoneHeader", schoolHeader);
			}
		}

		if (StringUtils.isNotEmpty(userId)) {
			if (StringUtils.isEmpty(visitorHeader)) {
				visitorHeader = getCommonHtml(usessionid, "visitor", userId, platformCode, commonHtmlUrl);
				MemcacheUtils.putObj(userId  + "_" + usessionid + "_visitorHeader_" + platformCode, visitorHeader, cacheSecs);
			}
			request.setAttribute("visitorHeader", visitorHeader);

			visitorFooter = (String) MemcacheUtils.getObj(userId + "_visitorFooter_" + platformCode);
			if (StringUtils.isEmpty(visitorFooter)) {
				visitorFooter = getCommonHtml(usessionid, "zoneFooter", null, platformCode, commonHtmlUrl);
				MemcacheUtils.putObj(userId  + "_" + usessionid + "_visitorFooter_" + platformCode, visitorFooter, cacheSecs);
			}
			request.setAttribute("visitorFooter", visitorFooter);
		}

		if (StringUtils.isEmpty(headerHtml)) {
			headerHtml = getCommonHtml(usessionid, "header", null, platformCode, commonHtmlUrl);
			MemcacheUtils.putObj(usessionid + "_headerHtml_" + platformCode, headerHtml, cacheSecs);
		}

		if (StringUtils.isEmpty(footerHtml)) {
			footerHtml = getCommonHtml(usessionid, "footer", null, platformCode, commonHtmlUrl);
			MemcacheUtils.putObj(usessionid + "_footerHtml_" + platformCode, footerHtml, cacheSecs);
		}
		if (StringUtils.isEmpty(zoneHeader) && CommonFunction.isNotNull(usessionid)) {
			zoneHeader = getCommonHtml(usessionid, "zoneHeader", null, platformCode, commonHtmlUrl);
			MemcacheUtils.putObj(usessionid + "_zoneHeader_" + platformCode, zoneHeader, cacheSecs);
		}

		if (StringUtils.isEmpty(zoneFooter) && CommonFunction.isNotNull(usessionid)) {
			zoneFooter = getCommonHtml(usessionid, "zoneFooter", null, platformCode, commonHtmlUrl);
			MemcacheUtils.putObj(usessionid + "_zoneFooter_" + platformCode, zoneFooter, cacheSecs);
		}
		
		if (StringUtils.isEmpty(oneClassHeader)) {
			oneClassHeader = getCommonHtml(usessionid, "oneClassHeader",null, platformCode, commonHtmlUrl);
			MemcacheUtils.putObj(usessionid + "_oneClassHeader_" + platformCode, oneClassHeader, cacheSecs);
		}
		
		if (StringUtils.isNotBlank(communityId)) {
			if (StringUtils.isEmpty(communityHeader)) {
				communityHeader = getCommonHtml(usessionid, "schoolHeader", communityId, platformCode, commonHtmlUrl);
				MemcacheUtils.putObj(communityId  + "_" + usessionid + "_communityHeader_" + platformCode, communityHeader, cacheSecs);
			}
			request.setAttribute("communityHeader", communityHeader);
		}

		request.setAttribute("headerHtml", headerHtml);
		request.setAttribute("footerHtml", footerHtml);
		if (StringUtils.isEmpty(isOrgFlag)) {
			request.setAttribute("zoneHeader", zoneHeader);
		}
		request.setAttribute("zoneFooter", zoneFooter);
		request.setAttribute("oneClassHeader", oneClassHeader);

		return true;
	}

	/**
	 * 
	 * @param usessionid
	 * @param type
	 *            type=class时调用班级，type=school学校头
	 * @return
	 */
	public String getCommonHtml(String usessionid, String type, String scid,
			String platformCode, String commonHtmlUrl) {
		String content = null;
		JSONObject body = new JSONObject();
		body.put("method", "commonHTML");
		JSONObject params = new JSONObject();
		if (StringUtils.isNotEmpty(usessionid)) {
			params.put("islogin", "1");
			params.put("usessionid", usessionid);
		} else {
			params.put("islogin", "0");// 用户未登录
		}
		params.put("areacode", platformCode);
		// headtype = 1 学校，2 班级
		if ("class".equals(type)) {
			params.put("headtype", "2");
			params.put("type", "zoneHeader");
			params.put("scid", scid);
		} else if ("school".equals(type)) {
			params.put("headtype", "1");
			params.put("type", "zoneHeader");
			params.put("scid", scid);
		}else if("oneClassHeader".equals(type)){
        	params.put("type", "header");
        	params.put("logo", "1");
		} else if ("visitor".equals(type)) {
			params.put("type", "zoneHeader");
			params.put("headtype", "3");
			params.put("scid", scid);
		} else if ("schoolHeader".equals(type)) {
			params.put("type", "schoolHeader");
			params.put("headtype", "1");
			params.put("scid", scid);
		}else {
			params.put("type", type);
		} 
		params.put("version", "1");
		params.put("source", "1");
		body.put("params", params);
		try {
			LOGGER.info("获取门户头尾信息... commonHtmlUrl=" + commonHtmlUrl + "\nparams=" + body.toString());
			String result = com.whty.cms.web.common.util.HttpClientUtil.post(commonHtmlUrl, body.toString());			
			if (CommonFunction.isNotNull(result)) {
				JSONObject obj = JSONObject.fromObject(result);
				if (MessageCode.SUCCESS.equals(obj.get("retCode"))) {
					content = obj.getJSONObject(MessageConstants.RESULT).optString("sCommonContent");
				}
			}
		} catch (Exception e) {
			LOGGER.error("门户头尾获取失败:" + e.getMessage(), e);
		}
		return content;
	}

	public List<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(List<String> ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}

}
