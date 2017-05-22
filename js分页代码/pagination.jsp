<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/import.jsp"%>
<c:if test="${pagination.totalPage >1 }">
	<div class="mgtd20 tc">
		<input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>
		<div class="turnPage" style="zoom: 1; overflow: hidden;">
			<span>共${pagination.totalPage}页</span>
			<span>
				<c:if test="${pagination.totalPage > 1 }">
					<a href="javascript:goPage(1);">首页</a>
				</c:if>
				<c:if test="${pagination.totalPage < 1 || pagination.totalPage == 1}">
					<a href="">首页</a>
				</c:if>
				<c:if test="${pagination.curPage > 5}">
					<a href="javascript:preFive();">上五页</a>
				</c:if>
				<c:forEach items="${pagination.pageGroup}" var="page" varStatus="status">
					<c:if test="${page == pagination.curPage}">
						<span class="on">${pagination.curPage}</span>
					</c:if>
					<c:if test="${page != pagination.curPage}">
						<a href="javascript:goPage(${page});">${page}</a>
					</c:if>
				</c:forEach>
				<c:if test="${pagination.totalPage > ((pagination.curPage/5)*5 + 5)}">
					<a href="javascript:nextFive();">下五页</a>
				</c:if>
				<c:if test="${pagination.totalPage > 5}">
					<a href="javascript:goPage(${pagination.totalPage});">尾页</a>
				</c:if>
			</span>
		</div>
	</div>
</c:if>
<script type="text/javascript">
	function goPage(pageNum) {
		$("#curPage").val(pageNum);
		$("#form1").submit();
	}
	
	function preFive() {
		var curPage = parseInt('${pagination.curPage}');
		var pageNum = (Math.ceil(curPage/5) - 1) * 5;
		goPage(pageNum);
	}
	
	function nextFive() {
		var curPage = parseInt('${pagination.curPage}');
		var pageNum = Math.ceil(curPage/5) * 5 + 1;
		goPage(pageNum);
	}
</script>