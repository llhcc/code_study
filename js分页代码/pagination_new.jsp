<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/import.jsp"%>
<input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>
<div class="turnPage t_c mgt10">
	<c:choose>
		<c:when test="${pagination.totalPage >1 }">
			<span>
				<c:if test="${pagination.curPage != 1 }">
					<a href="javascript:goPage(1);">首页</a>
					<a href="javascript:goPrePage();" class="p_left"></a>
				</c:if>
				<c:forEach items="${pagination.pageGroup}" var="page" varStatus="status">
				    <c:if test="${page >0}">
						<c:if test="${page == pagination.curPage}">
							<span class="on">${pagination.curPage}</span>
						</c:if>
						<c:if test="${page != pagination.curPage}">
							<a href="javascript:goPage(${page});">${page}</a>
						</c:if>
					</c:if>
				</c:forEach>
				<c:if test="${pagination.curPage < pagination.totalPage }">
					<a href="javascript:goNextPage();" class="p_right"></a>
					<a href="javascript:goPage(${pagination.totalPage});">尾页</a>
				</c:if>
			</span>
			<span class="mglr15 txt">共 ${pagination.totalPage} 页</span>
			<span class="txt">去第</span>
			<div class="mglr5 page_num_wrap t_l">
			    <input type="text" id="toPageNum" value="" class="num_text" onkeypress="return IsNum(event)"/>
			    <p class="anim">
			        <input type="button" id="confTurnPage" value="确定" class="cfm" onclick="javascript:toPage(${pagination.totalPage});"/>
			        <span class="txt">页</span>
			    </p>
			</div>
		</c:when>
		<c:otherwise>
			<span>&nbsp;</span>
		</c:otherwise>
	</c:choose>
</div>
<script type="text/javascript">
	$(document).keydown(function(e){
		if(e.which == 13){
			$("#confTurnPage").click();
		}
	});
	/* $("#toPageNum").blur(function(e){
		$("#confTurnPage").click();
	}); */
	//到输入的页面
	function toPage(totalPage) {
		var pageNum = $("#toPageNum").val();
		if(pageNum == ''){return;}
		var re = /^[0-9]+$/ ;
		if (!re.test(pageNum)) {
			$("#toPageNum").val('');return;
		}
		if(pageNum > totalPage){
			pageNum = totalPage;
		}
		if(pageNum < 1){
			pageNum = 1;
		}
		$("#curPage").val(pageNum);
		$("#form1").submit();
	}
	
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
	
	function IsNum(e) {
        var k = window.event ? e.keyCode : e.which;
        if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
        } else {
            if (window.event) {
                window.event.returnValue = false;
            }
            else {
                e.preventDefault(); //for firefox 
            }
        }
    } 
	
	function goPrePage() {
		var curPage = $("#curPage").val();
		var pageNum = curPage-1;
		goPage(pageNum);
	}
	
	function goNextPage() {
		var curPage = $("#curPage").val();
		var pageNum = parseInt(curPage)+1;
		goPage(pageNum);
	}
</script>