function demo(value){
	var requestParam = {
		type:'',
		value:value
	};
	requestParam.value1= "";
	$.post("<%=WebApp.PATH%>/downloadOrgRes.html",requestParam,function(data){
		if(data.result == ""){
			
		}else{
			
		}
	},"json");
}

--------------------------------------------------------------------------
function demo(){
	$.ajax({
		type:"POST",
		url:"",
		data:{flag:'1',returnUrl:''},
		async: false,//�����Ƿ��첽��Ĭ��Ϊ�첽,
		dataType:"json",
		success : function(data){
			
		},
		error : function(erro){
			
		}
	});
}

---------------------------------------------------------------------------
function demo(){
	$.get("",{flag:'1'},function(data){
		
	});
}

---------------------------------------------------------------------------
var comment = {
	append:function(obj){
		if($(obj).html == ''){
			
		}
	},
	submit:function(obj){
		var t = $(obj);
		t.addClass('loading');
		t.attr('disabled','disabled');
	}
}
-----------------------------------------------------------------------------
$.fn.extend({
	grade : function(valObj,scoreObj,resObj){
		var resObj = resObj || false;
		$(this).click(function(event){
			var event = event || window.event;
						var l = $(this).width();
						var result = "һ��";
						var score = Math.ceil((event.pageX - $(this).offset().left)/ (2 * l) * 10) * 2;
						$(this).find('em').width(score * 10 + '%');
						valObj.val(score);
						switch (score) {
							case 2:
								result = "�ܲ�";
								break;
							case 4:
							case 6:
								result = "һ��";
								break;
							case 8:
								result = "�ܺ�";
								break;
							default:
								result = "�Ƽ�";
						};
						resObj.html(result)
					});
		});
});
-------------------------------------------------------------------------------
var Cookie = {
	set : function(name,value){
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	},
	get: function(name){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg))
			return unescape(arr[2]);
		else
			return null;
	}
}
--------------------------------------------------------------------------------
--��ҳ
function gotowhatpage(){
		var whatpage = document.getElementById('whatpage').value;
		 if(isNaN( whatpage ))
		   {
			 alertDiv('����������');
		    return false;
		   }
		if (whatpage==""||whatpage==null||whatpage<=0){
			whatpage = 1;
		}
		if(!/^[0-9]*$/.test(whatpage)){
		     //alert("����������!");
		     whatpage = 1;
		}
		goPage(whatpage);
	}
	function goPage(pageNum) {
		$("#curPage").val(pageNum);
		$('#commentList').empty();
		$.ajax({
			url : "queryCommentList.html", //�����url��ַ
			dataType : "json", //���ظ�ʽΪjson
			async : true,//�����Ƿ��첽��Ĭ��Ϊ�첽����Ҳ��ajax��Ҫ����
			data : {
				curPage: pageNum,
				numPerPage: 5,
				productCode: '${product.productCode}',
				commentType: '1'
			}, //����ֵ
			type : "POST", //����ʽ
			success : function(data) {
				if (data.success && data.commentList) {
					var commentUserNum = data.commentUserNum;
					var commentList = data.commentList;
					var pagination = data.pagination;
					var scoreNumq = data.scoreNum;
					var commentListHtml = "";
					for (var i = 0; i < commentList.length; i++) {
						var comment = commentList[i];		
						commentListHtml += "<li>";
						commentListHtml += "<a href=\"javascript:void(0);\" class=\"z_l_img\"><img src=\"" +comment.userAvatar+"\" alt=\"\" /></a>";
						commentListHtml += "<div class=\"z_r_txt\">";
						commentListHtml += "<div class=\"clearfix\">";
						commentListHtml += "<em class=\"fr\">"+comment.commentTimeStr+"</em>";
						commentListHtml += "<a href=\"\" class=\"z_name\">"+comment.realName+"</a>";
						commentListHtml += "<div class=\"grade_wrap\">";
						commentListHtml += "  <div class=\"grade_outer2 fl\">";
						commentListHtml += "    <div class=\"grade_inner grade_inner2\" style=\"width:" + scoreNumq[i]/5*100 + "%;\"></div>";
						commentListHtml += "	<input type=\"hidden\" value=\"\">";
						commentListHtml += "  </div>";
						commentListHtml += "</div>";
						commentListHtml += "<p class=\"mgt10\">"+comment.commentContent +"</p>";
						commentListHtml += "</div>";
						commentListHtml += "</li>";
					}
					$("#commentList").html(commentListHtml);
					var pageBar = "<div class=\"turnPage t_c mgt10\">";
					pageBar+="<span>";
					if (pagination.curPage > 1) {
						pageBar += "<a href=\"javascript:goPage(1)\">��ҳ</a>";
						pageBar += "<a href=\"javascript:goPage(" + (pagination.curPage-1) + ")\" class=\"p_left\"></a>";
					}
					if (pagination.pagesGroup) {
						for (var i = 0; i < pagination.pagesGroup.length; i++) {
							var page = pagination.pagesGroup[i];
							if (page == pagination.curPage) {
								pageBar += "<span class=\"on\">" + pagination.curPage + "</span>";
							} else {
								pageBar += "<a href=\"javascript:goPage(" + page + ");\">" + page + "</a>";
							}
						}
					}
					if (pagination.curPage < pagination.totalPage) {
						pageBar += "<a href=\"javascript:goPage(" + (pagination.curPage+1) + ")\" class=\"p_right\"></a>";
						pageBar += "<a href=\"javascript:goPage(" + pagination.totalPage + ")\">βҳ</a>";
					}
					pageBar+="</span>";
					pageBar+="<span class=\"mglr15 txt\">�� "+ pagination.totalPage+" ҳ</span><span class=\"txt\">ȥ��</span><div class=\"mglr5 page_num_wrap t_l\"><input type=\"text\"id=\"whatpage\" value=\"\" class=\"num_text\"><p class=\"anim\" ><input type=\"button\" value=\"ȷ��\" onclick=\"gotowhatpage()\" class=\"cfm\"><span class=\"txt\">ҳ</span></p></div></div>";
					if(pagination.totalPage > 1){
						$("#pageBar").html(pageBar);
					}
				}
			},
			error: function(error){  
				$('#commentList').html('�����û����ۣ�');	              
			}
		});
	}
	function preFive() {
		var curPage = $("#curPage").val();
		var pageNum = (Math.ceil(curPage/5) - 1) * 5;
		goPage(pageNum);
	}
	
	function nextFive() {
		var curPage = $("#curPage").val();
		var pageNum = Math.ceil(curPage/5) * 5 + 1;
		goPage(pageNum);
	}

---------------------------------------------------------------------------------------
--����
function alertDiv(alertValue){
		var dialog = art.dialog({
			title:'��ʾ',
			content:'<div class="dialogbox"><p class="t_c pd10">' + alertValue + '</p><div class="t_c pdb20"> <input type="button" value="ȷ��" class="blue_btn"  onoff = "true" /> <input type="button" value="ȡ��" class="gray_btn" onoff = "false" /></div></div>',
			width:'400px',
			padding:'0px 10px',
			initialize:function(){
				var btnTue = $('.dialogbox').find('input[onoff=true]');
				var btnFalse = $('.dialogbox').find('input[onoff=false]');
				btnTue&&btnTue.on('click',function(){
					dialog.close();
				});
				btnFalse&&btnFalse.on('click',function(){
					dialog.close();
				});
			}
		});
	}

-----------------------------------------------------------------------------------------
$(document).ready(function(){
	$("#starbig1").mousemove(function(event){
			var event=event||window.event;
			var l=$(this).width();
			var score1=Math.ceil((event.pageX-$(this).offset().left)/(2*l)*10);
			$("#tipScore1").text(score1 + "��");
	 	});
		$("#starbig1").mouseleave(function(event){
			$("#tipScore1").empty();
			if('' != userScore && null != userScore && 'undefined' != userScore){
				$("#tipScore1").text(userScore + "��");
			}
	 	});
});
-----------------------------------------------------------------------------------------
//�ַ����ȿ���
	var self = $("[limit]");
    self.each(function(){   
        var objString = $(this).text();   
        var objLength = $(this).text().length;  
        var num = $(this).attr("limit");   
        if(objLength > num){   
			$(this).attr("title",$(this).text());   
            objString = $(this).text(objString.substring(0,num) + "...");   
        }   
    });
------------------------------------------------------------------------------------------
$('.yx_table dd').live("hover",function(event){
    	if (event.type == 'mouseenter') {
	    	$(this).find('.yx_button').show();	
    	} else if (event.type=='mouseleave') {
    		$(this).find('.yx_button').hide();
    	}
    });
-------------------------------------------------------------------------------------------
/** �첽����֪ʶ��*/
function asyncLoadKnowledgePointTree(tyFlag,resId,periodId,subjectId,parentId,selKnowledgePointId){
	var requestParam = {
		periodId: periodId,
		subjectId:subjectId,
		parentId:parentId
	};
	$.post("<%=WebApp.PATH%>/getKnowledgePointTree.do", requestParam, function(data) {
		if (data.success) {
			var zNodes = new Array();
			zNodes[0] = {id:'0', pId:'', name:'֪ʶ��', open:true, isParent:true }; 
			if(data.knowledgePointList){
				var knowledgePointList = data.knowledgePointList;
	 			for (var i = 0; i < knowledgePointList.length; i++) {
	 				var knowledgePoint = knowledgePointList[i];
	 				zNodes[i+1] = {
	 					id: knowledgePoint.knowledgePointId,
	 					pId: knowledgePoint.parentId,
	 					name: knowledgePoint.knowledgePointName,
	 					sortNum: knowledgePoint.sortNum,
	 					periodId: knowledgePoint.periodId,
	 					subjectId: knowledgePoint.subjectId,
	 					isParent:knowledgePoint.isParent
	 				};
	 			}
			}
			var setting = {
					view: {
						showLine: false,
						showIcon: showIconForTree
					},
					async:{
						enable: true,
						url: "<%=WebApp.PATH%>/getKnowledgePointTree.do",
						autoParam:["id"],
						dataType:"json",
						otherParam:{"periodId":periodId,"subjectId":subjectId},
						dataFilter:filter
					},
					data: {
						simpleData: {
							enable: true,
							idKey: "id",
							pIdKey: "pId",
							rootPId: "root"
						}	
					},
					callback: {
						onAsyncSuccess: onAsyncSuccess,
						onClick: clickKnowledgePoint
					}
				};
			if('0' == tyFlag){
				$.fn.zTree.init($(".w_column_box").find("#treeDemo3_"+resId), setting, zNodes);
				if('' != selKnowledgePointId && null != selKnowledgePointId && 'undefined' != selKnowledgePointId){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo3_"+resId);
					if(selKnowledgePointId.indexOf(",")){
						var ids = selKnowledgePointId.split(",");
						for(var i=0; i < ids.length; i++){
							var node = treeObj.getNodeByParam("id", ids[i], null);
				    		if (node) {
				    			treeObj.selectNode(node,true);
				    			treeObj.expandNode(node, true, null, null, null);
							}
						}
					}else{
						var node = treeObj.getNodeByParam("id", selKnowledgePointId, null);
			    		if (node) {
			    			treeObj.selectNode(node);
			    			treeObj.expandNode(node, true, null, null, null);
						}
					}
				}
			}else{
				$.fn.zTree.init($(".w_column_box").find("#treeDemo3"), setting, zNodes);
				if('' != selKnowledgePointId && null != selKnowledgePointId && 'undefined' != selKnowledgePointId){
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo3");
					if(selKnowledgePointId.indexOf(",")){
						var ids = selKnowledgePointId.split(",");
						for(var i=0; i < ids.length; i++){
							var node = treeObj.getNodeByParam("id", ids[i], null);
				    		if (node) {
				    			treeObj.selectNode(node,true);
				    			treeObj.expandNode(node, true, null, null, null);
							}
						}
					}else{
						var node = treeObj.getNodeByParam("id", selKnowledgePointId, null);
			    		if (node) {
			    			treeObj.selectNode(node);
			    			treeObj.expandNode(node, true, null, null, null);
						}
					}
				}
			}
		}
	}, "json");
}
function clickKnowledgePoint(event, treeId, treeNode) {
	if('0' != treeNode.id){
		if($("#unifyCatalogueFlag").val() == '0'){
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodes = treeObj.getSelectedNodes();
			var ids = "";
			for(var i =0; i < nodes.length; i++){
				if('0' != nodes[i].id){
					ids = ids + nodes[i].id + ",";
				}
			}
			ids = ids.substring(0,ids.lastIndexOf(","));
			$("#"+treeId).parents("div.xy_nd_fablist").find("input[id^='knowledgePointId_']").val(ids);
		}else{
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo3");
			var nodes = treeObj.getSelectedNodes();
			var ids = "";
			for(var i =0; i < nodes.length; i++){
				if('0' != nodes[i].id){
					ids = ids + nodes[i].id + ",";
				}
			}
			ids = ids.substring(0,ids.lastIndexOf(","));
			$("[name='bmtabCon']").find("input[id='knowledgePointId']").val(ids);
		}
	}
}
function filter(treeId, parentNode, childNodes) {
	var jsonArr = childNodes.knowledgePointList;//��ȡ��̨���ݵ�����
	if('0' == parentNode.pId){
		var zNodes = new Array();
		for (var i = 0; i < jsonArr.length; i++) {
				var knowledgePoint = jsonArr[i];
				zNodes[i] = {
					id: knowledgePoint.knowledgePointId,
					pId: knowledgePoint.parentId,
					name: knowledgePoint.knowledgePointName,
					isParent:'false'
					//isParent:knowledgePoint.isParent
				};
			}
		return zNodes;
	}
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
	if($("#unifyCatalogueFlag").val() == '0'){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.updateNode(treeNode);   //�첽���سɹ���ˢ�����ڵ�
	}else{
		var zTree = $.fn.zTree.getZTreeObj("treeDemo3");
		zTree.updateNode(treeNode);   //�첽���سɹ���ˢ�����ڵ�
	}
}
---------------------------------------------------------------------------------------
$(".tips1").delay(5000).hide(0);
$(".xy_nb_fbbox:first").addClass("xy_nbbor").find(".xy_nd_fablist").show();

---------------------------------------------------------------------------------------
var catalogueJsonData = new Array();
$("input[id^='res_']").each(function(){
	var jsonObj = {};
	jsonObj["resId"] = $(this).val();
	jsonObj["resType"] = resType;
	jsonObj["periodId"] = period;
	jsonObj["subjectId"] = subject;
	jsonObj["examPeriodId"] = examPeriodId;
	jsonObj["examSubjectId"] = examSubjectId;
	jsonObj["editionId"] = edition;
	jsonObj["textbookId"] = textbook;
	jsonObj["price"] = $("[name='bmtabCon']").find("#price").val();
	jsonObj["keyword"] = $("[name='bmtabCon']").find("#keyword").val();
	jsonObj["intro"] = $("[name='bmtabCon']").find("#intro").val();
	jsonObj["chapterId"] = $("[name='bmtabCon']").find("#chapter_").val();
	jsonObj["gradeId"] = $("[name='bmtabCon']").find("#gradeId").val();
	jsonObj["volumeId"] = $("[name='bmtabCon']").find("#volumeId").val();
	jsonObj["sjResTypeId"] = $("[name='bmtabCon']").find("#sjResTypeId").val();
	jsonObj["yearsId"] = $("[name='bmtabCon']").find("#yearsId").val();
	jsonObj["regionId"] = $("[name='bmtabCon']").find("#regionId").val();
	jsonObj["sjZGResTypeId"] = $("[name='bmtabCon']").find("#sjZGResTypeId").val();
	jsonObj["knowledgePointId"] = $("[name='bmtabCon']").find("#knowledgePointId").val();
	catalogueJsonData.push(jsonObj);
});
requestParam = {
		unifyCatalogueFlag: $("#unifyCatalogueFlag").val(),
		catalogueType: $("#catalogueType").val(),
		catalogueJsonData: JSON.stringify(catalogueJsonData)
};

-----------------------------------------------------------------------------------------















