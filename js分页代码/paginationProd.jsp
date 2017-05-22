<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<input type="hidden" id="curPage" name="curPage" value="${pagination.curPage}"/>
<div class="turnPage mgt10 t_c" id="pageDiv">
</div>
<script>
loadPageDiv();
function loadPageDiv(){
	var listStep = 9;
	var totalPage = ${pagination.totalPage };
	if (totalPage > 1) {
		var curPage = parseInt($("#curPage").val());
		var listBegin = curPage-Math.ceil(listStep/2);
		if (totalPage-curPage < Math.floor(listStep/2)) {
			listBegin = totalPage-8;
		}
		if (listBegin < 1) {
			listBegin = 1;
		}
		var listEnd = curPage+Math.floor(listStep/2);
		if (listEnd < 10) {
			listEnd = 10;
		}
		if (listEnd > totalPage) {
			listEnd = totalPage+1;
		}
		
		var pageDivHtml = "";
		var showTotalCount = $("#showTotalCount").val();
		if ("yes" == showTotalCount) {
			pageDivHtml += "<span class=\"fr qjf_rignum\">共<i style=\"color:#555;\">"+formatNumberThreePlace('${pagination.totalCount }')+"</i>份</span>";
		}
		if (curPage > 1) {
			pageDivHtml += "<a href=\"javascript:goPage(1)\">首页</a>";
			pageDivHtml += "<a href=\"javascript:goPage(" + (curPage-1) + ")\" class=\"p_left\"></a>";
		}
		for (var i = listBegin; i < listEnd; i++) {
			if (i != curPage) {
				pageDivHtml += "<a href=\"javascript:goPage(" + i + ")\">"+i+"</a>";
			} else {
				pageDivHtml += "<span class=\"on\">" + i + "</span>";
			}
		}
		if (curPage < totalPage) {
			pageDivHtml += "<a href=\"javascript:goPage(" + (curPage+1) + ")\" class=\"p_right\"></a>";
			pageDivHtml += "<a href=\"javascript:goPage(" + totalPage + ")\">尾页</a>";
		}
		pageDivHtml += "<span class=\"mglr15 txt\">共" + totalPage + " 页</span>";
		pageDivHtml += "<span class=\"txt\">去第</span>";
		pageDivHtml += "<div class=\"mglr5 page_num_wrap t_l\">";
		pageDivHtml += "<input id=\"toPageNum\" type=\"text\" class=\"num_text\" onkeypress=\"return IsNum(event)\"/>";
		pageDivHtml += "<p class=\"anim\"><input id=\"confTurnPage\" type=\"button\" value=\"确定\" onclick=\"toPage(" + totalPage + ")\" class=\"cfm\"/><span class=\"txt\">页</span></p>";
		pageDivHtml += "</div></div>";
		$("#pageDiv").html(pageDivHtml);
	}
}
$(document).live("keypress", function(e){
	if(e.which == 13){
		$("#confTurnPage").click();
	}
});
function goPage(pageNum) {
	$("#curPage").val(pageNum);
	$("#form1").submit();
}
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
//数字千位格式化
function formatNumberThreePlace(totalCount) {
	var formatNumber = "";
	totalCount += "";
	var totalCountNumber = totalCount.split("");
	for (var i = totalCountNumber.length-1 ; i >= 0; i--) {
		if ((totalCountNumber.length-i-1) != 0 && (totalCountNumber.length-i-1)%3  == 0) {
			formatNumber = "," + formatNumber;
		}
		formatNumber = "" + totalCountNumber[i] + "" + formatNumber;
	}
	return formatNumber;
}
</script>
