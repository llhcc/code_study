package com.whty.cms.web.common.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.whty.cms.common.domain.SessionUser;
import com.whty.cms.common.util.HttpClient;
import com.whty.cms.common.util.SysConf;
import com.whty.cms.web.common.constants.CommonConstants;
import com.whty.framework.util.MapUtils;
import com.whty.framework.util.ObjectFactory;

/**
 * @author	xiongxiaofei
 * @date	2014年12月26日
 * @desc	添加积分拦截器
 */
public class AddPointsInterceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LogManager.getLogger(AddPointsInterceptor.class);
	
	private JdbcTemplate jdbcTemplate = (JdbcTemplate) ObjectFactory.getBean("jdbcTemplate");

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception exception) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (exception == null) {
				String servletPath = request.getServletPath();
				String userId = null;
				SessionUser sessionUser = (SessionUser) request.getSession(true).getAttribute(CommonConstants.SESSION_USER);
				if (sessionUser != null && StringUtils.isNotEmpty(sessionUser.getUserId())) {
					userId = sessionUser.getUserId();
				}
				
				/**
				 * 上传资源
				 */
				if ("/uploadResource1.do".equals(servletPath) || "/uploadResource.do".equals(servletPath)) {
					addPoints(userId, CommonConstants.POINT_TYPE_UPLOAD);
				}
				
				/**
				 * 分享资源
				 */
				if ("/doShareToClassRes.do".equals(servletPath)) {
					addPoints(userId, CommonConstants.POINT_TYPE_SHARE);
				}
				
				/**
				 * 成功上报资源
				 */
				if ("/orgResAudit.do".equals(servletPath)) {
					String auditStatus = request.getParameter("auditStatus");
					if ("1".equals(auditStatus)) {
						String pkId = request.getParameter("pkId");
						if (StringUtils.isNotEmpty(pkId)) {
							String[] pkIds = pkId.split(",");
							String sql = "select REPORTED_ID from T_RES_REPORT where PK_ID=?";
							for (String id : pkIds) {
								Map<String,Object> map = jdbcTemplate.queryForMap(sql, id);
								String reportedId = (String) map.get("REPORTED_ID");
								addPoints(reportedId, CommonConstants.POINT_TYPE_REPORT_PASS);
							}
						}
					}
				}
				
				/**
				 * 资源被下载
				 */
				if ("/downloadLog.html".equals(servletPath)) {
					String downType = request.getParameter("downType");
					
					String id = null;
					String sql = null;
					
					/** 产品下载 */
					if ("1".equals(downType)) {
						id = request.getParameter("productCode");
						sql = "select USER_ID from T_PRODUCT where PRODUCT_CODE=?";
					}
					
					/** 资源下载 */
					if ("2".equals(downType)) {
						id = request.getParameter("resId");
						sql = "select USER_ID from T_RES_OWNER where RES_ID=?";
					}
					
					if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(sql)) {
						Map<String,Object> map = jdbcTemplate.queryForMap(sql, id);
						if (map != null) {
							String uid = (String) map.get("USER_ID");
							addPoints(uid, CommonConstants.POINT_TYPE_DOWNLOADED);
						}
					}
				}
				
				/**
				 * 资源被好评
				 */
				if ("/commentScore.html".equals(servletPath)) {
					String score = request.getParameter("score");
					if ("5".equals(score)) {
						String scoreType = request.getParameter("scoreType");
						/** 产品评分 */
						if ("1".equals(scoreType)) {
							String productCode = request.getParameter("productCode");
							String sql = "select t1.USER_ID from T_RES_OWNER t1 inner join T_PROD_RES t2 on t1.RES_ID=t2.RES_ID and t2.PRODUCT_CODE=?";
							List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, productCode);
							if (list != null && list.size() > 0) {
								for (Map<String,Object> map : list) {
									String uid = (String) map.get("USER_ID");
									addPoints(uid, CommonConstants.POINT_TYPE_SCORED);
								}
							}
						}
						
						/** 资源评分 */
						if ("2".equals(scoreType)) {
							String resId = request.getParameter("resId");
							String sql = "select USER_ID from T_RES_OWNER where RES_ID=?";
							Map<String,Object> map = jdbcTemplate.queryForMap(sql, resId);
							if (map != null) {
								String uid = (String) map.get("USER_ID");
								addPoints(uid, CommonConstants.POINT_TYPE_SCORED);
							}
						}
					}
				}
			}
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	/**
	 * @author	xiongxiaofei
	 * @date	2014年12月26日
	 * @desc	添加积分
	 * @param userId
	 * @param typeCode
	 * @throws Exception
	 */
	private void addPoints(String userId, String typeCode) throws Exception {
		if (StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(typeCode)) {
			HttpClient httpClient = new HttpClient(SysConf.getString("api.sme.url") + "/point/addpoint");
			Map<String,String> contentMap = MapUtils.newHashMap();
			contentMap.put("usercode", userId);
			contentMap.put("typecode", typeCode);
			JSONObject content = JSONObject.fromObject(contentMap);
			String response = httpClient.doPost(content.toString());
			LOGGER.info("sme接口返回=>" + response);
		}
	}

}
